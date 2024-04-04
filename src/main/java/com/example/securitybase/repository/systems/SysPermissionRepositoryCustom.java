package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionRepositoryCustom{
    List<SysPermission> findByGroupId(Long groupId);
    List<SysPermission> findByGroupIds(List<Long> groupIds);
    List<SysPermission> findByGroupIdAndRoleId(Long groupId, Long roleId);
    List<SysPermission> findByUserId(Long userId);

}