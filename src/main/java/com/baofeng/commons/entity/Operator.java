package com.baofeng.commons.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能：系统用户
 * */
@Entity
@Table(name = "operator")
@SuppressWarnings("serial")
public class Operator implements Serializable {

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/** 用户登录帐号 */
	private String accounts;
	/** 管理员 */
	private String name;
	/** 密码 */
	private String password;
	/** 联系电话 */
	private String phone;
	/** 电子邮件 */
	private String email;
	/** 用户类型（0：系统帐号，1：角色帐号） */
	private Integer type;
	/** 商家 */
	private String chamber;
	/** 接收服务 */
	private Integer callService;
	/** 帐号状态(0：正常，1：禁用) */
	private Integer status = 0;
	/** 删除(0:正常，1：删除) */
	private Integer delFlag = 0;
	/** 创建时间 */
	private Date createDt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCallService() {
		return callService;
	}

	public void setCallService(Integer callService) {
		this.callService = callService;
	}
}
