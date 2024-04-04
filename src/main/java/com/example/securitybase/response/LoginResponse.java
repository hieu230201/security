package com.example.securitybase.response;


import com.example.securitybase.model.administrator.UserRoleFunctionModel;

import java.util.List;

public class LoginResponse {
	private String access_token;
	private String refresh_token;
	private String token_type;
	private List<String> authorizes;
	private List<UserRoleFunctionModel> userRoleFunctionList;


private Long expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	private List<String> scopes;

	public List<String> getAuthorizes() {
		return authorizes;
	}

	public void setAuthorizes(List<String> authorizes) {
		this.authorizes = authorizes;
	}

	public List<UserRoleFunctionModel> getUserRoleFunctionList() {
		return userRoleFunctionList;
	}

	public void setUserRoleFunctionList(List<UserRoleFunctionModel> userRoleFunctionList) {
		this.userRoleFunctionList = userRoleFunctionList;
	}
}