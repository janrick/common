/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.handler;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.zjzmjr.security.web.authentication.hook.FormAuthenticationFailureHook;

/**
 * 用户登录失败处理器
 * 
 * @author elliott
 * @version $Id: FormAuthenticationFailureHandler.java, v 1.0 2014-1-1 下午7:58:44 elliott Exp $
 */
public class FormAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {
	
	private FormAuthenticationFailureHook failureHook;
	
	public FormAuthenticationFailureHandler(){}
	
	public FormAuthenticationFailureHandler(String defaultFailureUrl){
		super(defaultFailureUrl);
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		//登录失败处理
		if(failureHook!=null){
			failureHook.onFailure(request, response, exception);
		}
		if(!checkAndHandleAjax(request, response,exception)){
			super.onAuthenticationFailure(request, response, exception);
		}
	}
	
	private boolean checkAndHandleAjax(HttpServletRequest request,HttpServletResponse response,AuthenticationException exception)throws ServletException, IOException {
		if("AJAX".equalsIgnoreCase(request.getParameter("authencationType"))){
			String respStr = "{\"authenticated\":false,\"resultMsg\":\""+exception.getMessage()+"\"}";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			OutputStream ous = response.getOutputStream();
			try {
				ous.write(respStr.getBytes(StringUtils.isNotBlank(request.getCharacterEncoding())?request.getCharacterEncoding():"UTF-8"));
			} catch (Exception e) {
			}
			return true;
		}
		return false;
	}

	public FormAuthenticationFailureHook getFailureHook() {
		return failureHook;
	}

	public void setFailureHook(FormAuthenticationFailureHook failureHook) {
		this.failureHook = failureHook;
	}
	
}
