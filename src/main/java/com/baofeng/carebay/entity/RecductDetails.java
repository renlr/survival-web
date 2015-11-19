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
 * 描述：首页推荐商品多图版面
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "recductDetails")
public class RecductDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4271136560013551321L;
	/** 文件名称 */
	private String image;
	/** 文件sha1 */
	private String imageSha1;
	/** 跳转类型 */
	private Integer type;
	/** 跳转参数 */
	private String params;
	/** 跳转名称 */
	private String paramsName;
	/** 显示顺序 */
	private Integer indexs;
	/** 推荐商品 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "recduct_id")
	private Recduct recduct;

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

	public Recduct getRecduct() {
		return recduct;
	}

	public void setRecduct(Recduct recduct) {
		this.recduct = recduct;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getParamsName() {
		return paramsName;
	}

	public void setParamsName(String paramsName) {
		this.paramsName = paramsName;
	}
}
