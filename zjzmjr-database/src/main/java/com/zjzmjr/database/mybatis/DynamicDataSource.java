package com.zjzmjr.database.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.zjzmjr.database.context.DataSourceContextHolder;

/**
 * 建立动态数据源
 * 
 * @author oms
 * @version $Id: DynamicDataSource.java, v 0.1 2018-3-7 下午5:46:48 oms Exp $
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // 在进行DAO操作前，通过上下文环境变量，获得数据源的类型
        return DataSourceContextHolder.getDataSourceType();
    }

}
