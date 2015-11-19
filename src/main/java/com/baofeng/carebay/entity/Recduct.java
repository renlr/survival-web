package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：首页推荐商品
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "recduct")
public class Recduct extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1573444679336635758L;

	/** 展示版式（0：单张,1:多张） */
	private Integer model;
	/** 上线（0:下线，1：上线） */
	private Integer online;
	/** 显示顺序 */
	private Integer indexs;

	@Transient
	private String image;

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
