package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：推荐列表
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "recductList")
public class RecductList extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4618555361543833866L;
	/** 列表名称 */
	private String name;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
