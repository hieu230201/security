package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysGroupFull;
import com.example.securitybase.exception.CustomServiceBusinessException;
import com.example.securitybase.repository.systems.SysGroupFullRepository;
import com.example.securitybase.repository.systems.SysPermissionRepository;
import com.example.securitybase.repository.systems.SysRoleRepository;
import com.example.securitybase.service.systems.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SysGroupFullServiceImpl extends AbstractGenericService<SysGroupFull, Long>
        implements SysGroupFullService {

    @Autowired
    private SysGroupFullRepository repository;

    @Autowired
    private SysRoleRepository roleRepository;

    @Autowired
    private SysPermissionRepository permissionRepository;

    @Autowired
    private SysGroupDetailRepository sysGroupDetailRepository;


    @Autowired
    private SysGroupRoleService sysGroupRoleService;


    @Autowired
    private SysGroupUserService sysGroupUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    protected JpaRepository<SysGroupFull, Long> getRepository() {
        return repository;
    }

    @Override
    public Boolean addNode(SysGroupFull node, SysGroupFull parentNode) {
        return repository.addNode(node, parentNode);
    }

    @Override
    public Boolean addNode(SysGroupFull node, Long parentId) {
        var parentNode = findById(parentId);
        return addNode(node, parentNode);
    }

    @Override
    public Boolean removeNode(SysGroupFull node) {
        if (node == null)
            return false;

        return repository.removeNode(node);
    }

    @Override
    public Boolean removeNode(Long nodeId) {
        if (nodeId == 1L)
            return false;
        var node = findById(nodeId);
        return repository.removeNode(node);
    }

    @Override
    public List<SysGroupFull> getAllParentNode(SysGroupFull node) {
        if (node == null)
            return new ArrayList<>();
        return repository.getAllParentNode(node);
    }

    @Override
    public List<SysGroupFull> getAllParentNode(Long nodeId) {
        if (nodeId == null || nodeId == 0)
            return new ArrayList<>();
        var node = findById(nodeId);
        return getAllParentNode(node);
    }

    @Override
    public List<SysGroupFull> getAllParentNodeByGroupType(SysGroupFull node, String groupTypeParent) {
        return repository.getAllParentNodeByGroupType(node, groupTypeParent);
    }

    @Override
    public SysGroupFull getParentNodeNearestByGroupType(SysGroupFull node, String groupTypeParent) {
        var parents = getAllParentNodeByGroupType(node, groupTypeParent);
        if (parents.isEmpty())
            return null;
        return parents.get(parents.size() - 1);
    }

    @Override
    public SysGroupFull getGroupChiNhanhByUserId(Long userId) {
        var currentGroup = getByUserId(userId);
        if (currentGroup == null || currentGroup.isEmpty())
            return null;
        return getParentNodeNearestByGroupType(currentGroup.get(0), SysGroupType.CHI_NHANH.getValue());
    }

    @Override
    public SysGroupFull getGroupChiNhanhByUserName(String username) {
        var currentGroup = getByUserName(username);
        if (currentGroup == null || currentGroup.isEmpty())
            return null;
        return getParentNodeNearestByGroupType(currentGroup.get(0), SysGroupType.CHI_NHANH.getValue());
    }

    @Override
    public SysGroupFull getParentNodeFurthestByGroupType(SysGroupFull node, String groupTypeParent) {
        var parents = getAllParentNodeByGroupType(node, groupTypeParent);
        if (parents.isEmpty())
            return null;
        return parents.get(0);
    }

    @Override
    public List<SysGroupFull> getAllChildNode(SysGroupFull parentNode) {
        if (parentNode == null)
            return new ArrayList<>();
        return repository.getAllChildNode(parentNode);
    }

    @Override
    public List<SysGroupFull> getAllChildNode(Long parentNodeId) {
        if (parentNodeId == null || parentNodeId == 0)
            return new ArrayList<>();
        var node = findById(parentNodeId);
        return getAllChildNode(node);
    }

    @Override
    public SysGroupFull findByKey(String key) {
        return repository.findByKey(key);
    }

    @UseSafeRunning
    @UseLogging
    public List<SysGroupFullAto> getFullGroupByUserId(Long userId, Boolean includePermission) {
        List<SysGroupFullAto> sysGroupAtoes = new ArrayList<>();
        var sysGroups = repository.findByUserId(userId);
        for (var sysGroup : sysGroups)//noinspection DuplicatedCode,DuplicatedCode
        {
            var sysGroupAto = new SysGroupFullAto();
            sysGroupAto = sysGroupAto.fromEntity(sysGroup);

            List<SysRoleAto> sysRoleAtoes = ModelMapperUtil.listObjectToListModel(roleRepository.findByGroupIdAndUserId(sysGroupAto.getId(), userId), SysRoleAto.class);

            if (includePermission != null && includePermission) {
                for (SysRoleAto sysRoleAto : sysRoleAtoes) {
                    List<SysPermissionAto> sysPermissionAtoes = ModelMapperUtil.listObjectToListModel(permissionRepository.findByGroupIdAndRoleId(sysGroupAto.getId(), sysRoleAto.getId()), SysPermissionAto.class);
                    sysRoleAto.setSysPermissionAtoes(sysPermissionAtoes);
                }
            }


            sysGroupAto.setSysRoleAtoes(sysRoleAtoes);

            sysGroupAtoes.add(sysGroupAto);
        }

        return sysGroupAtoes;
    }

    @Override
    public List<SysGroupFull> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<SysGroupFull> findByMenuId(Long menuId) {
        return repository.findByMenuId(menuId);
    }

    @Override
    public List<SysGroupFull> findNotInMenuId(Long menuId) {
        return repository.findNotInMenuId(menuId);
    }

    @Override
    public List<SysGroupFull> getByUserName(String userName) {
        return repository.getByUserName(userName);
    }

    @Override
    public List<SysGroupFull> getDonViCapCao(String donvi) {
        return repository.getDonViCapCap(donvi);
    }

    @Override
    public String getBranchCodeByRoleIdAndUserName(Long roleId, String userName) {
        return repository.findBranchCodeByRoleIdAndUserName(roleId, userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean moveDepartment(Long groupId) throws CustomServiceBusinessException {
        var sysGroupFull = repository.findById(groupId).orElse(null);
        if (Objects.isNull(sysGroupFull)) {
            throw new CustomServiceBusinessException(ErrorCode.NOT_FOUND_GROUP);
        }

        var objChiNhanhAll = repository.findByKey("233_BDH_ChiNhanh");
        if (Objects.isNull(objChiNhanhAll)) {
            throw new CustomServiceBusinessException(ErrorCode.NOT_FOUND_GROUP_PARENT_CN);
        }

        //cập nhật parent
        sysGroupFull.setParentId(objChiNhanhAll.getId());
        repository.save(sysGroupFull);

        //cập nhật lại parent và left right tree


        var sysGroupDetail = sysGroupDetailRepository.findByGroupId(groupId);
        sysGroupDetail.setCommittee("27"); // đưa về cấp CN
        sysGroupDetailRepository.save(sysGroupDetail);

        //Xóa các role hiện tại
        //sysGroupRoleService.deleteByGroupId(groupId);

        var rolePD = sysRoleService.findByKey("G1_MBCN_PD");


        var roleCV = sysRoleService.findByKey("G1_MBCN_CV");
        var roleKS = sysRoleService.findByKey("G1_MBCN_KIEMSOAT");
        List<Long> lstSub = new ArrayList<>();
        if (!Objects.isNull(roleCV)) {
            lstSub.add(roleCV.getId());
        }
        if (!Objects.isNull(roleKS)) {
            lstSub.add(roleKS.getId());
        }

        // P. Khách hàng Cá nhân
        SysGroupFull objCn = new SysGroupFull();
        objCn.setParentId(groupId);
        objCn.setKey(sysGroupFull.getKey() + "_Phong_KHCN");
        objCn.setName("P. Khách hàng Cá nhân");
        objCn.setParentId(groupId);
        repository.save(objCn);

        SysGroupDetail objCnD = new SysGroupDetail();
        objCnD.setGroupId(objCn.getId());
        objCnD.setGroupType("2");
        objCnD.setCommittee("26");
        objCnD.setBranchCode(sysGroupDetail.getBranchCode());
        objCnD.setRefTinhCode(sysGroupDetail.getRefTinhCode());
        objCnD.setRefHuyenCode(sysGroupDetail.getRefHuyenCode());
        objCnD.setRefXaCode(sysGroupDetail.getRefXaCode());
        objCnD.setMaDvtt(sysGroupDetail.getMaDvtt());
        sysGroupDetailRepository.save(objCnD);


        // P. Hỗ trợ
        SysGroupFull objHt = new SysGroupFull();
        objHt.setParentId(groupId);
        objHt.setKey(sysGroupFull.getKey() + "_Phong_Ho_tro");
        objHt.setName("P. Hỗ trợ");
        objHt.setParentId(groupId);
        repository.save(objHt);

        SysGroupDetail objHtD = new SysGroupDetail();
        objHtD.setGroupId(objHt.getId());
        objHtD.setGroupType("2");
        objHtD.setCommittee("26");
        objHtD.setBranchCode(sysGroupDetail.getBranchCode());
        objHtD.setRefTinhCode(sysGroupDetail.getRefTinhCode());
        objHtD.setRefHuyenCode(sysGroupDetail.getRefHuyenCode());
        objHtD.setRefXaCode(sysGroupDetail.getRefXaCode());
        objHtD.setMaDvtt(sysGroupDetail.getMaDvtt());
        sysGroupDetailRepository.save(objHtD);

        // P. Khách hàng DN
        SysGroupFull objDn = new SysGroupFull();
        objDn.setParentId(groupId);
        objDn.setKey(sysGroupFull.getKey() + "_Phong_KHDN");
        objDn.setName("P. Khách hàng DN");
        objDn.setParentId(groupId);
        repository.save(objDn);

        SysGroupDetail objDnD = new SysGroupDetail();
        objDnD.setGroupId(objDn.getId());
        objDnD.setGroupType("2");
        objDnD.setCommittee("26");
        objDnD.setBranchCode(sysGroupDetail.getBranchCode());
        objDnD.setRefTinhCode(sysGroupDetail.getRefTinhCode());
        objDnD.setRefHuyenCode(sysGroupDetail.getRefHuyenCode());
        objDnD.setRefXaCode(sysGroupDetail.getRefXaCode());
        objDnD.setMaDvtt(sysGroupDetail.getMaDvtt());
        sysGroupDetailRepository.save(objDnD);

        if (lstSub.size() > 0) {
            sysGroupRoleService.save(objCn.getId(), lstSub);
            sysGroupRoleService.save(objDn.getId(), lstSub);
        }

        sysGroupRoleService.updateByGroupId(groupId, objHt.getId());
        sysGroupUserService.updateByGroupId(groupId, objHt.getId());

        if (!Objects.isNull(rolePD)) {
            List<Long> lst = new ArrayList<>();
            lst.add(rolePD.getId());
            sysGroupRoleService.save(groupId, lst);
        }
        return true;
    }

    @Override
    public List<SysUserModel> findUserDetail(Long id) {
        return repository.findUserDetail(id);
    }
}