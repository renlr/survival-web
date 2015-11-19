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
 * 描述：订单商品
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "productsOrderDetails")
public class ProductsOrderDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1435785772116189896L;

	/** 数量 */
	private Integer number;

	/** 订单号 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private ProductsOrder orders;
	/** 商品 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "products_id")
	private Products products;
	/** 销售属性 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "params_id")
	private ProductsParams params;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public ProductsOrder getOrders() {
		return orders;
	}

	public void setOrders(ProductsOrder orders) {
		this.orders = orders;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public ProductsParams getParams() {
		return params;
	}

	public void setParams(ProductsParams params) {
		this.params = params;
	}
}
