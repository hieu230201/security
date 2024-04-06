package com.example.securitybase.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SYS_GROUP_MENU")
public class SysGroupMenu implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8314857800240492585L;
	private Long id;
    private Long menuId;
    private Long groupId;

    @Id
   	@GeneratedValue(generator = "SYS_GROUP_MENU_SEQ", strategy = GenerationType.SEQUENCE)
   	@SequenceGenerator(name = "SYS_GROUP_MENU_SEQ", sequenceName = "SYS_GROUP_MENU_SEQ",allocationSize=1)
   	@Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MENU_ID")
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "GROUP_ID")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysGroupMenu that = (SysGroupMenu) o;

        if (id != that.id) return false;
        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        return groupId != null ? groupId.equals(that.groupId) : that.groupId == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}