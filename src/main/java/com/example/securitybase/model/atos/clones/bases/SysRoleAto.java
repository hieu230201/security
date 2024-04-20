package com.example.securitybase.model.atos.clones.bases;


import com.example.securitybase.entity.SysRole;

import java.util.List;

public class SysRoleAto extends BaseAto<SysRole,SysRoleAto> {
    @Override
    public Class<SysRole> getEntityClass() {
        return SysRole.class;
    }

    @Override
    public Class<SysRoleAto> getAtoClass() {
        return SysRoleAto.class;
    }

    private String key;
    private String name;
    private Boolean isActive;
    private List<SysPermissionAto> sysPermissionAtoes;

    public List<SysPermissionAto> getSysPermissionAtoes() {
        return sysPermissionAtoes;
    }

    public void setSysPermissionAtoes(List<SysPermissionAto> sysPermissionAtoes) {
        this.sysPermissionAtoes = sysPermissionAtoes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
