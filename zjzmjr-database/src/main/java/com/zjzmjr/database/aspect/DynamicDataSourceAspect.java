package com.zjzmjr.database.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

import com.zjzmjr.database.annotation.DataSourceDetermin;
import com.zjzmjr.database.context.DataSourceContextHolder;

/**
 * 限定客户端对某个接口的访问次数
 * 
 * @author oms
 * @version $Id: HeartBeatAspect.java, v 0.1 2016-9-11 下午1:31:42 oms Exp $
 */
@Aspect
public class DynamicDataSourceAspect implements Ordered {

	private String resultFalgField = "success";

	private String resultMsgField = "resultMsg";

	private String errorMsg = "访问过于频繁";

	private String charset = "UTF-8";

	/**
	 * 这种方式也是可以的，但会获取接口的信息
	 */
	// @Pointcut(value = "execution(@DataSourceDetermin * *.*(..))")
	// public void invokePointcut(){}

	@Pointcut(value = "@annotation(com.zjzmjr.database.annotation.DataSourceDetermin)")
	public void invokePointcut() {
	}

	// @Pointcut(value =
	// "execution(* com.zjzmjr.core.base.mybatis.DynamicDataSource.*(..))")
	// public void invokePointcut(){
	// charset = "UTF-8";
	//
	// }

	/**
	 * 
	 * 
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	// @Around("@annotation(com.zjzmjr.database.annotation.DataSourceDetermin)")
	@Around("invokePointcut()")
	public Object invokeAround(ProceedingJoinPoint jp) throws Throwable {

		// Method method = ((MethodSignature) jp.getSignature()).getMethod(); //
		// 没有接口的时候采用这种方式
		Method method = getMethod(jp);

		// 获取注解
		DataSourceDetermin beat = method
				.getAnnotation(DataSourceDetermin.class);
		if (beat == null) {
			throw new IllegalStateException(
					"can not find a DataSourceDetermin Annotation at action method["
							+ method.toString() + "]");
		}

		try {

			String dataSource = beat.value();
			if (StringUtils.isNotBlank(dataSource)) {
				DataSourceContextHolder.setDataSourceType(dataSource);
			}
		} catch (IllegalArgumentException ex) {
			// 失败之后跳转
			return String.class == method.getReturnType() ? beat.value() : null;
		}

		return jp.proceed(jp.getArgs());
	}

	public Method getMethod(ProceedingJoinPoint pjp) {
		// 获取参数的类型
		Object[] args = pjp.getArgs();
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Method method = null;
		try {
			method = pjp.getTarget().getClass()
					.getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}
		return method;

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
		return HIGHEST_PRECEDENCE;
	}

}
