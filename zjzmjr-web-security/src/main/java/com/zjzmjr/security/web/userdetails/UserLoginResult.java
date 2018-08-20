/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户登录结果
 * 
 * @author elliott
 * @version $Id: UserLoginResult.java, v 1.0 2013-12-31 下午1:55:01 elliott Exp $
 */
public class UserLoginResult implements UserDetails {
	private static final long serialVersionUID = -9060318500859237561L;
	private Integer userId;
	private Collection<? extends GrantedAuthority> authorities;
	private String password;
	private String username;
	private boolean accountNonExpired = false;
	private boolean accountNonLocked = false;
	private boolean credentialsNonExpired = false;
	private boolean sourceSecure = false;
	private boolean enabled = false;
	private boolean loginNonExpired = false;
	/** 公司或者组织id */
	private String companyId;
	
	public UserLoginResult(){}
	public UserLoginResult(Integer userId,Collection<? extends GrantedAuthority> authorities,
			String username,String password,boolean accountNonExpired,
			boolean accountNonLocked,boolean credentialsNonExpired,boolean enabled){
		this(userId, authorities, username, password, accountNonExpired, accountNonLocked, credentialsNonExpired,true,enabled);
	}
	
	public UserLoginResult(Integer userId,
			Collection<? extends GrantedAuthority> authorities,
			String username,String password,  boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialsNonExpired,
			boolean sourceSecure, boolean enabled) {
		super();
		this.userId = userId;
		this.authorities = authorities;
		this.password = password;
		this.username = username;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.sourceSecure = sourceSecure;
		this.enabled = enabled;
	}

	public UserLoginResult(Integer userId,
			Collection<? extends GrantedAuthority> authorities,
			String username,String password,  boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialsNonExpired,
			boolean sourceSecure, boolean enabled, boolean isAutoLogin) {
		super();
		this.userId = userId;
		this.authorities = authorities;
		this.password = password;
		this.username = username;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.sourceSecure = sourceSecure;
		this.enabled = enabled;
		this.loginNonExpired = isAutoLogin;
	}

	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public Integer getUserId(){
		return userId;
	}
	
	/** 
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/** 
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/** 
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/** 
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/** 
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/** 
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/** 
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}


	public boolean isSourceSecure() {
		return sourceSecure;
	}
	
	/**
	 * 是否不需要手动登录
	 * @return
	 */
	public boolean isLoginNonExpired() {
		return loginNonExpired;
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

}
