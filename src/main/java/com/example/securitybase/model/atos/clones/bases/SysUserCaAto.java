package com.example.securitybase.model.atos.clones.bases;

import com.example.securitybase.entity.SysUserCa;

import java.util.Base64;

public class SysUserCaAto extends BaseAto<SysUserCa, SysUserCaAto> {

	@Override
	public Class<SysUserCa> getEntityClass() {
		return SysUserCa.class;
	}

	@Override
	public Class<SysUserCaAto> getAtoClass() {
		return SysUserCaAto.class;
	}

	private String userName;
	private String fullName;
	private String email;
	private String phone;
	private Boolean active;
	private byte[] signature;
	private String signatureStr;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public String getSignatureStr() {
		if (signature != null && signature.length > 0)
			return Base64.getEncoder().encodeToString(signature);
		return signatureStr;
	}

	public void setSignatureStr(String signatureStr) {
		this.signatureStr = signatureStr;
	}

}
