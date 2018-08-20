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
import org.springframework.security.cas.ServiceProperties;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 自动登陆过滤器
 * @author hao
 * @version $Id: AutoSignOnFilter.java, v 0.1 Jun 3, 2015 6:19:16 AM hao Exp $
 */
public class AutoSignOnFilter extends OncePerRequestFilter {
	
	private ServiceProperties serviceProperties;
	
	private String loginUrl;
	
    private String autoSignOnUri = "/autoSignOn";
	
	private String entraceParameter = "entrance";

	/** 
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
        if (request.getRequestURI().equals(autoSignOnUri)) {
			request.getSession().setAttribute("entrance", request.getParameter(entraceParameter));
			final String urlEncodedService = createServiceUrl(httpRequest, httpResponse);
	        final String redirectUrl = createRedirectUrl(urlEncodedService);
	        
	        httpResponse.sendRedirect(redirectUrl);
		} else {
			filterChain.doFilter(httpRequest, httpResponse);
		}
	}
	
	
	/**
     * Constructs the Url for Redirection to the CAS server.  Default implementation relies on the CAS client to do the bulk of the work.
     *
     * @param serviceUrl the service url that should be included.
     * @return the redirect url.  CANNOT be NULL.
     */
    protected String createRedirectUrl(final String serviceUrl) {
        return CommonUtils.constructRedirectUrl(this.loginUrl, this.serviceProperties.getServiceParameter(), serviceUrl, this.serviceProperties.isSendRenew(), false);
    }
	
	
    protected String createServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
        return constructServiceUrl(request, response,serviceProperties.getService(), true);
    }
    
    /**
     * 
     * 
     * @param response
     * @param service
     * @param encode
     * @return
     */
    public String constructServiceUrl(HttpServletRequest request, HttpServletResponse response,  String service,  boolean encode) {
    	
        if (CommonUtils.isNotBlank(service)) {
            return encode ? response.encodeURL(service) : service;
        }
       
        return "";
    }


	    /**
     * Getter method for property <tt>autoSignOnUri</tt>.
     * 
     * @return property value of autoSignOnUri
     */
    public String getAutoSignOnUri() {
        return autoSignOnUri;
    }


    /**
     * Setter method for property <tt>autoSignOnUri</tt>.
     * 
     * @param autoSignOnUri value to be assigned to property autoSignOnUri
     */
    public void setAutoSignOnUri(String autoSignOnUri) {
        this.autoSignOnUri = autoSignOnUri;
    }


    /**
     * Getter method for property <tt>serviceProperties</tt>.
     * 
     * @return property value of serviceProperties
     */
	public ServiceProperties getServiceProperties() {
		return serviceProperties;
	}

	/**
	 * Setter method for property <tt>serviceProperties</tt>.
	 * 
	 * @param serviceProperties value to be assigned to property serviceProperties
	 */
	public void setServiceProperties(ServiceProperties serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	/**
	 * Getter method for property <tt>loginUrl</tt>.
	 * 
	 * @return property value of loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setter method for property <tt>loginUrl</tt>.
	 * 
	 * @param loginUrl value to be assigned to property loginUrl
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}


	/**
	 * Getter method for property <tt>entraceParameter</tt>.
	 * 
	 * @return property value of entraceParameter
	 */
	public String getEntraceParameter() {
		return entraceParameter;
	}


	/**
	 * Setter method for property <tt>entraceParameter</tt>.
	 * 
	 * @param entraceParameter value to be assigned to property entraceParameter
	 */
	public void setEntraceParameter(String entraceParameter) {
		this.entraceParameter = entraceParameter;
	}



	

}
