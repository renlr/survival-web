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
 * 描述：月子套餐轮播
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "monthsMealCarousel")
public class MonthsMealCarousel extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5494821175874988237L;

	/** 图片文件 */
	private String image;
	/** 图片sha1 */
	private String imageSha1;
	/** 月子套餐 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "meal_id")
	private MonthsMeal meal;
	/** 显示顺序 */
	private Integer indexs;
	/** 上下线(0：下线，1：上线) */
	private Integer online;

	@Transient
	private String chamber;

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

	public MonthsMeal getMeal() {
		return meal;
	}

	public void setMeal(MonthsMeal meal) {
		this.meal = meal;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}
}
