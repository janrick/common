package com.zjzmjr.database.context;


/**
 * 获得和设置上下文环境 主要负责改变上下文数据源的名称
 * 
 * @author oms
 * @version $Id: DataSourceContextHolder.java, v 0.1 2018-3-7 下午5:47:11 oms Exp
 *          $
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>(); // 线程本地环境

    // 设置数据源类型
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    // 获取数据源类型
    public static String getDataSourceType() {
        return (String) contextHolder.get();
    }

    // 清除数据源类型
    public static void clearDataSourceType() {
        contextHolder.remove();
    }

}
