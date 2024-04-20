package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, Long> {
    Long deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
