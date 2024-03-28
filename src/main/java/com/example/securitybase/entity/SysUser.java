package com.example.securitybase.entity;

import com.example.securitybase.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "SYS_USER")
public class SysUser extends BaseEntity {
    /**
     *
     */
    private Long id;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String department;
    private String status;
    private Boolean ignoreLdap;

    private Long loginFalseTimes;
    private String hrsCode;
    private String password;
    private Boolean isAccountApp;

    @Id
    @GeneratedValue(generator = "SYS_USER_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SYS_USER_SEQ", sequenceName = "SYS_USER_SEQ",allocationSize=1)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USERNAME", length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "FULLNAME", length = 100)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
    @Column(name = "PHONE", length = 12)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "DEPARTMENT", length = 1000)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "STATUS", length = 50)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "IGNORE_LDAP")
    public Boolean getIgnoreLdap() {
        return ignoreLdap;
    }

    public void setIgnoreLdap(Boolean ignoreLdap) {
        this.ignoreLdap = ignoreLdap;
    }

    @Basic
    @Column(name = "LOGIN_FALSE_TIMES")
    public Long getLoginFalseTimes() {
        return loginFalseTimes;
    }

    public void setLoginFalseTimes(Long loginFalseTimes) {
        this.loginFalseTimes = loginFalseTimes;
    }

    @Basic
    @Column(name = "HRS_CODE")
    public String getHrsCode() {
        return hrsCode;
    }

    public void setHrsCode(String hrsCode) {
        this.hrsCode = hrsCode;
    }



    @Basic
    @Column(name = "PASSWORD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "IS_ACCOUNT_APP")
    public Boolean getAccountApp() {
        return isAccountApp;
    }

    public void setAccountApp(Boolean accountApp) {
        isAccountApp = accountApp;
    }


}

