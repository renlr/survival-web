package com.baofeng.utils;

/**
 * 查询条件
 * 
 * @author RENLIANGRONG
 * 
 */
public class SearchRule {

	private Object data; // 选择的查询值

	private String field; // 查询字段

	private String op; // 查询操作

	private String conj; // 条件关系

	public SearchRule() {
		super();
	}

	public SearchRule(String field, String op, String data) {
		super();
		this.field = field;
		this.op = op;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getField() {
		return field;
	}

	public String getOp() {
		return op;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getConj() {
		return conj;
	}

	public void setConj(String conj) {
		this.conj = conj;
	}
}