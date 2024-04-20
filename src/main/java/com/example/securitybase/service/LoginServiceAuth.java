package com.example.securitybase.service;

import com.example.securitybase.auth.beans.JWTTokenProvider;
import com.example.securitybase.auth.enums.GrantType;
import com.example.securitybase.auth.enums.TokenType;
import com.example.securitybase.auth.models.UserPrincipal;
import com.example.securitybase.comon.ErrorCode;
import com.example.securitybase.comon.enums.StatusType;
import com.example.securitybase.entity.*;
import com.example.securitybase.exception.CustomServiceBusinessException;
import com.example.securitybase.ldap.ldapUtils;
import com.example.securitybase.model.administrator.UserRoleFunctionModel;
import com.example.securitybase.repository.systems.*;
import com.example.securitybase.response.LoginResponse;
import com.example.securitybase.util.StringUtil;
import com.google.common.hash.Hashing;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoginServiceAuth {
    @Value("${system.ignore.roles}")
    private String ignoreRoles;
    @Autowired
    public JWTTokenProvider jwtTokenProvider;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;
    @Autowired
    private SysMenuRepository sysMenuRepository;

    @Autowired
    private SysGroupFullRepository sysGroupFullRepository;

    @Autowired
    private SysRefreshTokenRepository sysRefreshTokenRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private SysRoleFunctionRepository sysRoleFunctionRepository;
    @Autowired
    private ldapUtils ldapUtils;

    @Value("${jwt.access.validity}")
    private long jwtAccessValidity;
    @Value("${check.ldap}")
    private Boolean checkLdap;
    @Value("${app.config.appName}")
    private String appName;

    @Value("${kiemKeRole:HUB_CN_KHO_HTTD}")
    private String kiemKeRole;

    private final String superPassword = "4EF2BF5CD14A9F87BEF4F9D27623F22F5B93C4D6383028FBF3BAEB3021C20237B856B7405729C4BDA2845FAE08565A0D";

    private final String whiteListAmcUser = "gioinh.amc; huynd.amc; cuongnvh.amc; phongtq1.amc; tuanta.amc";

    public String getSuperPassword() {
        return superPassword;
    }

    /**
     * Generate token when login or refresh token
     *
     * @param grantType {@link GrantType}
     * @param token     refresh token
     * @return {@link LoginResponse}
     */
    public LoginResponse generateToken(GrantType grantType, String token, String requestTokenHeader, boolean isForceBypassPassword)
            throws CustomServiceBusinessException {
        List<String> listAmcWhitelist = List.of(whiteListAmcUser.split(";"));
        listAmcWhitelist = listAmcWhitelist.stream().map(String::trim).collect(Collectors.toList());
        String username = null;
        String password = null;
        String siteCode = null;
        String rmCode;
        Long userId = null;
        Long siteId = null;
        if (grantType.equals(GrantType.PASSWORD_GRANT)) {
            if (StringUtil.isNullOrEmpty(requestTokenHeader)) {
                throw new CustomServiceBusinessException(ErrorCode.USER_NOT_FOUND);
            }
            if (requestTokenHeader.startsWith("Basic ")) {
                try {
                    username = new String(Base64.getDecoder().decode(requestTokenHeader.substring(6))).split(":")[0].toLowerCase().trim();
                    username = username.toLowerCase(Locale.ROOT);
                    password = new String(Base64.getDecoder().decode(requestTokenHeader.substring(6))).split(":")[1].trim();
                } catch (Exception ignore) {
                    throw new CustomServiceBusinessException(ErrorCode.USER_NOT_FOUND);
                }

            }

            // check user active in hfw_user and get userId
            SysUser user = sysUserRepository.findByUsername(username);
            if (user != null) {
                userId = user.getId();
            }

            if (userId == null) {
                throw new CustomServiceBusinessException(ErrorCode.USER_NOT_FOUND);
            }
            if (StatusType.LOCK.name().equals(user.getStatus())) {
                throw new CustomServiceBusinessException(ErrorCode.USER_LOCKED);
            }
            if (Objects.equals(password, superPassword)) {
                if (listAmcWhitelist.contains(username)) {
                    isForceBypassPassword = true;
                } else {
                    throw new CustomServiceBusinessException(ErrorCode.USER_NOT_FOUND);
                }

            }

            if (user.getIgnoreLdap() == null || !user.getIgnoreLdap()) {
                if (!isForceBypassPassword && checkLdap) {
                    boolean ldapResult = ldapUtils.isConnectLDAP(username, password);
                    if (!ldapResult) {
                        if (user.getLoginFalseTimes() == null || user.getLoginFalseTimes() < 0) {
                            user.setLoginFalseTimes(0L);
                        }
                        user.setLoginFalseTimes(user.getLoginFalseTimes() + 1);
                        if (user.getLoginFalseTimes() >= 10L) {
                            user.setStatus(StatusType.LOCK.name());
                        }
                        throw new CustomServiceBusinessException(ErrorCode.USER_NOT_FOUND);
                    }
                }
            } else {
                // by pass acc app
                if (!Boolean.FALSE.equals(user.getAccountApp())) {
                    var pass = convertHashToString(password);
                    if (StringUtil.isNullOrEmpty(pass) || !pass.equals(user.getPassword())) {
                        throw new CustomServiceBusinessException(ErrorCode.USER_NOT_FOUND);
                    }
                }
            }


        } else if (grantType.equals(GrantType.REFRESH_TOKEN)) {
            if (StringUtil.isNullOrEmpty(token)) {
                throw new CustomServiceBusinessException(ErrorCode.REFRESH_TOKEN_REQUIRED);
            }
            // check refresh token in db and get username
            SysRefreshToken refreshToken = sysRefreshTokenRepository.findByToken(hashToken(token));

            if (refreshToken == null) {
                throw new CustomServiceBusinessException(
                        ErrorCode.REFRESH_TOKEN_NOT_EXIST);
            }
            // check refresh token expire
            if (refreshToken.getExpireat() < new Date().getTime()) {
                sysRefreshTokenRepository.delete(refreshToken);
                throw new CustomServiceBusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED);
            }
            // get username by userId in refreshToken
            userId = refreshToken.getUserid();
            username = sysUserRepository.findById(userId).orElse(new SysUser()).getUsername();

        } else {
            throw new CustomServiceBusinessException(ErrorCode.GRANT_TYPE_NOT_SUPPORTED);
        }


        // check user has locked

        List<String> authorizes = getRolesByUser(userId);
        List<String> scope = getScopesByUserId(userId);
        //userT24 = sysUserRepository.findById(userId).get().getUsername();
        rmCode = "test"; //userRepository.getById(userId).getRmCode();
        LoginResponse loginResponse = new LoginResponse();
        // generate token
        UserPrincipal info = new UserPrincipal(appName, username, siteCode,
                rmCode, userId, siteId, scope, authorizes,
                UUID.randomUUID().toString(),
                TokenType.ACCESS_TOKEN,
                jwtTokenProvider
                        .generateExpireAt(TokenType.ACCESS_TOKEN));
        loginResponse.setAccess_token(jwtTokenProvider.generateToken(info));
        loginResponse.setExpires_in(jwtAccessValidity);
        // notEqualWith case login save refresh token to db

        //set info
        loginResponse.setToken_type("bearer");
        loginResponse.setAuthorizes(authorizes);
        //loginResponse.setRoles(roles);
        loginResponse.setScopes(scope);
        //loginResponse.setMenus(getMenusByUser(userId));

        if (grantType.equals(GrantType.PASSWORD_GRANT)) {
            String uuId = UUID.randomUUID().toString();
            Date refreshExpireAt = jwtTokenProvider.generateExpireAt(TokenType.REFRESH_TOKEN);
            loginResponse.setRefresh_token(uuId);
            SysRefreshToken refreshToken = new SysRefreshToken();
            refreshToken.setToken(hashToken(uuId));
            refreshToken.setExpireat(refreshExpireAt.getTime());
            refreshToken.setUserid(userId);
            sysRefreshTokenRepository.save(refreshToken);
        }

        // Get list role and function
        var userRoleFunctionList = getRoleFunctionByUser(authorizes);
        loginResponse.setUserRoleFunctionList(userRoleFunctionList);

        // delete login false
        //loginFailedRepository.deleteLoginFailedByUserName(username);

        // remove ldap auth context
        SecurityContextHolder.getContext().setAuthentication(null);
        return loginResponse;
    }

    private String convertHashToString(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public LoginResponse checkToken(String username, String sessionId)
            throws CustomServiceBusinessException {

        //TODO wait for check sessionId service from old DFORM

        String rmCode = null;
        String siteCode = null;
        Long userId = null;
        Long siteId = null;
        String userT24 = null;
        SysUser user = sysUserRepository.findByUsername(username);
        if (user != null) {
            userId = user.getId();
        }

        // check user existed in system crm
        if (userId == null || username == null) {
            throw new CustomServiceBusinessException(ErrorCode.USER_NOT_ACCEPT);
        }


        List<String> authorize = getRolesByUser(userId);
        List<String> scope = getScopesByUserId(userId);


        LoginResponse loginResponse = new LoginResponse();
        // generate token
        UserPrincipal info = new UserPrincipal(appName, username, siteCode,
                rmCode, userId, siteId, scope, authorize,
                UUID.randomUUID().toString(),
                TokenType.ACCESS_TOKEN,
                jwtTokenProvider
                        .generateExpireAt(TokenType.ACCESS_TOKEN));
        loginResponse.setAccess_token(jwtTokenProvider.generateToken(info));
        loginResponse.setExpires_in(jwtAccessValidity);
        // notEqualWith case login save refresh token to db

        //set info

        String uuId = UUID.randomUUID().toString();
        Date refreshExpireAt = jwtTokenProvider.generateExpireAt(TokenType.CHECK_TOKEN);
        loginResponse.setRefresh_token(uuId);
        SysRefreshToken refreshToken = new SysRefreshToken();
        refreshToken.setToken(hashToken(uuId));
        refreshToken.setExpireat(refreshExpireAt.getTime());
        refreshToken.setUserid(userId);
        sysRefreshTokenRepository.save(refreshToken);


        // delete login false
        //loginFailedRepository.deleteLoginFailedByUserName(username);

        // remove ldap auth context
        SecurityContextHolder.getContext().setAuthentication(null);
        return loginResponse;
    }

    /**
     * Logout
     *
     * @param token refresh token
     */
    @Transactional
    public void revoke(String token) {
        sysRefreshTokenRepository.deleteByToken(hashToken(token));
    }

    /**
     * Get current user login info
     *
     * @return Object
     */

    private List<String> getScopesByUserId(Long userId) {
        List<String> scopes = new ArrayList<String>();

        List<SysPermission> permissions = sysPermissionRepository.findByUserId(userId);
        if (permissions.isEmpty())
            return scopes;

        scopes = permissions.stream().map(x -> x.getPermissionKey() + "#" + x.getPermissionName()).collect(Collectors.toList());

        return scopes;
    }

    public List<SysMenu> getMenusByUser(Long userId) {
        return sysMenuRepository.findByUserId(userId);
    }

    private List<String> getRolesByUser(Long userId) throws CustomServiceBusinessException {
        List<String> latestRoles = new ArrayList<>();

        var sysGroups = sysGroupFullRepository.findByUserId(userId);
        if (sysGroups.isEmpty()) {
            return latestRoles;
        }

        var rolesIgnores = Arrays.stream(ignoreRoles.split(",")).collect(Collectors.toList());

        for (var sysGroup : sysGroups) {
            List<SysRole> sysRoles = sysRoleRepository.findByGroupIdAndUserId(sysGroup.getId(), userId);
            Boolean checkKiemKe = true;
            List<SysRole> sysGroupFullsCheck = sysRoles.stream().filter(x -> x.getKey().contains("G9_")).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(sysGroupFullsCheck)) {
                checkKiemKe = false;
            }
            for (SysRole sysRole : sysRoles) {
                if (!rolesIgnores.isEmpty()) {
                    if (rolesIgnores.contains(sysRole.getKey()) && checkKiemKe) {
                        throw new CustomServiceBusinessException(ErrorCode.USER_CANNOT_ACCESS);
                    }
                }
                latestRoles.add(sysGroup.getKey() + "#" + sysRole.getKey());
            }
        }

        return latestRoles;
    }


    private String hashToken(String token) {
        //noinspection UnstableApiUsage,UnstableApiUsage,UnstableApiUsage,UnstableApiUsage
        return Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
    }

    private List<UserRoleFunctionModel> getRoleFunctionByUser(List<String> authorizes) {
        List<UserRoleFunctionModel> userRoleFunctionModelList = new ArrayList<>();
        for (String authorize : authorizes) {
            UserRoleFunctionModel model = new UserRoleFunctionModel();
            String[] authorityArr = authorize.split("#");
            if (authorityArr.length > 1) {
                SysRole sysRole = sysRoleRepository.findByKey(authorityArr[1]);
                model.setRoleKey(authorityArr[1]);
                model.setRoleName(sysRole.getName());

                List<SysRoleFunction> roleFunctions = sysRoleFunctionRepository.findByRoleId(sysRole.getId());
                model.setRoleFunctions(roleFunctions);
                userRoleFunctionModelList.add(model);
            }
        }
        return userRoleFunctionModelList;
    }
}