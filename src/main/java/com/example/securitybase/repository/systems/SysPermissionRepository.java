package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission, Long>
        , SysPermissionRepositoryCustom,
        BaseActiveFieldRepository<SysPermission>
        {
    SysPermission findByPermissionKeyAndPermissionName(String permissionKey, String permissionName);

}