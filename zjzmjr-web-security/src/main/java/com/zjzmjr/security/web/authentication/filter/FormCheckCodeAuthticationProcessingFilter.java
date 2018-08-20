/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.zjzmjr.security.web.authentication.exception.CheckCodeException;
import com.zjzmjr.security.web.authentication.exception.FormLoginException;
import com.zjzmjr.security.web.authentication.strategy.CheckCodeStrategy;
import com.zjzmjr.security.web.authentication.strategy.NullCheckCodeStrategy;
import com.zjzmjr.security.web.authentication.token.UsernamePasswordToken;
import com.zjzmjr.security.web.util.WebUtil;

/**
 * 带有验证码验证功能的表单登录拦截器
 * 
 * <p>通过指定验证码的验证策略，来进行验证码验证</p>
 * 
 * @author elliott
 * @version $Id: FormCheckCodeAuthticationProcessingFilter.java, v 1.0 2013-12-25 下午7:19:38 elliott Exp $
 */
public class FormCheckCodeAuthticationProcessingFilter extends
		AbstractAuthenticationProcessingFilter {
	/**
	 * 验证码验证策略
	 */
	private CheckCodeStrategy checkCodeStrategy = new NullCheckCodeStrategy();
	
	/**
	 * 验证码字段名称
	 */
	private String codeField = "checkCode";
	/**
	 * 用户名字段名
	 */
	private String usernameField = "username";
	/**
	 * 密码字段名称
	 */
	private String passwordField  = "password";
	
	private String tokenField = "springtoken";
	
	protected FormCheckCodeAuthticationProcessingFilter(
			String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse resp) throws AuthenticationException,
			IOException, ServletException {
		if(!checkCodeStrategy.isCheckCodeValid(req, req.getParameter(codeField))){
			throw new CheckCodeException("验证码错误");
		}
		
		String username = StringUtils.trimToNull(req.getParameter(usernameField));
		String password = req.getParameter(passwordField);
		
		if(username==null || StringUtils.isEmpty(password)){
			throw new FormLoginException("用户名和密码不能为空");
		}
		
		String token = StringUtils.trimToNull(req.getParameter(tokenField));
		
//		// 根据ip地址来源
//		return getAuthenticationManager().authenticate(new UsernamePasswordToken(username, password,WebUtil.getUserIp(req)));
		return getAuthenticationManager().authenticate(new UsernamePasswordToken(username, password,WebUtil.getUserIp(req), token));
	}

	public CheckCodeStrategy getCheckCodeStrategy() {
		return checkCodeStrategy;
	}

	public void setCheckCodeStrategy(CheckCodeStrategy checkCodeStrategy) {
		this.checkCodeStrategy = checkCodeStrategy;
	}

	public String getCodeField() {
		return codeField;
	}

	public void setCodeField(String codeField) {
		this.codeField = codeField;
	}

	public String getUsernameField() {
		return usernameField;
	}

	public void setUsernameField(String usernameField) {
		this.usernameField = usernameField;
	}

	public String getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(String passwordField) {
		this.passwordField = passwordField;
	}

	public String getTokenField() {
		return tokenField;
	}

	public void setTokenField(String tokenField) {
		this.tokenField = tokenField;
	}
	

}
