package com.baofeng.utils;

/**
 * @author RENLIANGRONG
 * 
 */
public class SearchOrder {
	// 排序的字段
	private String orderField;

	// 排序方式
	private String orderBy;

	public SearchOrder() {
		super();
	}
	public SearchOrder(String orderField, String orderBy) {
		super();
		this.orderField = orderField;
		this.orderBy = orderBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
}
