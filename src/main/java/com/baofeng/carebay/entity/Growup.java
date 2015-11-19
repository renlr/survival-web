package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：成长分组
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "growup")
public class Growup extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -4549933030540048812L;

	/** 分组名称 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
