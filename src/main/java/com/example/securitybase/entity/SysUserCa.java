package com.example.securitybase.entity;

import com.example.securitybase.entity.base.BaseEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_USER_CA")
public class SysUserCa extends BaseEntity {
    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private Boolean active;
    private byte[] signature;

    @Id
   	@GeneratedValue(generator = "sys_user_ca_seq", strategy = GenerationType.SEQUENCE)
   	@SequenceGenerator(name = "sys_user_ca_seq", sequenceName = "SYS_USER_CA_SEQ",allocationSize=1)
   	@Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_NAME", length = 200)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "FULL_NAME", length = 200)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "EMAIL", length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PHONE", length = 15)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "ACTIVE", nullable = true, precision = 0)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Lob
    @Basic
    @Column(name = "SIGNATURE", nullable = true)
    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}