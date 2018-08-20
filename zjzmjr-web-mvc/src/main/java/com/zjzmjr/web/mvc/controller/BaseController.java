/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.web.mvc.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjzmjr.common.json.DateJsonValueProcessor;
import com.zjzmjr.common.json.NumberJsonValueProcessor;
import com.zjzmjr.web.mvc.constants.WebConstants;
import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 控制器基类
 *   <p>该类约定了一些基本的响应格式：
 *      <ul>
 *         <li>响应的数据模型中有2个标记字段：1.'success'是否响应正常；2.'resultMsg'响应状态信息</li>
 *      </ul>
 *   </p>
 *   <p>该类实现的一些控制器通用的基本操作:
 *        <ul>
 *           <li>重定向分装</li>
 *           <li>向客户端写入json</li>
 *           <li>向客户端写入普通文本</li>
 *           <li>错误信息处理</li>
 *        </ul>
 *   </p>
 * 
 * @author elliott
 * @version $Id: BaseController.java, v 1.0 2013-12-25 上午10:57:47 elliott Exp $
 */
public class BaseController {
	/**
	 * 字段匹配
	 */
	private Pattern fieldPattern = Pattern.compile("\\{(\\w+)\\}");
	
	/**
	 * 编码
	 */
	private String encodeing = "UTF-8";
	
	/**
	 * 用户浏览器重定向
	 * 
	 * @param  url  重定向的URL
	 * @return
	 */
	protected String redirect(String url){
		return "redirect:"+StringUtils.trimToEmpty(url);
	}
	
	/**
	 * 以json方式返回结果
	 * 
	 * @param model   数据模型
	 * @param resp    Http响应
	 */
	protected void responseAsJson(Map<String, Object> model,HttpServletResponse resp){
		//resp.reset();
		if(!resp.isCommitted()){
			resp.setCharacterEncoding(encodeing);
			resp.setContentType("text/plain");
			
			OutputStream outs = null;
			try {
				outs = resp.getOutputStream();
				outs.write(JSONObject.fromObject(model).toString().getBytes(encodeing));
			} catch (IOException e) {
			}finally{
				IOUtils.closeQuietly(outs);
			}
		}
	}

	/**
	 * 以jsonp的形式返回数据， 
	 * 客户端jsonp的回调方法必须是callback， 如果不是callback的话，客户端将不能正确的获取数据
	 * 
	 * @param model
	 * @param resp
	 */
	protected void responseAsJsonp(Map<String, Object> model,
			HttpServletResponse resp) {
		// resp.reset();
		if (!resp.isCommitted()) {
			resp.setCharacterEncoding(encodeing);
			resp.setContentType("text/plain");

			OutputStream outs = null;
			try {
				outs = resp.getOutputStream();
				String jsonp = "callback(" + JSONObject.fromObject(model).toString() + ")";
				outs.write(jsonp.getBytes(encodeing));
			} catch (IOException e) {
			} finally {
				IOUtils.closeQuietly(outs);
			}
		}
	}

	/**
	 * 以json方式返回结果
	 * 
	 * @param json    json对象
	 * @param resp    Http响应
	 */
	protected void responseAsJson(HttpServletResponse resp,JSONObject json){
		//resp.reset();
		if(!resp.isCommitted()){
			resp.setCharacterEncoding(encodeing);
			resp.setContentType("text/plain");
			
			OutputStream outs = null;
			try {
				outs = resp.getOutputStream();
				outs.write(json.toString().getBytes(encodeing));
			} catch (IOException e) {
			}finally{
				IOUtils.closeQuietly(outs);
			}
		}
	}
	
	/**
	 * 响应处理结果信息
	 * 
	 * @param model
	 * @param resp
	 */
	protected void responseResultAsJson(ModelMap model,HttpServletResponse resp){
		if(!resp.isCommitted()){
			resp.setCharacterEncoding(encodeing);
			resp.setContentType("text/plain");
			
			OutputStream outs = null;
			try {
				JSONObject json = new JSONObject();
				json.put("success", model.get("success"));
				json.put("resultMsg", model.get("resultMsg"));
				
				outs = resp.getOutputStream();
				outs.write(json.toString().getBytes(encodeing));
			} catch (IOException e) {
			}finally{
				IOUtils.closeQuietly(outs);
			}
		}
	}
	
	/**
	 * 以普通文本方式响应
	 * 
	 * @param text   响应数据
	 * @param resp   Http响应
	 */
	protected void responseAsText(String text,HttpServletResponse resp){
		if(!resp.isCommitted()){
			resp.setCharacterEncoding(encodeing);
			resp.setContentType("text/html");
			
			OutputStream outs = null;
			try {
				outs = resp.getOutputStream();
				outs.write(StringUtils.trimToEmpty(text).getBytes(encodeing));
			} catch (IOException e) {
			}finally{
				IOUtils.closeQuietly(outs);
			}
		}
	}
	
	/**
	 * 向数据模型中写入成功信息
	 * 
	 * @param model  数据模型
	 * @param msg    信息
	 * @return
	 */
	protected Map<String, Object> putSuccess(Map<String, Object> model,String msg){
		return putSuccess(model, "0", msg);
	}
	
	protected Map<String, Object> putSuccess(Map<String, Object> model,String code,String msg){
		if(model!=null){
			model.put("success", true);
			model.put("code", StringUtils.trimToEmpty(code));
			model.put("resultMsg", StringUtils.trimToEmpty(msg));
		}
		return model;
	}
	
	/**
	 * 向数据模型中写入错误信息
	 * 
	 * @param model  数据模型
	 * @param msg    错误信息
	 * @return
	 */
	protected Map<String, Object> putError(Map<String, Object> model,String msg){
		return putError(model, "-1", msg);
	}
	
	/**
	 * 写入错误信息
	 * 
	 * @param model
	 * @param code	错误码
	 * @param msg
	 * @return
	 */
	public Map<String, Object> putError(Map<String, Object> model,String code,String msg){
		if(model!=null){
			model.put("success", false);
			model.put("resultMsg", StringUtils.trimToEmpty(msg));
			model.put("code", StringUtils.trimToEmpty(code));
		}
		return model;
	}
	
	/**
	 * 处理表单的绑定错误信息,将错误信息放入数据模型中
	 * <p>处理绑定错误信息，并返回第一个错误信息</p>
	 * 
	 * @param form    表单
	 * @param result  绑定结果
	 * @param model   数据模型
	 * @return		     是否有绑定错误,<code>true</code>表示有错误
	 */
	protected boolean resolveBindingError(AbstractForm form,BindingResult result,Map<String, Object> model){
		return resolveBindingError(form, result, model, false);
	}
	
	/**
	 * 处理表单的绑定错误信息,将错误信息放入数据模型中
	 * <p>处理绑定错误信息，并返回第一个错误信息</p>
	 * 
	 * @param form    表单
	 * @param result  绑定结果
	 * @param model   数据模型
	 * @return		     是否有绑定错误,<code>true</code>表示有错误
	 */
	protected boolean resolveBindingError(AbstractForm form,BindingResult result,ModelMap model){
		return resolveBindingError(form, result, model, false);
	}
	
	/**
	 * 处理表单绑定错误信息,将错误信息放入数据模型中
	 * 
	 * @param form     表单
	 * @param result   绑定结果
	 * @param model    数据模型
	 * @param fullMsg  是否全部信息,是指是否所有的错误信息都显示在结果中，如果为<code>false</code>那么字显示第一个绑定错误
	 * @return		        是否有绑定错误,<code>true</code>表示有错误
	 */
	protected boolean resolveBindingError(AbstractForm form,BindingResult result,Map<String, Object> model,boolean fullMsg){
		if(result!=null&&result.hasErrors()){
			model.put("success", false);
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errs = result.getAllErrors();
			for(int i=0;i<errs.size();i++){
				if(i>=1){
					if(!fullMsg){
						break;
					}
					sb.append(",");
				}
				ObjectError err = errs.get(i);
				
				/*
				 * 如果是类型转换错误
				 **/
				if("typeMismatch".equals(err.getCode())){
					String[] codes = err.getCodes();
					if(codes!=null&&codes.length>=2){
						String field = codes[1].replace("typeMismatch.", "");
						String humanField = form.resolveFiled(field);
						sb.append(humanField==null?field:humanField).append("格式错误！");
					}
				}else{
					String msg = err.getDefaultMessage();
					if(StringUtils.isNotBlank(msg)){
						Matcher m = fieldPattern.matcher(msg);
						if(m.find()){
							String field = m.group(1);
							String humanField = form.resolveFiled(field);
							msg = msg.replace("{"+field+"}", humanField==null?field:humanField);
						}
					}
					sb.append(msg);
				}
			}
			model.put("resultMsg", sb.toString());
			return true;
		}
		return false;
	}
	
	/**
	 * 往数据模型写入令牌
	 * 
	 * @param model			数据模型
	 * @param tokenName		token在session中的名称,null这为{@link WebConstants#DEFAULT_FORM_TOKEN_NAME}
	 * @return
	 */
	protected boolean resolveToken(Map<String, Object> model,String tokenName){
		if(model!=null && RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes){
			String token = UUID.randomUUID().toString();
			HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			req.getSession(true).setAttribute(StringUtils.isBlank(tokenName)?WebConstants.DEFAULT_FORM_TOKEN_NAME:StringUtils.trimToEmpty(tokenName), token);
			model.put("token", token);
			return true;
		}
		return false;
	}
	
	public String getEncodeing() {
		return encodeing;
	}

	public void setEncodeing(String encodeing) {
		this.encodeing = encodeing;
	}
	
	protected JsonConfig getJsonConfig(String datePattern){
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(StringUtils.isBlank(datePattern)?"yyyy-MM-dd":datePattern));
		return config;
	}
	
	protected JsonConfig getJsonConfig(String datePattern,String moneyPattern){
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(StringUtils.isBlank(datePattern)?"yyyy-MM-dd":datePattern));
		config.registerJsonValueProcessor(BigDecimal.class, new NumberJsonValueProcessor(StringUtils.isEmpty(moneyPattern)?"#,##0.##":moneyPattern));
		return config;
	}
	
}
