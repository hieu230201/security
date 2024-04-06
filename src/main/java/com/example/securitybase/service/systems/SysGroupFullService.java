package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysGroupFull;
import com.example.securitybase.exception.CustomServiceBusinessException;

import java.util.List;

public interface SysGroupFullService extends GenericSevice<SysGroupFull, Long> {
    Boolean addNode(SysGroupFull node, SysGroupFull parentNode);

    Boolean addNode(SysGroupFull node, Long parentId);

    Boolean removeNode(SysGroupFull node);

    Boolean removeNode(Long nodeId);

    List<SysGroupFull> getAllParentNode(SysGroupFull node);

    List<SysGroupFull> getAllParentNode(Long nodeId);

    List<SysGroupFull> getAllParentNodeByGroupType(SysGroupFull node, String groupTypeParent);

    SysGroupFull getParentNodeNearestByGroupType(SysGroupFull node, String groupTypeParent);

    SysGroupFull getGroupChiNhanhByUserId(Long userId);

    SysGroupFull getGroupChiNhanhByUserName(String username);

    SysGroupFull getParentNodeFurthestByGroupType(SysGroupFull node, String groupTypeParent);

    List<SysGroupFull> getAllChildNode(SysGroupFull parentNode);

    List<SysGroupFull> getAllChildNode(Long parentNodeId);

    SysGroupFull findByKey(String key);

    List<SysGroupFullAto> getFullGroupByUserId(Long userId, Boolean includePermission);

    List<SysGroupFull> getByUserId(Long userId);

    List<SysGroupFull> findByMenuId(Long menuId);

    List<SysGroupFull> findNotInMenuId(Long menuId);

    List<SysGroupFull> getByUserName(String userName);

    List<SysGroupFull> getDonViCapCao(String donvi);

    String getBranchCodeByRoleIdAndUserName(Long roleId, String userName);

    boolean moveDepartment(Long groupId) throws CustomServiceBusinessException;

    List<SysUserModel> findUserDetail(Long id);
}