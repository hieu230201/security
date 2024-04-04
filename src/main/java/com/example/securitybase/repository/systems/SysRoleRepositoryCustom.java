package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepositoryCustom {
    List<SysRole> findByPermissionId(Long permissionId);
    List<SysRole> findByGroupIds(Long... groupIds);
    List<SysRole> findByGroupId(Long groupId);
    List<SysRole> findByGroupIdAndUserId(Long groupId, Long userId);
    List<SysRole> findByUserId(Long userId);
    List<SysRole> findByUserIdNew(Long userId);

    List<String> findFunctionsByUserId(Long userId);
    List<String> findFunctionsByRoleId(Long roleId);
    boolean checkUserHasFunction(String functionName, Long userId);
    boolean checkRoleHasFunction(String functionName, Long roleId);
}