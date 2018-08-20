/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 
 * @author hao
 * @version $Id: SessionInitialFilter.java, v 0.1 Jun 18, 2015 6:03:03 PM hao Exp $
 */
public class SessionInitialFilter extends OncePerRequestFilter {

    private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
    
    /** The name of the artifact parameter.  This is used to capture the session identifier. */
    private String artifactParameterName = "ticket";
    
    /** 
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        if ( CommonUtils.isNotBlank(CommonUtils.safeGetParameter(request, artifactParameterName))) {
            sessionStrategy.onAuthentication(null, request, response);
        }

        filterChain.doFilter(servletRequest, response);
    }

    
    /**
     * Getter method for property <tt>sessionStrategy</tt>.
     * 
     * @return property value of sessionStrategy
     */
    public SessionAuthenticationStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    
    /**
     * Setter method for property <tt>sessionStrategy</tt>.
     * 
     * @param sessionStrategy value to be assigned to property sessionStrategy
     */
    public void setSessionStrategy(SessionAuthenticationStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    
    /**
     * Getter method for property <tt>artifactParameterName</tt>.
     * 
     * @return property value of artifactParameterName
     */
    public String getArtifactParameterName() {
        return artifactParameterName;
    }

    
    /**
     * Setter method for property <tt>artifactParameterName</tt>.
     * 
     * @param artifactParameterName value to be assigned to property artifactParameterName
     */
    public void setArtifactParameterName(String artifactParameterName) {
        this.artifactParameterName = artifactParameterName;
    }
}
