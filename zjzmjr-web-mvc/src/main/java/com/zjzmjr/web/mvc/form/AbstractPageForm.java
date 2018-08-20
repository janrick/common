package com.zjzmjr.web.mvc.form;

/**
 * 具有分页功能的表单基础抽象类
 * 
 * @author oms
 *
 */
public abstract class AbstractPageForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3265935087251138563L;

	private final int PAGE_SIZE = 20;

	/**
	 * 当前页数
	 */
	private Integer page;

	/**
	 * 每页件数
	 */
	private Integer rows;

	/**
	 * 排序字段
	 */
	private String sort;

	/**
	 * 排序顺序
	 */
	private String order;

	public Integer getPage() {
		return page == null ? 1 : page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows == null ? PAGE_SIZE : rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
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

}
