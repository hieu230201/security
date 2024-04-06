package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysRoleMenu;

public interface SysRoleMenuService extends GenericSevice<SysRoleMenu, Long> {
    Boolean toggleSave(SysRoleMenu data, Boolean isActive);
}