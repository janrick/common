/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 本地登出成功后发送CAS登出通知
 * 
 * @author hao
 * @version $Id: CommonLogoutSuccessHandler.java, v 0.1 Jun 18, 2015 12:44:51 PM hao Exp $
 */
public class CasLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 登出成功转向的URL
     */
    private String logoutSuccessUrl;

    /**
     * 发送到CAS的登出命令
     */
    private String casLogoutUrl;

    /**
     * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.sendRedirect(StringUtils.trimToEmpty(casLogoutUrl) + "?service=" + StringUtils.trimToEmpty(logoutSuccessUrl));
    }

    /**
     * Getter method for property <tt>logoutSuccessUrl</tt>.
     * 
     * @return property value of logoutSuccessUrl
     */
    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    /**
     * Setter method for property <tt>logoutSuccessUrl</tt>.
     * 
     * @param logoutSuccessUrl
     *            value to be assigned to property logoutSuccessUrl
     */
    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    /**
     * Getter method for property <tt>casLogoutUrl</tt>.
     * 
     * @return property value of casLogoutUrl
     */
    public String getCasLogoutUrl() {
        return casLogoutUrl;
    }

    /**
     * Setter method for property <tt>casLogoutUrl</tt>.
     * 
     * @param casLogoutUrl
     *            value to be assigned to property casLogoutUrl
     */
    public void setCasLogoutUrl(String casLogoutUrl) {
        this.casLogoutUrl = casLogoutUrl;
    }

}
