package com.baofeng.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索过滤器
 * @author RENLIANGRONG
 * 
 */
public class SearchFilter {
	
	 // 多字段查询时分组类型，主要是AND或者OR
	private String groupOp;
	
	// 多字段查询时候，查询条件的集合
	private List<SearchRule> rules = new ArrayList<SearchRule>(); 

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<SearchRule> getRules() {
		return rules;
	}

	public void setRules(List<SearchRule> rules) {
		this.rules = rules;
	}

}