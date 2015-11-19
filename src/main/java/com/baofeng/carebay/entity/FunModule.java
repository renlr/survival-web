package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：功能模块
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "funModule")
public class FunModule extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1571192633233822458L;
	/** 功能名称 */
	private String name;
	/** 功能key */
	private String vals;
	/** 显示顺序 */
	private Integer indexs;
	/** 首页显示（1:显示） */
	private Integer views;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVals() {
		return vals;
	}

	public void setVals(String vals) {
		this.vals = vals;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}
}
