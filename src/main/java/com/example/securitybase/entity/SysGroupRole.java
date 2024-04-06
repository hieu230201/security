package com.example.securitybase.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SYS_GROUP_ROLE")
public class SysGroupRole implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6022694996027970298L;
	private long id;
    private Long groupId;
    private Long roleId;

    @Id
   	@GeneratedValue(generator = "SYS_GROUP_ROLE_SEQ", strategy = GenerationType.SEQUENCE)
   	@SequenceGenerator(name = "SYS_GROUP_ROLE_SEQ", sequenceName = "SYS_GROUP_ROLE_SEQ",allocationSize=1)
   	@Column(name = "ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

        SysGroupRole that = (SysGroupRole) o;

        if (id != that.id) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        return roleId != null ? roleId.equals(that.roleId) : that.roleId == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
