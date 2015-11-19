package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：会所管理
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "chamber")
public class Chamber extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6991056989827037332L;

	/** 会所名称 */
	private String name;
	/** 会报地址 */
	private String address;
	/** 联系电话 */
	private String telPhone;
	/** 负责人 */
	private String manager;
	/** 离所密码 */
	private String leavePWD;
	/** 重置密码 */
	private String resetPWD;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getLeavePWD() {
		return leavePWD;
	}

	public void setLeavePWD(String leavePWD) {
		this.leavePWD = leavePWD;
	}

	public String getResetPWD() {
		return resetPWD;
	}

	public void setResetPWD(String resetPWD) {
		this.resetPWD = resetPWD;
	}
}
