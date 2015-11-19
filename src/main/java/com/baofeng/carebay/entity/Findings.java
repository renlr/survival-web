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
 * 描述：调查结果
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "findings")
public class Findings extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4567599350344327374L;

	/** 调查用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 答案详细 */
	private String answerDetails;

	/** 问卷试题 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "quest_id")
	private Questionnaire quest;

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public String getAnswerDetails() {
		return answerDetails;
	}

	public void setAnswerDetails(String answerDetails) {
		this.answerDetails = answerDetails;
	}

	public Questionnaire getQuest() {
		return quest;
	}

	public void setQuest(Questionnaire quest) {
		this.quest = quest;
	}

}
