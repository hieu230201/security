package com.example.securitybase.controller.bases;

import com.example.securitybase.auth.beans.UserAuthProvider;
import com.example.securitybase.auth.models.UserPrincipal;
import com.example.securitybase.logging.LogManage;
import com.example.securitybase.logging.bases.ILogManage;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Locale;

public class BaseController {
	protected static final ILogManage logger = LogManage.getLogManage(BaseController.class);

	@Autowired
	UserAuthProvider userAuthProvider;

	protected UserPrincipal getUserPrincipal() {
		return userAuthProvider.getUserPrincipal();
	}
	protected String getUsername() {
		return userAuthProvider.getUsername().toLowerCase(Locale.ROOT);
	}

	protected Long getUserId() {
		return userAuthProvider.getUserId();
	}

}