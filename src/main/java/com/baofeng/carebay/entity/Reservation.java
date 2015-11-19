package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：服务预约
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8826325277364628686L;

	/** 真实姓名 */
	private String name;
	/** 手机号码 */
	private String phone;
	/** 预约时间 */
	private Date reservaDT;
	/** 服务Id */
	private String params;
	/** 服务名称 */
	private String paramsName;
	/** 备注 */
	@Lob
	private String remarks;
	/** 用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 服务类型(0:护理套餐，1：我要瘦身) */
	private Integer type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getReservaDT() {
		return reservaDT;
	}

	public void setReservaDT(Date reservaDT) {
		this.reservaDT = reservaDT;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getParamsName() {
		return paramsName;
	}

	public void setParamsName(String paramsName) {
		this.paramsName = paramsName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
