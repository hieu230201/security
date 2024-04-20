package com.example.securitybase.model.atos.clones.bases;


import com.example.securitybase.entity.SysPermission;

public class SysPermissionAto extends AbstractAto<SysPermission, SysPermissionAto> {
    private String permissionKey;
    private String permissionName;
    private Boolean isActive;

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public Class<SysPermission> getEntityClass() {
        return SysPermission.class;
    }

    @Override
    public Class<SysPermissionAto> getAtoClass() {
        return SysPermissionAto.class;
    }
}
