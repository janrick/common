/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.provider;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.zjzmjr.security.web.authentication.exception.FormLoginException;
import com.zjzmjr.security.web.authentication.strategy.MD5WithSaltPasswordEncodeStrategy;
import com.zjzmjr.security.web.authentication.strategy.PasswordEncodeStrategy;
import com.zjzmjr.security.web.authentication.token.UsernamePasswordToken;
import com.zjzmjr.security.web.userdetails.UserLoginResult;
import com.zjzmjr.security.web.userdetails.UserLoginService;

/**
 * 用户名密码验证提供器
 * 
 * @author elliott
 * @version $Id: UsernamePasswordAuthenticationProvider.java, v 1.0 2013-12-25 下午9:12:56 elliott Exp $
 */
public class UsernamePasswordAuthenticationProvider implements
		AuthenticationProvider,InitializingBean{

	/**
	 * 密码加密策略
	 */
	private PasswordEncodeStrategy passwordStrategy = new MD5WithSaltPasswordEncodeStrategy();
	
	private UserLoginService userLoginService;
	/** 
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		if(!(authentication instanceof UsernamePasswordToken)){
			throw new FormLoginException("登录令牌类型错误");
		}
		UsernamePasswordToken token = (UsernamePasswordToken)authentication;
		
		UserLoginResult result = userLoginService.loadUser(token.getPrincipal(),token.getSource(), token.getToken());
		
		if(result!=null){
			if(!result.isAccountNonLocked()){
				throw new FormLoginException("帐户已冻结");
			}else if(!result.isEnabled()){
				throw new FormLoginException("帐户已注销");
			}else if(!result.isCredentialsNonExpired()){
				throw new FormLoginException("用户密码已过期");
			}else if(!result.isAccountNonExpired()){
				throw new FormLoginException("用户帐户已过期");
			}else if(!result.isSourceSecure()){
				throw new FormLoginException("用户来源限制");
			}else{
				if(result.isLoginNonExpired() || passwordStrategy.isPasswordValid(token.getCredentials(), result.getPassword())){
					// 当用户不需要手动登录或密码对时
					token.setAuthenticated(true);
					token.setAuthorities(result.getAuthorities());
					/**
					 * 用户名为用户ID唯一标识
					 */
					token.setPrincipal(Integer.toString(result.getUserId()));
					token.setName(result.getUsername());
					token.setCredentials("[protected]");
					// 公司或者组织id设置
					token.setCompanyId(result.getCompanyId());
				}else{
					token.setAuthenticated(false);
					throw new FormLoginException("用户名或密码错误");
				}
			}
		}else{
			throw new FormLoginException("登录失败");
		}
		return token;
	}

	/** 
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordToken.class);
	}

	public UserLoginService getUserLoginService() {
		return userLoginService;
	}

	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	public PasswordEncodeStrategy getPasswordStrategy() {
		return passwordStrategy;
	}

	public void setPasswordStrategy(PasswordEncodeStrategy passwordStrategy) {
		this.passwordStrategy = passwordStrategy;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(this.userLoginService==null){
			throw new IllegalArgumentException("userLoginService不能为空");
		}
	}
	
}
