package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysMenu;
import com.example.securitybase.repository.systems.SysMenuRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends AbstractGenericService<SysMenu, Long> implements SysMenuService {

    @Autowired
    private SysMenuRepository repository;
    @Autowired
    private SysGroupMenuRepository groupMenuRepository;

    @Override
    protected JpaRepository<SysMenu, Long> getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public Boolean saveAndReplaceAll(List<SysMenu> sysMenus) {
        repository.deleteAll();
        repository.saveAll(sysMenus);
        return true;
    }

    @Override
    @Transactional
    public List<SysMenu> findAllByOrderBySort() {
        return repository.findAllByOrderBySort();
    }


    @Override
    public List<SysMenu> findByGroupId(Long groupId) {
        return repository.findByGroupId(groupId);
    }

    @Override
    public List<SysMenu> findNotInGroupId(Long groupId) {
        return repository.findNotInGroupId(groupId);
    }

    @Override
    public List<SysMenu> findByRoleId(Long roleId) {
        return repository.findByRoleId(roleId);
    }

    @Override
    public List<SysMenu> findNotInRoleId(Long roleId) {
        return repository.findNotInRoleId(roleId);
    }

}

