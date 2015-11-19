package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：问卷试题
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "questionnaire")
public class Questionnaire extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2532460502886262265L;

	/** 试题名称 */
	private String subject;
	/** 类型(1:单选,2:多选,3：填写) */
	private Integer type;
	/** 答案选项 */
	private String options;
	/** 显示顺序 */
	private Integer indexs;
	/** 上线（0:下线，1：上线） */
	private Integer online;
	/** 调查类型(1:饮食调查，2:服务调查) */
	private Integer inquiry;
	@Transient
	private String chamber;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getInquiry() {
		return inquiry;
	}

	public void setInquiry(Integer inquiry) {
		this.inquiry = inquiry;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

}
