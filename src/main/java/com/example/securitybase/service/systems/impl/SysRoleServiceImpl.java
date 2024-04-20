package com.example.securitybase.service.systems.impl;

import com.example.securitybase.entity.SysRole;
import com.example.securitybase.model.atos.clones.bases.SysPermissionAto;
import com.example.securitybase.model.atos.clones.bases.SysRoleAto;
import com.example.securitybase.repository.systems.SysPermissionRepository;
import com.example.securitybase.repository.systems.SysRoleRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysRoleService;
import com.example.securitybase.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleServiceImpl extends AbstractGenericService<SysRole, Long> implements SysRoleService {

    @Autowired
    private SysRoleRepository repository;

    @Autowired
    private SysPermissionRepository permissionRepository;

    @Override
    protected JpaRepository<SysRole, Long> getRepository() {
        return repository;
    }

    @Override
    public List<SysRole> findByGroupId(Long groupId) {
        return repository.findByGroupId(groupId);
    }

    @Override
    public List<SysRole> findByPermissionId(Long permissionId) {
        return repository.findByPermissionId(permissionId);
    }


    @Override
    public SysRole findByKey(String key) {
        return repository.findByKey(key);
    }

    @Override
    public List<SysRoleAto> findByGroupId(Long groupId, Boolean includePermission) {
        var listResult = new ArrayList<SysRoleAto>();

        var roles = findByGroupId(groupId);
        if(roles == null || roles.isEmpty())
            return listResult;

        for (var sysRole : roles){
            var roleAto = new SysRoleAto().fromEntity(sysRole);

            if(includePermission != null && includePermission){
                List<SysPermissionAto> sysPermissionAtoes =
                        ModelMapperUtil
                                .listObjectToListModel(permissionRepository.
                                        findByGroupIdAndRoleId(groupId, roleAto.getId()), SysPermissionAto.class);
                roleAto.setSysPermissionAtoes(sysPermissionAtoes);
            }

            listResult.add(roleAto);
        }

        return listResult;


    }

    @Override
    public List<SysRole> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<SysRole> findByUserIdNew(Long userId) {
        return repository.findByUserIdNew(userId);
    }

    @Override
    public Boolean checkExists(SysRole data) {
        var dataDb = repository.findByKey(data.getKey());
        if(dataDb == null)
            return false;
        if(dataDb.getId().equals(data.getId())){
            return false;
        }
        return true;
    }

    @Override
    public List<String> findFunctionsByUserId(Long userId) {
        return repository.findFunctionsByUserId(userId);
    }

    @Override
    public List<String> findFunctionsByRoleId(Long roleId) {
        return repository.findFunctionsByRoleId(roleId);
    }

    @Override
    public boolean checkUserHasFunction(String functionName, Long userId) {
        return repository.checkUserHasFunction(functionName, userId);
    }

    @Override
    public boolean checkRoleHasFunction(String functionName, Long roleId) {
        return repository.checkRoleHasFunction(functionName, roleId);
    }
}