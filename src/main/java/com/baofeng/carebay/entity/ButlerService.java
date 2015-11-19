package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：管家服务
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "butlerService")
public class ButlerService extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4511359281305063089L;
	/** 呼叫用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 弹窗告示(0:未告示,1:已告示) */
	private Integer alerts;
	/** 服务状态(0:未服务,1:已服务) */
	private Integer service;
	/** 服务工作人员 */
	private String servicePersonal;
	/** 服务时间 */
	private Date servDT;
	
	@Transient
	private String chamber;
	

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public Integer getAlerts() {
		return alerts;
	}

	public void setAlerts(Integer alerts) {
		this.alerts = alerts;
	}

	public Integer getService() {
		return service;
	}

	public void setService(Integer service) {
		this.service = service;
	}

	public String getServicePersonal() {
		return servicePersonal;
	}

	public void setServicePersonal(String servicePersonal) {
		this.servicePersonal = servicePersonal;
	}

	public Date getServDT() {
		return servDT;
	}

	public void setServDT(Date servDT) {
		this.servDT = servDT;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}
}
