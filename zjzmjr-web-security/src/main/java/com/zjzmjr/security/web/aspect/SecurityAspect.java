/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.aspect;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.enums.SecurityActionEnum;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.security.web.util.WebUtil;

/**
 * 安全检测切面
 * 
 * @author elliott
 * @version $Id: SecurityAspect.java, v 1.0 2013-12-26 上午1:13:19 elliott Exp $
 */
@Aspect
public class SecurityAspect implements Ordered{
	private String resultFalgField = "success";
	private String resultMsgField  = "resultMsg";
	private String charset = "UTF-8";
	
	private String defaultErrorView;
	
	private String unauthenticatedMsg = "未认证";
	private String untrustSourceMsg   = "不安全的IP地址";
	private String nopermissionMsg    = "权限不足";
	
	
	@Around("@annotation(com.zjzmjr.security.web.annotation.Security)")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
	  Method method = ((MethodSignature)jp.getSignature()).getMethod();
	  Security security = method.getAnnotation(Security.class);
	  if(security==null){
		  throw new IllegalStateException("can not find the annonation Security on the method ["+method.toString()+"]");
	  }
	  SecurityActionEnum action = security.action();
	  if(SecurityActionEnum.EXCEPTION==action){
		  actionException(jp, method, security);
	  }else{
		  HttpServletRequest req = null;
		  HttpServletResponse resp = null;
		  ModelMap model = null;
		  
		  if(jp.getArgs()!=null&&jp.getArgs().length>0){
			  for(Object arg:jp.getArgs()){
				  if(arg instanceof HttpServletRequest){
					  req = (HttpServletRequest) arg;
				  }else if(arg instanceof HttpServletResponse){
					  resp = (HttpServletResponse) arg;
				  }else if(arg instanceof ModelMap){
					  model = (ModelMap) arg;
				  }
			  }
		  }
		  if(req==null){
			  req = getRequest();
		  }
		  if(resp==null){
			  resp = getResponse();
		  }
		  
		  //如果不是controller或者可写入错误的来源都为空，那么抛出异常
		  if(!isController(method)||req==null&&resp==null&&model==null&&Void.class==method.getReturnType()){
			  actionException(jp, method, security);
		  }else{ //如果是控制器，并且有写入错误的模型，那么将错误信息写入模型，并尝试返回视图
			  boolean hasErr = false;
			  if(!checkAuthenticated(security)){
				  setResultFailMsg(method, req, resp, model, unauthenticatedMsg);
				  hasErr = true;
			  }else if(!checkSource(jp, method, security)){
				  setResultFailMsg(method, req, resp, model, untrustSourceMsg);
				  hasErr = true;
			  }else if(!checkAuth(jp, method, security)){
				  setResultFailMsg(method, req, resp, model, nopermissionMsg);
				  hasErr = true;
			  }
			  if(hasErr){
				  if(StringUtils.isNotBlank(security.view())&&String.class!=method.getReturnType()){
					  throw new RuntimeException("无法返回controller视图");
				  }
				  return Void.TYPE==method.getReturnType()?null:
					  StringUtils.isBlank(security.view())&&StringUtils.isNotBlank(defaultErrorView)?defaultErrorView:security.view();
			  }
		  }
	  }
	  return jp.proceed(jp.getArgs());	
	}
	
	protected void actionException(ProceedingJoinPoint jp,Method method,Security security){
		if(!checkAuthenticated(security)){
			throw new SecurityException(unauthenticatedMsg);
		}
		if(!checkSource(jp, method, security)){
			throw new SecurityException(untrustSourceMsg);
		}
		if(!checkAuth(jp, method, security)){
			throw new SecurityException(nopermissionMsg);
		}
	}
	
	/**
	 * 将错误信息放入适当的输出
	 * 
	 * @param method
	 * @param req
	 * @param resp
	 * @param model
	 * @param msg
	 * @return  是否是controller方法
	 */
	protected void setResultFailMsg(Method method,HttpServletRequest req,HttpServletResponse resp,ModelMap model,String msg){
		// 如果是void，那么ajax
		if (Void.TYPE == method.getReturnType()) {
			if (resp != null) { // 如果带有response，那么将信息写入到response
				resp.setCharacterEncoding(charset);
				resp.setContentType("text/html");
				OutputStream outs = null;
				try {
					outs = resp.getOutputStream();
					outs.write(getJsonFailMsg(msg).getBytes(charset));
				} catch (IOException e) {
				} finally {
					IOUtils.closeQuietly(outs);
				}
			}
		} else {
			if (model != null) {
				model.put(resultFalgField, false);
				model.put(resultMsgField, msg);
			} else if (req != null) {
				req.setAttribute(resultFalgField, false);
				req.setAttribute(resultMsgField, msg);
			}
		}
	}
	
	
	/**
	 * 检察源
	 * 
	 * @param jp
	 * @param method
	 * @param security
	 * @return
	 */
	protected boolean checkSource(ProceedingJoinPoint jp,Method method,Security security){
		if(!security.checkSource()){
			return true;
		}
		HttpServletRequest req = getRequest();
		return req!=null&& SpringSecurityUtil.isSourceMatch(WebUtil.getUserIp(req));
	}
	
	/**
	 * 检查权限
	 * 
	 * @param jp
	 * @param method
	 * @param security
	 * @return
	 */
	protected boolean checkAuth(ProceedingJoinPoint jp,Method method,Security security){
		String[] auths = security.auth();
		if(auths==null||auths.length==0){
			return true;
		}
		for(String auth:auths){
			if(SpringSecurityUtil.hasAuth(auth)){
				return true;
			}
		}
		return false;
	}
	
	
	protected String getJsonFailMsg(String msg){
		return "{\""+resultFalgField+"\":false,\""+resultMsgField+"\":\""+msg+"\"}";
	}
	
	/**
	 * 检查认证
	 * 
	 * @return
	 */
	protected boolean checkAuthenticated(Security security){
		return !security.value()||SpringSecurityUtil.isAuthenticated();
	}
	
	/**
	 * 是否是controller
	 * 
	 * @param method
	 * @return
	 */
	protected boolean isController(Method method){
		return method.getAnnotation(RequestMapping.class)!=null||method.getClass().getAnnotation(RequestMapping.class)!=null;
	}
	
	/**
	 * 从上下文中获取HttpRequest
	 * 
	 * @return
	 */
	private HttpServletRequest getRequest(){
		Object obj = RequestContextHolder.getRequestAttributes();
		if(obj instanceof ServletRequestAttributes){
			return ((ServletRequestAttributes)obj).getRequest();
		}
		return null;
	}
	
	/**
	 * 从上下文中获取HttpServletResponse
	 * 
	 * @return
	 */
	private HttpServletResponse getResponse(){
		Object obj = RequestContextHolder.getRequestAttributes();
		if(obj instanceof ServletWebRequest){
			return ((ServletWebRequest)obj).getResponse(); 
		}
		return null;
	}
	
	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}
	
	

	public String getResultFalgField() {
		return resultFalgField;
	}

	public void setResultFalgField(String resultFalgField) {
		this.resultFalgField = resultFalgField;
	}

	public String getResultMsgField() {
		return resultMsgField;
	}

	public void setResultMsgField(String resultMsgField) {
		this.resultMsgField = resultMsgField;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getUnauthenticatedMsg() {
		return unauthenticatedMsg;
	}

	public void setUnauthenticatedMsg(String unauthenticatedMsg) {
		this.unauthenticatedMsg = unauthenticatedMsg;
	}

	public String getUntrustSourceMsg() {
		return untrustSourceMsg;
	}

	public void setUntrustSourceMsg(String untrustSourceMsg) {
		this.untrustSourceMsg = untrustSourceMsg;
	}

	public String getNopermissionMsg() {
		return nopermissionMsg;
	}

	public void setNopermissionMsg(String nopermissionMsg) {
		this.nopermissionMsg = nopermissionMsg;
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
  
}
