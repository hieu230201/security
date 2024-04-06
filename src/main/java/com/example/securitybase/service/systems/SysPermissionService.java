package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysPermission;

import java.util.List;

public interface SysPermissionService extends GenericSevice<SysPermission, Long> {
    Boolean checkExists(SysPermission data);

}