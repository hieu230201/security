package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysRolePermission;

import java.util.List;

public interface SysRolePermissionService extends GenericSevice<SysRolePermission, Long> {

    Boolean toggleSave(SysRolePermission data, Boolean isActive);
}