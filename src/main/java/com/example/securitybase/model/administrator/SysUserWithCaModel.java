package com.example.securitybase.model.administrator;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class SysUserWithCaModel {
    private Long id;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String phoneCA;
    private String department;
    private String status;
    private Boolean ignoreLdap;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Boolean active;
    private byte[] signature;
    private String groupName;

    protected Date createdDate;
    protected String createdBy;
    protected Date modifiedDate;
    protected String modifiedBy;

    private MultipartFile[] files;
    private String hrsCode;
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIgnoreLdap() {
        return ignoreLdap;
    }

    public void setIgnoreLdap(Boolean ignoreLdap) {
        this.ignoreLdap = ignoreLdap;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    public byte[] getSignature() {
        if (signature != null && signature.length > 10)
            return signature;

        if (files != null && files.length > 0) {
            var file = files[0];
            try {
                return file.getBytes();
            } catch (Exception ex) {
                return new byte[0];
            }
        }

        return new byte[0];
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getHrsCode() {
        return hrsCode;
    }

    public void setHrsCode(String hrsCode) {
        this.hrsCode = hrsCode;
    }

    public String getPhoneCA() {
        return phoneCA;
    }

    public void setPhoneCA(String phoneCA) {
        this.phoneCA = phoneCA;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
