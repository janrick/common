/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.token;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 默认HttpForm 表单登录Token
 * 
 * @author elliott
 * @version $Id: UsernamePasswordToken.java, v 1.0 2013-12-25 下午7:54:43 elliott
 *          Exp $
 */
public class UsernamePasswordToken implements Authentication {
	private static final long serialVersionUID = 2630767188012490327L;

	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 令牌,用户ID
	 */
	private String principal;
	/**
	 * 密码
	 */
	private String credentials;
	/**
	 * 来源,登录发起源，一般值IP
	 */
	private String source;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 客户端登录token
	 */
	private String token;

	/**
	 * 公司或者组织id
	 */
	private String companyId;

	private boolean authenticated = false;

	private Collection<? extends GrantedAuthority> authorities = Collections
			.emptyList();

	public UsernamePasswordToken(String principal, String credentials) {
		this.principal = principal;
		this.credentials = credentials;
	}

	public UsernamePasswordToken(String principal, String credentials,
			String source) {
		super();
		this.principal = principal;
		this.credentials = credentials;
		this.source = source;
	}

	public UsernamePasswordToken(String principal, String credentials,
			String source, String token) {
		super();
		this.principal = principal;
		this.credentials = credentials;
		this.source = source;
		this.token = token;
	}

	/**
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see org.springframework.security.core.Authentication#getAuthorities()
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public void setAuthorities(
			Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @see org.springframework.security.core.Authentication#getCredentials()
	 */
	@Override
	public String getCredentials() {
		return credentials;
	}

	/**
	 * @see org.springframework.security.core.Authentication#getDetails()
	 */
	@Override
	public Object getDetails() {
		return null;
	}

	/**
	 * @see org.springframework.security.core.Authentication#getPrincipal()
	 */
	@Override
	public String getPrincipal() {
		return principal;
	}

	/**
	 * @see org.springframework.security.core.Authentication#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	/**
	 * 发起源，一般指IP
	 * 
	 * @return
	 */
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 用户类型
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 客户端登录token值
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 公司或者组织id
	 * 
	 * @return
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * 公司或者组织id
	 * 
	 * @param companyId
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @see org.springframework.security.core.Authentication#setAuthenticated(boolean)
	 */
	@Override
	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

	@Override
	public String toString() {
		return "UsernamePasswordToken [name=" + name + ", principal="
				+ principal + ", credentials=" + credentials + ", source="
				+ source + ", type=" + type + ", token=" + token
				+ ", companyId=" + companyId + ", authenticated="
				+ authenticated + ", authorities=" + authorities + "]";
	}

}
