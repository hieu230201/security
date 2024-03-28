package com.example.securitybase.entity;


import com.example.securitybase.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "SYS_MENU")
public class SysMenu extends BaseEntity {
    /**
	 * 
	 */
	private long id;
    private String name;
    private String path;
    private String icon;
    private Long posIndex;
    private Long parentId;
    private Boolean active;
    private Boolean main;
    private String description;
    private Long sort;

    @Id
   	@Column(name = "ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", length = 500)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PATH", length = 500)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "ICON", length = 200)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "POS_INDEX")
    public Long getPosIndex() {
        return posIndex;
    }

    public void setPosIndex(Long posIndex) {
        this.posIndex = posIndex;
    }

    @Basic
    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "IS_ACTIVE")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "IS_MAIN")
    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    @Basic
    @Column(name = "DESCRIPTION", length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "SORT")
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}