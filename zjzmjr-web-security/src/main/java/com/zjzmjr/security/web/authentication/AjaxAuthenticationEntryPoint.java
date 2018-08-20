/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author elliott
 * @version $Id: AjaxAuthenticationEntryPoint.java, v 1.0 2014-12-15 下午1:07:16 elliott Exp $
 */
public class AjaxAuthenticationEntryPoint extends org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint{

	public AjaxAuthenticationEntryPoint(String loginFormUrl){
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		String flag = request.getParameter("__redirect");
		if(!"true".equalsIgnoreCase(flag)){
			if(!response.isCommitted()){
                response.setCharacterEncoding("UTF-8");
				OutputStream ous = null;
				try {
					ous = response.getOutputStream();
					ous.write("{\"code\":405,\"success\":false,\"resultMsg\":\"请先登录\"}".getBytes("UTF-8"));
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
