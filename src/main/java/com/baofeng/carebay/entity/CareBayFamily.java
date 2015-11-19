package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：家庭关系表
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "carebayFamily")
@SuppressWarnings("serial")
public class CareBayFamily extends BaseEntity implements Serializable {

	/** 关系称呼 */
	private String appellation;
	/** 关系描述 */
	private String description;
	/** 是否共享(0:共享，1：私有) */
	private Integer share;

	public String getAppellation() {
		return appellation;
	}

	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getShare() {
		return share;
	}

	public void setShare(Integer share) {
		this.share = share;
	}

}
