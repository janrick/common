/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;

import com.zjzmjr.security.web.util.WebUtil;

/**
 * ajax跳转
 * 
 * @author elliott
 * @version $Id: LoginUrlAuthenticationEntryPoint.java, v 1.0 2014-4-11 下午6:26:46 elliott Exp $
 */
public class LoginUrlAuthenticationEntryPoint extends org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint{
	public LoginUrlAuthenticationEntryPoint(String loginFormUrl){
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		String af = request.getParameter("__af");
		if("scriptRedirect".equals(af)||"scriptParentRedirect".equals(af)){
			String entrance = request.getParameter("entrance");
			if(!response.isCommitted()){
				OutputStream ous = null;
				try {
					ous = response.getOutputStream();
					String redirect = getRedirectUrl(request, response, authException);
					if(StringUtils.isNotBlank(entrance)){
						Map<String, String> param = new HashMap<String, String>(4);
						param.put("entrance", entrance);
						redirect = WebUtil.addQueryParameter(redirect, param);
					}
					ous.write(("<script>window"+("scriptRedirect".equals(af)?"":".parent")+".location.href='"+redirect+"';</script>").getBytes());
				}finally{
					IOUtils.closeQuietly(ous);
				}
			}
		}else{
			super.commence(request, response, authException);
		}
	}
	
	protected String getRedirectUrl(HttpServletRequest request,HttpServletResponse response,AuthenticationException authException)throws IOException, ServletException{
		if (isForceHttps() && "http".equals(request.getScheme())) {
			 return buildHttpsRedirectUrlForRequest(request);
		}
		return buildRedirectUrlToLoginPage(request, response, authException);
	}
	
	
}
