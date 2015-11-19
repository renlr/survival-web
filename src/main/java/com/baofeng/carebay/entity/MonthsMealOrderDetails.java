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
 * 描述：月子餐点单详细
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "monthsMealOrderDetails")
public class MonthsMealOrderDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4244178200724513184L;

	/** 数量 */
	private Integer number;

	/** 菜单Id */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "orders_id")
	private MonthsMealOrder orders;

	/** 用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "meal_id")
	private MonthsMeal meal;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public MonthsMealOrder getOrders() {
		return orders;
	}

	public void setOrders(MonthsMealOrder orders) {
		this.orders = orders;
	}

	public MonthsMeal getMeal() {
		return meal;
	}

	public void setMeal(MonthsMeal meal) {
		this.meal = meal;
	}
}
