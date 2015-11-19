package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：消息中心
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "messageInbox")
public class MessageInbox extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7628220883775989753L;

	/** 消息内容 */
	@Column(length = 1100)
	private String message;
	/** 发送用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 阅读状态(0:未处理,1:已处理) */
	private Integer markRead;
	/** 意见类型 */
	private Integer msgtype;
	@Transient
	private String chamber;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public Integer getMarkRead() {
		return markRead;
	}

	public void setMarkRead(Integer markRead) {
		this.markRead = markRead;
	}

	public Integer getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(Integer msgtype) {
		this.msgtype = msgtype;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

}
