package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysRolePermission;
import com.example.securitybase.repository.systems.SysRolePermissionRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SysRolePermissionServiceImpl extends AbstractGenericService<SysRolePermission, Long> implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionRepository repository;

    @Override
    protected JpaRepository<SysRolePermission, Long> getRepository() {
        return repository;
    }


    @Override
    @org.springframework.transaction.annotation.Transactional
    public Boolean toggleSave(SysRolePermission data, Boolean isActive) {

        if(isActive == null || !isActive){

            if(data.getId() != null && data.getId() != 0){
                deleteById(data.getId());
                return false;
            }
            repository
                    .deleteByRoleIdAndPermissionId(data.getRoleId(), data.getPermissionId());

            return false;
        }
        repository
                .deleteByRoleIdAndPermissionId(data.getRoleId(), data.getPermissionId());

        repository.save(data);

        return true;
    }
}