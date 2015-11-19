package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：图片类型
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "serviceType")
public class ServiceType extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2388648450856239428L;
	/** 类型名称 */
	private String name;
	/** 显示顺序 */
	private Integer indexs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}
}
