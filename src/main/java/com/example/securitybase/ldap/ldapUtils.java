package com.example.securitybase.ldap;

import com.example.securitybase.logging.LogManage;
import com.example.securitybase.logging.bases.ILogManage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
/**
 * 
 * @author hieunt
 *
 */
@Component
public class ldapUtils {
	private static final ILogManage logger = LogManage.getLogManage(ldapUtils.class);

	@Value("${mb.ldap.url}")
	private String ldapUrl;

	@Value("${mb.ldapamc.url}")
	private String ldapAMC;

	public boolean isConnectLDAP(String username, String password) {
		DirContext ctx = null;
		try {
			String domainUser;
			String ldapString;
			if(username.toLowerCase().endsWith(".amc")){
				domainUser = "amc\\" + username;
				ldapString = ldapAMC;
			}
			else{
				domainUser = "bank\\" + username;
				ldapString = ldapUrl;
			}
			Hashtable<String, Object> env = new Hashtable<>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, ldapString);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, domainUser);
			env.put(Context.SECURITY_CREDENTIALS, password);
			ctx = new InitialDirContext(env);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("LDAP Connect Error: %s", e.getMessage()),e);
			return false;
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					logger.error(String.format("Close LDAP Error: %s", e.getMessage()),e);
				}
			}
		}
	}
}

