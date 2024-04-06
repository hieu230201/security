package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysGroupUser;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysGroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysGroupUserServiceImpl extends AbstractGenericService<SysGroupUser, Long> implements SysGroupUserService {

    @Autowired
    private SysGroupUserRepository repository;

    @Override
    protected JpaRepository<SysGroupUser, Long> getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public Long deleteByGroupIdAndRoleIdAndUserId(Long groupId, Long roleId, Long userId) {
        return repository.deleteByGroupIdAndRoleIdAndUserId(groupId, roleId, userId);
    }

    @Override
    @Transactional
    public SysGroupUser findByGroupIdAndRoleIdAndUserId(Long groupId, Long roleId, Long userId) {
        return repository.findByGroupIdAndRoleIdAndUserId(groupId, roleId, userId);
    }

    @Override
    @Transactional
    public void updateByGroupId(Long groupId, Long newGroupId) {
        repository.updateByGroupId(groupId,newGroupId);
    }
}
