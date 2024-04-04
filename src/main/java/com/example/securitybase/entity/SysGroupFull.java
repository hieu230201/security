package com.example.securitybase.entity;


import com.example.securitybase.entity.base.BaseEntity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

/**
 * @author caunv
 * @created_date 02/04/2021 - 3:23 PM
 */
@Entity
@Table(name = "SYS_GROUP_FULL")
public class SysGroupFull extends BaseEntity {
    private Long id;
    private String key;
    private String name;
    private Long left;
    private Long right;
    private Long parentId;
    private String soTaiKhoan;

    @Id
    @GeneratedValue(generator = "SYS_GROUP_FULL_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SYS_GROUP_FULL_SEQ", sequenceName = "SYS_GROUP_FULL_SEQ",allocationSize=1)
    @Column(name = "ID", nullable = false, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "KEY", nullable = true, length = 200, updatable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 500)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "LEFT", nullable = true, precision = 0)
    public Long getLeft() {
        return left;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    @Basic
    @Column(name = "RIGHT", nullable = true, precision = 0)
    public Long getRight() {
        return right;
    }

    public void setRight(Long right) {
        this.right = right;
    }

    @Basic
    @Column(name = "PARENT_ID", nullable = true, precision = 0)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    @Basic
    @Column(name = "SO_TAI_KHOAN", nullable = true, length = 200)
    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }
}

