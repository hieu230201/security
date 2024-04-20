package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysGroupMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysGroupMenuRepository extends JpaRepository<SysGroupMenu, Long>{
    List<SysGroupMenu> findByMenuId(Long menuId);
    void deleteByGroupIdAndMenuId(Long groupId, Long menuId);
}