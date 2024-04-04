package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, Long>, SysMenuRepositoryCustom {
    List<SysMenu> findAllByOrderBySort();
}