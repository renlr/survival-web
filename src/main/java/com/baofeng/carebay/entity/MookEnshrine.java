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
 * 描述：期刊收藏
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "mookenshrine")
public class MookEnshrine extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7388825003477348384L;
	/** 用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 期刊 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "mook_id")
	private Mook mook;

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public Mook getMook() {
		return mook;
	}

	public void setMook(Mook mook) {
		this.mook = mook;
	}
}
