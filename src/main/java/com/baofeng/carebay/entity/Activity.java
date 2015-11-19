package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：最新活动
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "activity")
public class Activity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3653225204225556484L;
	/** 活动名称 */
	private String name;
	/** 活动摘要 */
	@Column(length = 1000)
	private String content;
	/** 开始时间 */
	private Date beginTime;
	/** 结束时间 */
	private Date endTime;
	/** 活动介绍 */
	@Column(length = 3000)
	private String details;
	/** 活动上线(0:下线,1:上线) */
	private Integer online;
	/** 定时时间 */
	@Column(length = 200)
	private String taskDT;
	@Transient
	private String chamber;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getTaskDT() {
		return taskDT;
	}

	public void setTaskDT(String taskDT) {
		this.taskDT = taskDT;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

}
