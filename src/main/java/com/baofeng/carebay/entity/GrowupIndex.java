package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：成长指标
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "growupIndex")
public class GrowupIndex extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2170454355333654376L;
	/** 体重 */
	private String weight;
	/** 身高 */
	private String height;
	/** BMI */
	private String bmi;
	/** 分组 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "growup_id")
	private Growup growup;
	/** 宝宝 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "bay_id")
	private CareBay careBay;
	/** 用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 宝宝月数 */
	private Integer month;
	/** 测量日期 */
	private Date mentDT;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getBmi() {
		return bmi;
	}

	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	public Growup getGrowup() {
		return growup;
	}

	public void setGrowup(Growup growup) {
		this.growup = growup;
	}

	public CareBay getCareBay() {
		return careBay;
	}

	public void setCareBay(CareBay careBay) {
		this.careBay = careBay;
	}

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Date getMentDT() {
		return mentDT;
	}

	public void setMentDT(Date mentDT) {
		this.mentDT = mentDT;
	}
}
