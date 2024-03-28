package com.example.securitybase.entity;


import com.example.securitybase.entity.base.BaseEntity;
import com.example.securitybase.entity.base.EntityWithActiveField;

import javax.persistence.*;

@Entity
@Table(name = "SYS_PERMISSION")
public class SysPermission extends BaseEntity implements EntityWithActiveField {
    /**
	 * 
	 */
	private Long id;
    private String permissionKey;
    private String permissionName;
    private Boolean isActive;


    @Id
   	@GeneratedValue(generator = "SYS_PERMISSION_SEQ", strategy = GenerationType.SEQUENCE)
   	@SequenceGenerator(name = "SYS_PERMISSION_SEQ", sequenceName = "SYS_PERMISSION_SEQ",allocationSize=1)
   	@Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PERMISSION_KEY", length = 500)
    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    @Basic
    @Column(name = "PERMISSION_NAME", length = 500)
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Basic
    @Column(name = "IS_ACTIVE")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysPermission that = (SysPermission) o;

        if (id != that.id) return false;
        if (permissionKey != null ? !permissionKey.equals(that.permissionKey) : that.permissionKey != null)
            return false;
        if (permissionName != null ? !permissionName.equals(that.permissionName) : that.permissionName != null)
            return false;
        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (modifiedBy != null ? !modifiedBy.equals(that.modifiedBy) : that.modifiedBy != null) return false;
        return modifiedDate != null ? modifiedDate.equals(that.modifiedDate) : that.modifiedDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (permissionKey != null ? permissionKey.hashCode() : 0);
        result = 31 * result + (permissionName != null ? permissionName.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}