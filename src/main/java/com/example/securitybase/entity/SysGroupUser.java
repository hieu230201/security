package com.example.securitybase.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SYS_GROUP_USER")
public class SysGroupUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3556032667335113984L;
	private long id;
    private Long userId;
    private Long groupId;
    private Long roleId;

    @Id
   	@GeneratedValue(generator = "SYS_GROUP_USER_SEQ", strategy = GenerationType.SEQUENCE)
   	@SequenceGenerator(name = "SYS_GROUP_USER_SEQ", sequenceName = "SYS_GROUP_USER_SEQ",allocationSize=1)
   	@Column(name = "ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "GROUP_ID")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "ROLE_ID")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysGroupUser that = (SysGroupUser) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        return roleId != null ? roleId.equals(that.roleId) : that.roleId == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}