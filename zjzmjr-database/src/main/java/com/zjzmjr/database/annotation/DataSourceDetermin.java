package com.zjzmjr.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源的定义, 根据注解的形式，确定系统里面到底是用哪个数据库的数据源
 * 
 * 假如在一个系统里面有多个数据库的时候。
 * 
 * @author oms
 * @version $Id: DataSourceDetermin.java, v 0.1 2018-3-19 下午1:18:15 oms Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceDetermin {

    /**
     * 根据指定的值来进行决定数据源
     * 
     * @return
     */
    public String value() default "";

    /**
     * 错误信息
     * 
     * @return
     */
    public String errorMsg() default "";
    
}
