/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.handler;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.zjzmjr.security.web.authentication.hook.FormAuthenticationSuccessHook;
import com.zjzmjr.security.web.authentication.token.UsernamePasswordToken;

/**
 * 用户登录成功处理器
 * 
 * @author elliott
 * @version $Id: FormAuthenticationSuccessHandler.java, v 1.0 2013-12-31 下午7:18:52 elliott Exp $
 */
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private FormAuthenticationSuccessHook successHook;
	
	public FormAuthenticationSuccessHandler(){}
	public FormAuthenticationSuccessHandler(String defaultTargetUrl){
		setDefaultTargetUrl(defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		if(this.successHook!=null){
			this.successHook.onSuccess(request, response, authentication);
		}
		if("AJAX".equalsIgnoreCase(request.getParameter("authencationType"))&&(authentication instanceof UsernamePasswordToken)){
			UsernamePasswordToken token = (UsernamePasswordToken)authentication;
			String respStr = "{\"authenticated\":"+token.isAuthenticated()+",\"username\":\""+(token.isAuthenticated()?token.getName():"")+"\",\"userId\":"+(token.isAuthenticated()?token.getPrincipal():"")+",\"entrance\":\""+getRedirectUrl(request, response)+"\"}";
			
			if(!response.isCommitted()){
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				OutputStream ous = response.getOutputStream();
				try {
					ous.write(respStr.getBytes(StringUtils.isNotBlank(request.getCharacterEncoding())?request.getCharacterEncoding():"UTF-8"));
				} catch (Exception e) {
				}
			}
			
		}else{
			String targetUrl = getRedirectUrl(request, response);
	        if (response.isCommitted()) {
	            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
	            return;
	        }
	        getRedirectStrategy().sendRedirect(request, response, targetUrl);
		}
	}
	
    protected String getRedirectUrl(HttpServletRequest request, HttpServletResponse response){
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrlParameter = getTargetUrlParameter();
		String redirectUrl = null;
		if (request.getSession().getAttribute("entrance") != null) {
			redirectUrl = (String) request.getSession().getAttribute("entrance");
			request.getSession().removeAttribute("entrance");
		} else if (savedRequest == null) {
			redirectUrl = determineTargetUrl(request, response);
		} else if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.isNotBlank(request.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);
			redirectUrl = determineTargetUrl(request, response);
		}
		clearAuthenticationAttributes(request);
		return redirectUrl == null ? savedRequest.getRedirectUrl() : redirectUrl;
    }
    
	public FormAuthenticationSuccessHook getSuccessHook() {
		return successHook;
	}
	public void setSuccessHook(FormAuthenticationSuccessHook successHook) {
		this.successHook = successHook;
	}
	
}
