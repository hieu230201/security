package com.example.securitybase.entity;


import com.example.securitybase.entity.base.BaseEntity;
import com.example.securitybase.entity.base.EntityWithActiveField;

import javax.persistence.*;

@Entity
@Table(name = "SYS_ROLE")
public class SysRole extends BaseEntity implements EntityWithActiveField {
    /**
	 * 
	 */
	private Long id;
    private String key;
    private String name;
    private Boolean isActive;
    private Boolean isManage;


    @Id
   	@GeneratedValue(generator = "SYS_ROLE_SEQ", strategy = GenerationType.SEQUENCE)
   	@SequenceGenerator(name = "SYS_ROLE_SEQ", sequenceName = "SYS_ROLE_SEQ",allocationSize=1)
   	@Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "KEY")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "IS_ACTIVE")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    @Basic
    @Column(name = "IS_MANAGE")
    public Boolean getManage() {
        return isManage;
    }

    public void setManage(Boolean manage) {
        isManage = manage;
    }

}

