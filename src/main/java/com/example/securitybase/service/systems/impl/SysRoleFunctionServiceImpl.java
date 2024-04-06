package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysRoleFunction;
import com.example.securitybase.repository.systems.SysRoleFunctionRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysRoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleFunctionServiceImpl extends AbstractGenericService<SysRoleFunction, Long> implements SysRoleFunctionService {

    @Autowired
    private SysRoleFunctionRepository repository;

    @Override
    protected JpaRepository<SysRoleFunction, Long> getRepository() {
        return repository;
    }
}
