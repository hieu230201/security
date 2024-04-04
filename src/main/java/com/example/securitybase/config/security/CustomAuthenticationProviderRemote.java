package com.example.securitybase.config.security;

import com.example.securitybase.auth.beans.JWTTokenProvider;
import com.example.securitybase.auth.enums.TokenType;
import com.example.securitybase.auth.models.UserPrincipal;
import com.example.securitybase.comon.ErrorCode;
import com.example.securitybase.comon.enums.StatusType;
import com.example.securitybase.entity.SysPermission;
import com.example.securitybase.entity.SysRole;
import com.example.securitybase.entity.SysUser;
import com.example.securitybase.exception.CustomServiceBusinessException;
import com.example.securitybase.model.administrator.UserRoleFunctionModel;
import com.example.securitybase.repository.systems.*;
import com.example.securitybase.repository.systems.SysRoleRepository;
import com.example.securitybase.response.LoginResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProviderRemote implements AuthenticationProvider {

    private String superUsername;

    private String superPassword;

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
    private com.example.securitybase.ldap.ldapUtils ldapUtils;

    @Value("${jwt.access.validity}")
    private long jwtAccessValidity;

    @Value("${check.ldap}")
    private Boolean checkLdap;

    @Value("${app.config.appName}")
    private String appName;

    public CustomAuthenticationProviderRemote() {

    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var part = authentication.getName().split("#");
        if (part.length == 1) {
            return null;
        }
        var username = part[0];
        var superUser = part[1];
        var password = authentication.getCredentials().toString();
        if (!Objects.equals(password, superPassword) || !Objects.equals(superUser, superUsername)) {
            return null;
        }
        String siteCode = null;
        String rmCode;
        Long userId = null;
        Long siteId = null;
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
        if (user.getIgnoreLdap() == null || !user.getIgnoreLdap()) {
            if (checkLdap) {
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
        }
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

// Get list role and function
        var userRoleFunctionList = getRoleFunctionByUser(authorizes);
        loginResponse.setUserRoleFunctionList(userRoleFunctionList);

// delete login false
//loginFailedRepository.deleteLoginFailedByUserName(username);

// remove ldap auth context
        SecurityContextHolder.getContext().setAuthentication(null);
        if (loginResponse.getAccess_token() != null) {
            return new UsernamePasswordAuthenticationToken(info, password, authorizes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        return null;
    }

    private List<String> getScopesByUserId(Long userId) {
        List<String> scopes = new ArrayList();

        List<SysPermission> permissions = sysPermissionRepository.findByUserId(userId);
        if (permissions.isEmpty())
            return scopes;

        scopes = permissions.stream().map(x -> x.getPermissionKey() + "#" + x.getPermissionName()).collect(Collectors.toList());

        return scopes;
    }

    private List<String> getRolesByUser(Long userId) throws CustomServiceBusinessException {
        List<String> latestRoles = new ArrayList();

        var sysGroups = sysGroupFullRepository.findByUserId(userId);
        if (sysGroups.isEmpty()) {
            return latestRoles;
        }

        var rolesIgnores = Arrays.stream(ignoreRoles.split(",")).collect(Collectors.toList());

        for (var sysGroup : sysGroups) {
            List<SysRole> sysRoles = sysRoleRepository.findByGroupIdAndUserId(sysGroup.getId(), userId);

            for (SysRole sysRole : sysRoles) {
                if (!rolesIgnores.isEmpty()) {
                    if (rolesIgnores.contains(sysRole.getKey())) {
                        throw new CustomServiceBusinessException(ErrorCode.USER_CANNOT_ACCESS);
                    }
                }
                latestRoles.add(sysGroup.getKey() + "#" + sysRole.getKey());
            }
        }

        return latestRoles;
    }

    private List getRoleFunctionByUser(List<String> authorizes) {
        List userRoleFunctionModelList = new ArrayList();
        for (String authorize : authorizes) {
            UserRoleFunctionModel model = new UserRoleFunctionModel();
            String[] authorityArr = authorize.split("#");
            if (authorityArr.length > 1) {
                SysRole sysRole = sysRoleRepository.findByKey(authorityArr[1]);
                model.setRoleKey(authorityArr[1]);
                model.setRoleName(sysRole.getName());

                List roleFunctions = sysRoleFunctionRepository.findByRoleId(sysRole.getId());
                model.setRoleFunctions(roleFunctions);
                userRoleFunctionModelList.add(model);
            }
        }
        return userRoleFunctionModelList;
    }

    @Override
    public boolean supports(Class authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}


