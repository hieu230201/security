//package com.example.securitybase.service.systems.impl;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import com.example.securitybase.entity.SysRole;
//import com.example.securitybase.entity.SysUser;
//import com.example.securitybase.exception.CustomServiceBusinessException;
//import com.mbbank.cmv.common.enums.Ptvt.DonViKhoiTao;
//import com.mbbank.cmv.common.enums.systems.SysGroupType;
//import com.mbbank.cmv.common.enums.systems.SysRoleKey;
//import com.mbbank.cmv.common.enums.systems.SysUserRole;
//import com.mbbank.cmv.integration.restapis.esb.hcmtoken.getuserhcm.GetUserHcmService;
//import com.mbbank.cmv.integration.restapis.esb.hcmtoken.getuserhcm.response.UserHcmResponse;
//import com.mbbank.cmv.model.SysUserModel;
//import com.mbbank.cmv.model.administrator.SysUserWithCaModel;
//import com.mbbank.cmv.model.atos.systems.SysUserCaAto;
//import com.mbbank.cmv.repository.systems.CmvMappingRoleHcmRepository;
//import com.mbbank.cmv.service.impl.AbstractGenericService;
//import com.mbbank.cmv.service.systems.SysGroupFullService;
//import com.mbbank.cmv.service.systems.SysRoleService;
//import com.mbbank.cmv.service.systems.SysUserCaService;
//import io.jsonwebtoken.lang.Collections;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//
//import com.mbbank.cmv.common.anotations.UseLogging;
//import com.mbbank.cmv.common.anotations.UseSafeRunning;
//import com.mbbank.cmv.repository.systems.SysGroupUserRepository;
//import com.mbbank.cmv.repository.systems.SysUserRepository;
//import com.mbbank.cmv.service.systems.SysUserService;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.CollectionUtils;
//
///**
// * @author nampv
// */
//@Service
//public class SysUserServiceImpl extends AbstractGenericService<SysUser, Long>
//        implements SysUserService {
//
//    @Autowired
//    private SysUserRepository repository;
//    @Autowired
//    private SysGroupFullService sysGroupFullService;
//    @Autowired
//    private SysRoleService sysRoleService;
//    @Autowired
//    private SysUserCaService sysUserCaService;
//
//    @Autowired
//    SysGroupUserRepository sysGroupUserRepository;
//
//    @Autowired
//    private GetUserHcmService getUserHcmService;
//
//    @Autowired
//    private CmvMappingRoleHcmRepository cmvMappingRoleHcmRepository;
//
//    @Override
//    protected JpaRepository<SysUser, Long> getRepository() {
//        return repository;
//    }
//
//    @Override
//    public Page<SysUser> findAllByPaging(String username, Pageable pageable) {
//        return repository.findAllByPaging(username, pageable);
//    }
//
//    @Override
//    public SysUserWithCaModel findWithCaByUserId(Long userId) {
//        var sysUser = findByUserId(userId);
//        if (sysUser == null)
//            return null;
//        var sysUserCa = sysUserCaService.findByUserName(sysUser.getUsername());
//
//        var obj = ModelMapperUtil.toObject(sysUser, SysUserWithCaModel.class);
//        var data = sysGroupFullService.getByUserName(sysUser.getUsername());
//        if (sysUserCa != null) {
//            obj.setActive(sysUserCa.getActive());
//            obj.setSignature(sysUserCa.getSignature());
//            obj.setPhoneCA(sysUserCa.getPhone());
//        }
//        if (!Collections.isEmpty(data)) {
//            var sysUserModels = sysGroupFullService.findUserDetail(sysUser.getId());
//            if (!CollectionUtils.isEmpty(sysUserModels)) {
//                var roleName = sysUserModels.stream().map(SysUserModel::getRoleName).collect(Collectors.toList());
//                obj.setRoleName(String.join(" , ", roleName));
//
//                var sysUserModel = sysUserModels.stream().findFirst().orElse(new SysUserModel());
//                var sysGroupFull = sysGroupFullService.findById(sysUserModel.getParentId());
//                obj.setGroupName(String.format("%1$s -> %2$s", sysGroupFull.getName(), sysUserModel.getGroupName()));
//            }
//        }
//        return obj;
//    }
//
//    private String convertHashToString(String text) {
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("MD5");
//            byte[] hashInBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder();
//            for (byte b : hashInBytes) {
//                sb.append(String.format("%02x", b));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public SysUserWithCaModel saveWithCa(SysUserWithCaModel model) {
//        if (model == null)
//            return null;
//        var sysUser = ModelMapperUtil.toObject(model, SysUser.class);
//        if (StringUtil.isNotNullAndEmpty(model.getPassword())) {
//            sysUser.setPassword(convertHashToString(model.getPassword()));
//        }
//        save(sysUser);
//
//        var sysUserCa = sysUserCaService.findByUserName(model.getUsername());
//        if (sysUserCa == null) {
//            sysUserCa = new SysUserCaAto();
//        }
//        sysUserCa.setUserName(model.getUsername());
//        sysUserCa.setEmail(model.getEmail());
//        sysUserCa.setPhone(model.getPhoneCA());
//        sysUserCa.setFullName(model.getFullname());
//        if (model.getSignature() != null && model.getSignature().length > 10)
//            sysUserCa.setSignature(model.getSignature() == null ? new byte[0] : model.getSignature());
//        sysUserCa.setActive(model.getActive());
//        sysUserCaService.save(sysUserCa.toEntity());
//
//        model.setFiles(null);
//        return model;
//    }
//
//    @Override
//    @UseLogging
//    @UseSafeRunning
//    public SysUser findByUserId(Long userId) {
//        SysUser user = repository.findById(userId).orElse(null);
//        return user;
//    }
//
//
//    @Override
//    public List<SysUser> findByGroupIdAndRoleId(Long groupId, Long roleId) {
//        return repository.findByGroupIdAndRoleId(groupId, roleId);
//    }
//
//    @Override
//    public List<SysUser> findInParentGroupByGroupTypeAndRole(Long userId, String groupType, Long roleId) {
//        var groups = sysGroupFullService.getByUserId(userId);
//        if (groups.isEmpty())
//            return new ArrayList<>();
//
//        var firstGroup = groups.get(0);
//
//        var groupParent = sysGroupFullService.getParentNodeNearestByGroupType(firstGroup, groupType);
//        if (groupParent == null)
//            return new ArrayList<>();
//
//        return findByGroupIdAndRoleId(groupParent.getId(), roleId);
//    }
//
//    @Override
//    public List<SysUser> findInParentGroupByGroupTypeAndRole(Long userId, String groupType, String roleKey) {
//        var role = sysRoleService.findByKey(roleKey);
//        if (role == null)
//            return new ArrayList<>();
//        return findInParentGroupByGroupTypeAndRole(userId, groupType, role.getId());
//    }
//
//    @Override
//    public List<SysUser> findAllGdChiNhanh(Long userId) {
//        var roleGd = SysRoleKey.GD_CHI_NHANH.getValue();
//        var roles = roleGd.split(",");
//        for (var role : roles) {
//            var list = findInParentGroupByGroupTypeAndRole(userId, SysGroupType.CHI_NHANH.getValue(), role);
//            if (list != null && !list.isEmpty())
//                return list;
//        }
//        return new ArrayList<>();
//    }
//
//    @Override
//    public List<SysUser> findAllPhoGdChiNhanh(Long userId) {
//        return findInParentGroupByGroupTypeAndRole(userId, SysGroupType.CHI_NHANH.getValue(), SysRoleKey.PGD_CHI_NHANH.getValue());
//    }
//
//
//    @Override
//    public List<SysUser> findByGroupId(Long groupId) {
//        return repository.findByGroupId(groupId);
//    }
//
//    @Override
//    public List<SysUser> findNotInGroupId(Long groupId) {
//        return repository.findNotInGroupId(groupId);
//    }
//
//    @Override
//    public List<SysUser> findNotInGroupId(Long groupId, String username) {
//        return repository.findNotInGroupId(groupId, username);
//    }
//
//    @Override
//    public Boolean isUserIdInGroupId(Long groupId, Long userId) {
//        return repository.isUserIdInGroupId(groupId, userId);
//    }
//
//    @Override
//    public boolean checkExists(String username) {
//        if (StringUtil.isNullOrEmpty(username))
//            return false;
//        var dataDb = repository.findByUsername(username);
//        return dataDb != null;
//    }
//
//    @Override
//    public Boolean checkExists(SysUser data) {
//        var dataDb = repository.findByUsername(data.getUsername());
//        if (dataDb == null)
//            return false;
//
//        if (dataDb.getId().equals(data.getId())) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public List<SysUser> findManages(Long groupId) {
//        return repository.findManages(groupId);
//    }
//
//    @Override
//    public List<SysUser> findManagesHighLevel(Long groupId, Long userId, boolean forceJumpLevel) {
//        return repository.findManagesHighLevel(groupId, userId, forceJumpLevel);
//    }
//
//    @Override
//    public SysUser findByUsername(String username) {
//        return repository.findByUsername(username);
//    }
//
//    @Override
//    public List<SysUser> findEmployeesByManagerId(Long userId) {
//        return repository.findEmployeesByManagerId(userId);
//    }
//
//    @Override
//    public boolean isLeader(Long userId) {
//        return repository.isLeader(userId);
//    }
//
//    @Override
//    public List<SysUser> getListUserApprovedByRoleAndUserCV(Long userStaffId, String roleStaffId, String roleApprovedId) {
//        return repository.getListUserApprovedByRoleAndUserCV(userStaffId, roleStaffId, roleApprovedId);
//    }
//
//    @Override
//    public List<SysUser> getListUserApprovedByListRoleAndUserCV(Long userStaffId, List<String> roleStaffKey, String roleApprovedKey) {
//        return repository.getListUserApprovedByListRoleAndUserCV(userStaffId, roleStaffKey, roleApprovedKey);
//    }
//
//    @Override
//    public List<SysUser> getListUserApproved(Long userStaffId, List<String> roleStaffKey, List<String> roleApprovedKey) {
//        return repository.getListUserApproved(userStaffId, roleStaffKey, roleApprovedKey);
//    }
//
//    @Override
//    @Transactional
//    public List<SysUser> updateRoleUser(List<SysUser> list) {
//        if (list == null || list.isEmpty()) {
//            return null;
//        }
//        list = repository.saveAll(list);
//        return list;
//    }
//
//    @Override
//    public List<SysUser> findByLikeUsername(String username) throws CustomServiceBusinessException {
//        List<SysUser> list = repository.findByUsernameLike("%" + username + "%");
//        if (list == null || list.isEmpty()) {
//            list = new ArrayList<>();
//            UserHcmResponse userHcmResponse = getUserHcmService.getCustomerHcm(username);
//            if (userHcmResponse != null && userHcmResponse.getData() != null) {
//                list = new ArrayList<>();
//                SysUser sysUser = new SysUser();
//                sysUser.setUsername(userHcmResponse.getData().getUserId());
//                sysUser.setFullname(userHcmResponse.getData().getFullName());
//                sysUser.setStatus("NORMAL");
//                repository.save(sysUser);
//                list.add(sysUser);
//            }
//        }
//        return list != null ? list.stream().filter(x -> x.getFullname() != null).collect(Collectors.toList()) : null;
//    }
//
//    @Override
//    public String getRoleByUserName(String username) throws CustomServiceBusinessException {
//        UserHcmResponse userHcmResponse = getUserHcmService.getCustomerHcm(username);
//        if (userHcmResponse != null && userHcmResponse.getData() != null) {
//            return userHcmResponse.getData().getJobName();
//        }
//        return null;
//    }
//
//    @Override
//    public String getBranchByUser(String username, String key) {
//        SysRole role = sysRoleService.findByKey(key);
//        if (role != null) {
//            return sysGroupFullService.getBranchCodeByRoleIdAndUserName(role.getId(), username);
//        }
//        return null;
//    }
//
//    @Override
//    public List<SysUser> getListUserPheDuyet(Long userId, String donViDinhGiaId) {
//        List<String> roleStaffKeys = new ArrayList<>();
//        List<SysUser> listUser = new ArrayList<>();
//        if (donViDinhGiaId.equals(DonViKhoiTao.MB.getValue())) {
//            roleStaffKeys.add(SysUserRole.MB_CHUYEN_VIEN_QLTSDB.getValue());
//            roleStaffKeys.add(SysUserRole.MB_ADMIN_QLTSDB.getValue());
//            listUser = repository.getListUserApprovedByListRoleAndUserCV(userId,
//                    roleStaffKeys, SysUserRole.MB_KIEM_SOAT_QLTSDB.getValue());
//        } else if (donViDinhGiaId.equals(DonViKhoiTao.MBAMC.getValue())) {
//            roleStaffKeys.add(SysUserRole.AMC_CVDG.getValue());
//            listUser = repository.getListUserApprovedByListRoleAndUserCV(userId,
//                    roleStaffKeys, SysUserRole.AMC_GDPD.getValue());
//        }
//
//        return listUser;
//    }
//}
//
