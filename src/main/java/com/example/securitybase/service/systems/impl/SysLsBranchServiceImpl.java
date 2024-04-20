package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysLsBranch;
import com.example.securitybase.repository.systems.SysLsBranchRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysLsBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SysLsBranchServiceImpl extends AbstractGenericService<SysLsBranch, Long> implements SysLsBranchService {

    @Autowired
    private SysLsBranchRepository repository;

    @Override
    protected JpaRepository<SysLsBranch, Long> getRepository() {
        return repository;
    }
}
