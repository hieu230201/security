package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysGroupMenu;

public interface SysGroupMenuService extends GenericSevice<SysGroupMenu, Long> {

    Boolean toggleSave(SysGroupMenu data, Boolean isActive);
}