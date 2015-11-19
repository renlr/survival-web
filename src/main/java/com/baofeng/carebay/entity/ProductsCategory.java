package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：商品分类
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "productsCategory")
public class ProductsCategory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5033244594693457376L;
	/** 分类名称 */
	private String name;
	/** 父分类 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private ProductsCategory category;
	/** 绑定导航菜单 */
	private String navigtor;
	/**会所*/
	@Transient
	private String chamber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductsCategory getCategory() {
		return category;
	}

	public void setCategory(ProductsCategory category) {
		this.category = category;
	}

	public String getNavigtor() {
		return navigtor;
	}

	public void setNavigtor(String navigtor) {
		this.navigtor = navigtor;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}
}
