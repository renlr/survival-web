package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：孕期贴士
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "gestationTips")
public class GestationTips extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2758396391114621312L;
	/** 贴士名称 */
	private String name;
	/** 周期 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "week_id")
	private WeekService week;
	/** 提示内容 */
	private String content;
	/** 上线(0:下线，1：上线) */
	private Integer online;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public WeekService getWeek() {
		return week;
	}

	public void setWeek(WeekService week) {
		this.week = week;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}
}
