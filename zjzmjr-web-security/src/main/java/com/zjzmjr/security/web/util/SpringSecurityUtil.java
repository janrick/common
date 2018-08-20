/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.zjzmjr.security.web.authentication.token.UsernamePasswordToken;
import com.zjzmjr.security.web.userdetails.UserLoginResult;

/**
 * 用户安全工具类
 * 
 * <p>该类主要结合SpringSecurity框架，提供：
 *     <ul>
 *        <li>检查是否含有权限</li>
 *        <li>获取登录名</li>
 *        <li>获取登录令牌</li>
 *        <li>获取登录源</li>
 *        <li>获取登录类型</li>
 *     </ul>
 * </p>
 * 
 * @author elliott
 * @version $Id: SpringSecurityUtil.java, v 1.0 2013-12-25 下午11:38:08 elliott Exp $
 */
public class SpringSecurityUtil {

	/**
	 * 用户是否已经认证
	 * 
	 * @return
	 */
	public static boolean isAuthenticated(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof UsernamePasswordToken || authentication instanceof CasAuthenticationToken){
			return authentication.isAuthenticated();
		}
		return  false ;
	}
	
	/**
	 * 是否有权限
	 * 
	 * @param auth  权限名称
	 * @return
	 */
	public static boolean hasAuth(String auth){
		if(StringUtils.isNotBlank(auth)){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication!=null){
				if(authentication.getAuthorities()!=null&&authentication.getAuthorities().size()>0){
					for(GrantedAuthority granted:authentication.getAuthorities()){
						if(auth.equals(granted.getAuthority())){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取登录用户名
	 * 
	 * @return
	 */
	public static String getUserName(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && (authentication instanceof UsernamePasswordToken || authentication instanceof CasAuthenticationToken)){
			return authentication.getName();
		}
		return null;
	}
	
	/**
	 * 获取登录令牌,一般指用户唯一标识
	 * 
	 * @return
	 */
	public static String getPrincipal(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication!=null && authentication instanceof CasAuthenticationToken){
			CasAuthenticationToken token = (CasAuthenticationToken)authentication;
			UserLoginResult principal = (UserLoginResult) token.getPrincipal();
			return String.valueOf(principal.getUserId());
		} else if(authentication instanceof UsernamePasswordToken){
			return (String)authentication.getPrincipal();
		}
		return null;
	}
	
	/**
	 * 获取数字类型的登录令牌
	 * 
	 * <p>如果令牌不是数字类型的，那么返回<code>null</code></p>
	 * 
	 * @return
	 */
	public static Integer getIntPrincipal(){
		String p = getPrincipal();
		if(p!=null&&p.matches("^\\d{1,}$")){
			return Integer.parseInt(p);
		}
		return null;
	}
	
	/**
	 * 获取源
	 * 
	 * @return
	 */
	public static String getSource(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null&&(authentication instanceof UsernamePasswordToken)){
			return ((UsernamePasswordToken)authentication).getSource();
		} else  if(authentication!=null&&(authentication instanceof CasAuthenticationToken)){
			CasAuthenticationToken casToken = (CasAuthenticationToken)authentication;
			String source = (String) casToken.getAssertion().getPrincipal().getAttributes().get("source");
			return source;
		}
		return null;
	}
	
	/**
	 * 获取公司或组织编号
	 * 
	 * @return
	 */
	public static String getCompany(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null&&(authentication instanceof UsernamePasswordToken)){
			return ((UsernamePasswordToken)authentication).getCompanyId();
		} else  if(authentication!=null&&(authentication instanceof CasAuthenticationToken)){
			CasAuthenticationToken casToken = (CasAuthenticationToken)authentication;
			String source = (String) casToken.getAssertion().getPrincipal().getAttributes().get("companyId");
			return source;
		}
		return null;
	}

	/**
	 * 获取数字类型的公司或组织编号
	 * 
	 * <p>如果令牌不是数字类型的，那么返回<code>null</code></p>
	 * 
	 * @return
	 */
	public static Integer getIntCompany(){
		String p = getCompany();
		if(p!=null&&p.matches("^\\d{1,}$")){
			return Integer.parseInt(p);
		}
		return null;
	}
	
	/**
	 * 获取类型
	 * 
	 * @return
	 */
	public static String getType(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null&&(authentication instanceof UsernamePasswordToken)){
			return ((UsernamePasswordToken)authentication).getType();
		} else if(authentication!=null&&(authentication instanceof CasAuthenticationToken)){
			CasAuthenticationToken casToken = (CasAuthenticationToken)authentication;
			String type = (String) casToken.getAssertion().getPrincipal().getAttributes().get("type");
			return type;
		}
		return null;
	}
	
	/**
	 * 登录源是否匹配
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isSourceMatch(String source){
		if(source!=null){
			return source.equals(getSource());
		}
		return false;
	}
	
	/**
	 * 登录类型是否匹配
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isTypeMatch(String type){
		if(type!=null){
			return type.equals(getType());
		}
		return false;
	}
}
