package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, Long> {
    Long deleteByRoleIdAndMenuId(Long roleId, Long menuId);
}
