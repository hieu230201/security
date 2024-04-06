package com.example.securitybase.service.systems.impl;


import com.example.securitybase.entity.SysPermission;
import com.example.securitybase.repository.systems.SysPermissionRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SysPermissionServiceImpl extends AbstractGenericService<SysPermission, Long> implements SysPermissionService {

    @Autowired
    private SysPermissionRepository repository;

    @Override
    protected JpaRepository<SysPermission, Long> getRepository() {
        return repository;
    }

    @Override
    public Boolean checkExists(SysPermission data) {
        var dataDb = repository.findByPermissionKeyAndPermissionName(data.getPermissionKey(), data.getPermissionName());
        if(dataDb == null)
            return false;

        if(dataDb.getId().equals(data.getId())){
            return false;
        }
        return true;
    }

}
