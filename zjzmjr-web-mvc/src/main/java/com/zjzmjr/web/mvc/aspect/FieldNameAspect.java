package com.zjzmjr.web.mvc.aspect;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.FieldSignature;
import org.springframework.core.Ordered;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjzmjr.common.annotation.FieldName;
import com.zjzmjr.common.annotation.FieldNameContextHolder;

/**
 * 
 * 获取类中属性字段的中英文名称
 * 
 * @author oms
 *
 */
@Aspect
@Deprecated
public class FieldNameAspect implements Ordered {

	private String resultFalgField = "success";

	private String resultMsgField = "resultMsg";

	private String errorMsg = "访问过于频繁";

//	private String charset = "UTF-8";

	private FieldNameContextHolder holder;

	/**
	 * 
	 * 
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.zjzmjr.common.annotation.FieldName)")
	public Object invokeAround(ProceedingJoinPoint jp) throws Throwable {
		Field field = ((FieldSignature) jp.getSignature()).getField();

		HttpServletRequest req = null;
		HttpServletResponse resp = null;
		// AbstractForm form = null;
		ModelMap model = null;

		// 解析参数列表
		Object[] args = jp.getArgs();
		if (args != null && args.length > 0) {
			for (Object arg : args) {
				if (arg instanceof HttpServletRequest) {
					req = ((HttpServletRequest) arg);
					// }else if(arg instanceof AbstractForm){
					// form = (AbstractForm)arg;
				} else if (arg instanceof ModelMap) {
					model = (ModelMap) arg;
				} else if (arg instanceof HttpServletResponse) {
					resp = (HttpServletResponse) arg;
				}
			}
		}
		// 获取注解
		FieldName beat = field.getAnnotation(FieldName.class);
		if (beat == null) {
			throw new IllegalStateException(
					"can not find a FieldName Annotation at class attribute["
							+ field.toString() + "]");
		}

		if (req == null) {
			if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes) {
				req = ((ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes()).getRequest();
			} else {
				throw new RuntimeException(
						"can not find a valid request at class attribute["
								+ field.toString() + "] context");
			}
		}

		try {

//			String fieldName = beat.name();
//			String fieldValue = beat.value();
//			holder.putFieldNameContext(jp.getClass().getName(), new  TripleValueHolder<String, String, String>(field.getName(), fieldName, fieldValue));
			
		} catch (IllegalArgumentException ex) {
			setResultFailMsg(beat, field, req, resp, model, ex.getMessage());
			// 失败之后跳转
			return String.class == field.getType() ? beat.value() : null;
		}

		return jp.proceed(jp.getArgs());
	}

	// 将错误信息返回
	protected void setResultFailMsg(FieldName dc, Field field,
			HttpServletRequest req, HttpServletResponse resp, ModelMap model,
			String errorInfo) {

		/*
		 * // 如果是void，那么ajax if (Void.TYPE == field.getType()) { if (resp !=
		 * null) { // 如果带有response，那么将信息写入到response
		 * resp.setCharacterEncoding(charset); resp.setContentType("text/html");
		 * OutputStream outs = null; try { outs = resp.getOutputStream();
		 * outs.write
		 * (getJsonFailMsg(StringUtils.isBlank(dc.name())?errorInfo:dc.
		 * name()).getBytes(charset)); } catch (IOException e) { } finally {
		 * IOUtils.closeQuietly(outs); } } } else { if (model != null) {
		 * model.put(resultFalgField, false); model.put(resultMsgField,
		 * StringUtils.isBlank(dc.name()) ? errorMsg :
		 * StringUtils.trimToEmpty(errorMsg)); } else if (req != null) {
		 * req.setAttribute(resultFalgField, false);
		 * req.setAttribute(resultMsgField, StringUtils.isBlank(dc.name()) ?
		 * errorMsg : StringUtils.trimToEmpty(errorMsg)); } else { throw new
		 * RuntimeException(
		 * "can not find ModelMap or Request to put the message from action method["
		 * + field.toString() + "]"); } }
		 */
	}

	protected String getJsonFailMsg(String customMsg) {
		return "{\""
				+ resultFalgField
				+ "\":false,\""
				+ resultMsgField
				+ "\":\""
				+ (StringUtils.isBlank(customMsg) ? errorMsg : StringUtils
						.trimToEmpty(customMsg)) + "\"}";
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

	public FieldNameContextHolder getHolder() {
		return holder;
	}

	public void setHolder(FieldNameContextHolder holder) {
		this.holder = holder;
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}

}
