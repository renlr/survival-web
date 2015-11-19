package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：周期功能管理
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "weekService")
public class WeekService extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5971521789249741929L;

	/** 周期名称 */
	private Integer name;
	/** 上线(0:下线，1：上线) */
	private Integer online;

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

}
