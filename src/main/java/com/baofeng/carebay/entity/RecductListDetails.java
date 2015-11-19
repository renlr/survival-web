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
 * 描述：推荐产品列表
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "recductListDetails")
public class RecductListDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4618555361543833866L;

	/** 产品分类 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ductCat_id")
	private RecductList ductCat;
	/** 商品 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "products_id")
	private Products products;

	public RecductList getDuctCat() {
		return ductCat;
	}

	public void setDuctCat(RecductList ductCat) {
		this.ductCat = ductCat;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}
}
