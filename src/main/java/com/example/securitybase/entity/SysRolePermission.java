package com.example.securitybase.entity;

import javax.persistence.*;

@Entity
@Table(name = "SYS_ROLE_PERMISSION")
public class SysRolePermission {
    private Long id;
    private Long roleId;
    private Long permissionId;

    @Id
    @GeneratedValue(generator = "SYS_ROLE_PERMISSION_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SYS_ROLE_PERMISSION_SEQ", sequenceName = "SYS_ROLE_PERMISSION_SEQ",allocationSize=1)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ROLE_ID")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "PERMISSION_ID")
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

}