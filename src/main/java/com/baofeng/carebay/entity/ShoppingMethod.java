package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：配送方式
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "shoppingMethod")
public class ShoppingMethod extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5059919024061378702L;

	/** 方式名称 */
	private String method;
	/** 是否默认(0:否，1：是) */
	private Integer defaults;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getDefaults() {
		return defaults;
	}

	public void setDefaults(Integer defaults) {
		this.defaults = defaults;
	}
}
