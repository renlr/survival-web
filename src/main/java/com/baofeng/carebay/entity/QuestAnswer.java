package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：试题答案
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "questAnswer")
public class QuestAnswer extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2532460502886262265L;

	/** 试题答案（试题Id:答案,试题Id:答案,试题Id:答案） */
	@Lob
	private String answer;

	/** 调查类型(1:饮食调查，2:服务调查) */
	private Integer inquiry;

	/** 调查用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 问卷题目 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "quest_id")
	private Questionnaire quest;
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public Integer getInquiry() {
		return inquiry;
	}

	public void setInquiry(Integer inquiry) {
		this.inquiry = inquiry;
	}

	public Questionnaire getQuest() {
		return quest;
	}

	public void setQuest(Questionnaire quest) {
		this.quest = quest;
	}
}
