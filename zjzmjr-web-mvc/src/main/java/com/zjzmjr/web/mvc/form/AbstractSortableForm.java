/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.web.mvc.form;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author elliott
 * @version $Id: AbstractSortableForm.java, v 1.0 2014-2-26 下午11:31:42 elliott Exp $
 */
public abstract class AbstractSortableForm extends AbstractForm {
	private static final long serialVersionUID = -8041235721541589818L;
	private String sort;
	private String order;
	
	/** 
	 * @see com.yztz.web.mvc.form.AbstractForm#resolveFiled(java.lang.String)
	 */
	@Override
	public String resolveFiled(String field) {
		return null;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	/**
	 * 解决数据库字段映射
	 * 
	 * <p>给出实体类的字段，返回可以排序的数据库字段</p>
	 * 
	 * @param field
	 * @return
	 */
	public abstract String resolveSortField(String field);
	
	/**
	 * 获取数据库字段主键
	 * 
	 * @return
	 */
	public abstract String getIdField();
	
	/**
	 * 是否可排序
	 * 
	 * @return
	 */
	public boolean isSortable(){
		return StringUtils.isNotBlank(resolveSortField(sort))&&("asc".equalsIgnoreCase(order)||"desc".equalsIgnoreCase(order));
	}
	
	/**
	 * 获取排序方式
	 * 
	 * @return
	 */
	public String getOrderBy(){
		if(isSortable()){
			return resolveSortField(sort)+" "+order.toUpperCase()+
					(StringUtils.isBlank(getIdField())||resolveSortField(sort).equalsIgnoreCase(getIdField())?"":","+getIdField()+" ASC");
		}
		return null;
	}

}
