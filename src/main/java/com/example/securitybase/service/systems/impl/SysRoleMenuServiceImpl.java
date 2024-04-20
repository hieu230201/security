package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysRoleMenu;
import com.example.securitybase.repository.systems.SysRoleMenuRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class SysRoleMenuServiceImpl extends AbstractGenericService<SysRoleMenu, Long> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuRepository repository;

    @Override
    protected JpaRepository<SysRoleMenu, Long> getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public Boolean toggleSave(SysRoleMenu data, Boolean isActive) {
        if (isActive == null || !isActive) {

            if (data.getId() != null && data.getId() != 0) {
                deleteById(data.getId());
                return false;
            }
            repository
                    .deleteByRoleIdAndMenuId(data.getRoleId(), data.getMenuId());

            return false;
        }
        repository
                .deleteByRoleIdAndMenuId(data.getRoleId(), data.getMenuId());

        repository.save(data);

        return true;
    }
}