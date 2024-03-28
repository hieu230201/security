package com.example.securitybase.entity.base;

import com.example.securitybase.auth.beans.UserAuthProvider;
import com.example.securitybase.util.BeanUtil;
import com.example.securitybase.util.StringUtil;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	//@CreatedDate
	//@Temporal(TemporalType.TIMESTAMP)
	@Basic
	@Column(name = "CREATED_DATE", updatable = false)
	protected Date createdDate;

	@CreatedBy
	@Basic
	@Column(name = "CREATED_BY", updatable = false)
	protected String createdBy;

	//@LastModifiedDate
	//@Temporal(TemporalType.TIMESTAMP)
	@Basic
	@Column(name = "MODIFIED_DATE")
	protected Date modifiedDate;

	@LastModifiedBy
	@Basic
	@Column(name = "MODIFIED_BY")
	protected String modifiedBy;

	@Basic
	@Column(name = "CREATED_DATE", updatable = false)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Basic
	@Column(name = "CREATED_BY", updatable = false)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Basic
	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	@Basic
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	private String myCurrentUsername(){
		UserAuthProvider userAuthProvider = BeanUtil.getBean(UserAuthProvider.class);
		if(userAuthProvider == null)
			return "";
		String username = userAuthProvider.getUsername();
		if(username == null)
			username = "";
		return username;
	}

	@PrePersist
	protected void prePersist() {
		if(this.createdDate == null)
			this.createdDate = new Date();
		if(this.modifiedDate == null)
			this.modifiedDate = new Date();

		if(StringUtil.isNullOrEmpty(this.createdBy)){
			String username = myCurrentUsername();
			this.createdBy = username;
			this.modifiedBy = username;
		}

	}
	@PreUpdate
	private void preUpdate(){
		this.modifiedDate = new Date();
		this.modifiedBy = myCurrentUsername();
	}

	@PreRemove
	protected void preRemove() {
		this.modifiedDate = new Date();
	}
}