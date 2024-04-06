package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysGroupRole;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysGroupRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysGroupRoleServiceImpl extends AbstractGenericService<SysGroupRole, Long> implements SysGroupRoleService {

    @Autowired
    private SysGroupRoleRepository repository;

    @Override
    protected JpaRepository<SysGroupRole, Long> getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public void save(Long groupId, List<Long> roleIds) {
        repository.deleteByGroupId(groupId);

        List<SysGroupRole> sysGroupRoles = new ArrayList<>();

        for (Long roleId : roleIds) {
            SysGroupRole sysGroupRole = new SysGroupRole();
            sysGroupRole.setGroupId(groupId);
            sysGroupRole.setRoleId(roleId);
            sysGroupRoles.add(sysGroupRole);
        }
        repository.saveAll(sysGroupRoles);
    }


    @Override
    public void deleteByGroupId(Long groupId) {
        repository.deleteByGroupId(groupId);
    }

    @Override
    public void updateByGroupId(Long groupId, Long newGroupId) {
        repository.updateByGroupId(groupId, newGroupId);
    }
}

