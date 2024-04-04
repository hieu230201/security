package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuRepositoryCustom {
    List<SysMenu> findByUserId(Long userId);
    @Deprecated
    List<SysMenu> findByGroupId(Long groupId);
    @Deprecated
    List<SysMenu> findNotInGroupId(Long groupId);

    List<SysMenu> findByRoleId(Long roleId);
    List<SysMenu> findNotInRoleId(Long roleId);

}

