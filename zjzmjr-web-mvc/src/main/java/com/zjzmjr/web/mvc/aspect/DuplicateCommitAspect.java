/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.web.mvc.aspect;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjzmjr.web.mvc.annotation.DuplicateCommit;
import com.zjzmjr.web.mvc.enums.DuplicateCommitActionEnum;
import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 表单重复提交，切面处理器
 * 
 * <p>该类会拦截带有{@link DuplicateCommit}注解的方法，然后读取注解的配置,并对放入和检查token作出相应的操作.</p>
 * <p>在{@link DuplicateCommit#action()}为{@link DuplicateCommitActionEnum#PUT_TOKEN}时，将一个UUID放入session;<br/>
 *   在{@link DuplicateCommit#action()}为{@link DuplicateCommitActionEnum#CHECK_TOKEN}时，将会把用户提交的token与session中保存的token比较，如果不相等，那么将错误信息写到：
 *   <ul>
 *      <li>1.用户的action方法的返回类型是{@link Void void}并且参数列表中包含有{@link HttpServletResponse response},那么错误信息会以Json的方式写入到{@link HttpServletResponse response}中</li>
 *      <li>2.用户的action方法的参数列表中包含{@link ModelMap},那么将会把错误信息写入{@link ModelMap}</li>
 *      <li>3.用户的action方法的参数列表中包含{@link HttpServletRequest request},那么将会把错误信息写入{@link HttpServletRequest request}</li>
 *   </ul>
 * </p>
 * 
 * @author elliott
 * @version $Id: DuplicateCommitInterceptor.java, v 1.0 2013-12-24 下午7:50:41 elliott Exp $
 */
@Aspect
public class DuplicateCommitAspect implements Ordered{
	private String formTokenField = "token";
	private String resultFalgField = "success";
	private String resultMsgField  = "resultMsg";
	private String errorMsg = "页面已过期";
	private String charset = "UTF-8";
	
	@Around("@annotation(com.zjzmjr.web.mvc.annotation.DuplicateCommit)")
	public Object invokeAround(ProceedingJoinPoint jp) throws Throwable{
		Method method = ((MethodSignature)jp.getSignature()).getMethod();
		
		HttpServletRequest req = null;
		HttpServletResponse resp = null;
        AbstractForm form = null;
		ModelMap model = null;
		
		
		//解析参数列表
		Object[] args = jp.getArgs();
		if(args!=null&&args.length>0){
			for(Object arg:args){
				if(arg instanceof HttpServletRequest){
					req = ((HttpServletRequest) arg);
				}else if(arg instanceof AbstractForm){
					form = (AbstractForm)arg;
				}else if(arg instanceof ModelMap){
					model = (ModelMap)arg;
				}else if(arg instanceof HttpServletResponse){
					resp = (HttpServletResponse)arg;
				}
			}
		}
		//获取注解
		DuplicateCommit dc = method.getAnnotation(DuplicateCommit.class);
		if(dc==null){
			throw new IllegalStateException("can not find a DuplicateCommit Annotation at action method["+method.toString()+"]");
		}
		
		if(req==null){
			if(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes){
				req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			}else{
				throw new RuntimeException("can not find a valid request at action method["+method.toString()+"] context");
			}
		}
		
		//如果是放入token
		if(DuplicateCommitActionEnum.PUT_TOKEN==dc.action()){
			putToken(req, model, dc.tokenName());
		}else{
			String token = null;
			if(form!=null){
				token = StringUtils.trimToNull(form.getToken());
			}else if(req!=null){
				token = StringUtils.trimToNull(req.getParameter(formTokenField));
			}
			if(token==null||!token.equals(req.getSession(true).getAttribute(dc.tokenName()))){//令牌错误
				if(String.class==method.getReturnType()&&model!=null&&form!=null){
					model.put("form", form);
				}
				
				//如果视图类型不是string，抛出异常
				if(StringUtils.isNotBlank(dc.value())&&String.class!=method.getReturnType()){
					throw new RuntimeException("controller无法返回视图");
				}
				
				//将错误信息写入模型
				setResultFailMsg(dc,method, req,resp, model);
				
				return String.class==method.getReturnType()?dc.value():null;
			}else{
				clearToken(req,dc.tokenName());
			}
		}
		return jp.proceed(jp.getArgs());
	}
	

	//将错误信息返回
	protected void setResultFailMsg(DuplicateCommit dc,Method method,HttpServletRequest req,HttpServletResponse resp,ModelMap model){
		//重新放置token
		putToken(req, model, dc.tokenName());
		
		//如果是void，那么ajax
		if(Void.TYPE==method.getReturnType()){
			if(resp!=null){ //如果带有response，那么将信息写入到response
				resp.setCharacterEncoding(charset);
				resp.setContentType("text/html");
				OutputStream outs = null;
				try {
					outs = resp.getOutputStream();
					outs.write(getJsonFailMsg(dc.errorMsg()).getBytes(charset));
				} catch (IOException e) {
				}finally{
					IOUtils.closeQuietly(outs);
				}
			}
		}else{
			if(model!=null){
				model.put(resultFalgField, false);
				model.put(resultMsgField, StringUtils.isBlank(dc.errorMsg())?errorMsg:StringUtils.trimToEmpty(errorMsg));
			}else if(req!=null){
				req.setAttribute(resultFalgField, false);
				req.setAttribute(resultMsgField, StringUtils.isBlank(dc.errorMsg())?errorMsg:StringUtils.trimToEmpty(errorMsg));
			}else{
				throw new RuntimeException("can not find ModelMap or Request to put the message from action method["+method.toString()+"]");
			}
		}
	}

	protected String putToken(HttpServletRequest req,ModelMap model,String tokenName){
		String token = UUID.randomUUID().toString();
		req.getSession(true).setAttribute(StringUtils.trimToEmpty(tokenName), token);
		req.setAttribute("token", token);
		
		if(model!=null){
			model.put("token", token);
		}
		return token;
	}
	
	protected void clearToken(HttpServletRequest req,String tokenName){
		if(req.getSession(false)!=null){
			req.getSession().removeAttribute(StringUtils.trimToEmpty(tokenName));
		}
	}
	
	protected String getJsonFailMsg(String customMsg){
		return "{\""+resultFalgField+"\":false,\""+resultMsgField+"\":\""+(StringUtils.isBlank(customMsg)?errorMsg:StringUtils.trimToEmpty(customMsg))+"\"}";
	}


	public String getFormTokenField() {
		return formTokenField;
	}


	public void setFormTokenField(String formTokenField) {
		this.formTokenField = formTokenField;
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


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public String getCharset() {
		return charset;
	}


	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
	
}
