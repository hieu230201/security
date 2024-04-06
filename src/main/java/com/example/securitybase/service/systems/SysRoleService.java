package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysRole;

import java.util.List;

public interface SysRoleService extends GenericSevice<SysRole, Long> {
    List<SysRole> findByGroupId(Long groupId);
    List<SysRole> findByPermissionId(Long permissionId);
    SysRole findByKey(String key);

    List<SysRoleAto> findByGroupId(Long groupId, Boolean includePermission);
    List<SysRole> findByUserId(Long userId);
    List<SysRole> findByUserIdNew(Long userId);
    Boolean checkExists(SysRole data);

    List<String> findFunctionsByUserId(Long userId);
    List<String> findFunctionsByRoleId(Long roleId);
    boolean checkUserHasFunction(String functionName, Long userId);
    boolean checkRoleHasFunction(String functionName, Long roleId);
}