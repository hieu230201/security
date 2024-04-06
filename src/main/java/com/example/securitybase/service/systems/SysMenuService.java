package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends GenericSevice<SysMenu, Long> {
    Boolean saveAndReplaceAll(List<SysMenu> sysMenus);
    List<SysMenu> findAllByOrderBySort();

    @Deprecated
    List<SysMenu> findByGroupId(Long groupId);
    @Deprecated
    List<SysMenu> findNotInGroupId(Long groupId);

    List<SysMenu> findByRoleId(Long roleId);
    List<SysMenu> findNotInRoleId(Long roleId);

}