package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysGroupMenu;
import com.example.securitybase.repository.systems.SysGroupMenuRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysGroupMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class SysGroupMenuServiceImpl extends AbstractGenericService<SysGroupMenu, Long> implements SysGroupMenuService {

    @Autowired
    private SysGroupMenuRepository repository;

    @Override
    protected JpaRepository<SysGroupMenu, Long> getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public Boolean toggleSave(SysGroupMenu data, Boolean isActive) {
        if (isActive == null || !isActive) {

            if (data.getId() != null && data.getId() != 0) {
                deleteById(data.getId());
                return false;
            }
            repository
                    .deleteByGroupIdAndMenuId(data.getGroupId(), data.getMenuId());

            return false;
        }
        repository
                .deleteByGroupIdAndMenuId(data.getGroupId(), data.getMenuId());

        repository.save(data);

        return true;
    }
}