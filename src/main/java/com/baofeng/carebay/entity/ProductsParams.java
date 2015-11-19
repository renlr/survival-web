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
 * 描述：商品销售属性
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "productsParams")
public class ProductsParams extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4859505643086233489L;
	/** 规格1 */
	private String name1;
	/** 规格2 */
	private String name2;
	/** 规格1值 */
	private String auts1;
	/** 规格2值 */
	private String auts2;
	/** 成本价格 */
	private Float costPrice;
	/** 市场价格 */
	private Float bazaarPrice;
	/** 销售价格 */
	private Float sellPrice;
	/** 库存数量 */
	private Long inventory;
	/** 商品 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "products_id")
	private Products products;

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getAuts1() {
		return auts1;
	}

	public void setAuts1(String auts1) {
		this.auts1 = auts1;
	}

	public String getAuts2() {
		return auts2;
	}

	public void setAuts2(String auts2) {
		this.auts2 = auts2;
	}

	public Float getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Float costPrice) {
		this.costPrice = costPrice;
	}

	public Float getBazaarPrice() {
		return bazaarPrice;
	}

	public void setBazaarPrice(Float bazaarPrice) {
		this.bazaarPrice = bazaarPrice;
	}

	public Float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}
}
