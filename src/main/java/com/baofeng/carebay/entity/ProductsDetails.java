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
 * 描述：母婴产品图片文件表
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "productsDetails")
public class ProductsDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6530220641717300164L;
	/** 文件名称 */
	private String image;
	/** 文件sha1 */
	private String imageSha1;
	/** 显示顺序 */
	private Integer indexs;
	/** 母婴产品 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "products_id")
	private Products products;
	/** 裁剪(0:未裁剪,1:已裁剪) */
	private Integer tailor;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageSha1() {
		return imageSha1;
	}

	public void setImageSha1(String imageSha1) {
		this.imageSha1 = imageSha1;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Integer getTailor() {
		return tailor;
	}

	public void setTailor(Integer tailor) {
		this.tailor = tailor;
	}
}
