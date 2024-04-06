package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysGroupDetail;
import com.example.securitybase.service.systems.AbstractGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SysGroupDetailServiceImpl extends AbstractGenericService<SysGroupDetail, Long> implements SysGroupDetailService {

    @Autowired
    private SysGroupDetailRepository repository;

    @Override
    protected JpaRepository<SysGroupDetail, Long> getRepository() {
        return repository;
    }

    @Override
    public SysGroupDetail findByGroupId(Long groupId) {
        return repository.findByGroupId(groupId);
    }

    @Override
    public Long deleteByGroupId(Long groupId) {
        return repository.deleteByGroupId(groupId);
    }

}