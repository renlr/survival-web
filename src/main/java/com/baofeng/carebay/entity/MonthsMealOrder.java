package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：套餐点单
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "monthsMealOrder")
public class MonthsMealOrder extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5494821175874988237L;

	/** 用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 点餐单号 */
	private String orderNo;
	/** 总金额 */
	private Float totalPrice;
	/** 点餐状态(0：等待处理，1：点餐完成，2：点餐失败) */
	private Integer tranStatus;
	/** 服务状态(0:未服务,1:已服务) */
	private Integer service;
	/** 送餐人员 */
	private String deliverMeals;
	@Transient
	private String chamber;
	@Transient
	private List<MonthsMealOrderDetails> detailsList;

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(Integer tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String getDeliverMeals() {
		return deliverMeals;
	}

	public void setDeliverMeals(String deliverMeals) {
		this.deliverMeals = deliverMeals;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

	public Integer getService() {
		return service;
	}

	public void setService(Integer service) {
		this.service = service;
	}

	public List<MonthsMealOrderDetails> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(List<MonthsMealOrderDetails> detailsList) {
		this.detailsList = detailsList;
	}
}
