package com.example.securitybase.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SYS_ROLE_MENU")
public class SysRoleMenu {
    private Long id;
    private Long roleId;
    private Long menuId;

    @Id
    @GeneratedValue(generator = "SYS_ROLE_MENU_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SYS_ROLE_MENU_SEQ", sequenceName = "SYS_ROLE_MENU_SEQ",allocationSize=1)
    @Column(name = "ID")
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
    @Column(name = "MENU_ID")
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}