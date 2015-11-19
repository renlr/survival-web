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
 * 描述：首页海报图片
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "poster")
public class Poster extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7536627186331585074L;

	/** 图片名称 */
	private String name;
	/** 文件名称 */
	private String image;
	/** 图片唯一值 */
	private String imageSha1;
	/** 跳转类型(1:产品分类，2：产品详情,3：功能模块,4：官方活动) */
	private Integer type;
	/** 跳转参数 */
	private String prams;
	/** 跳转名称 */
	private String pramsName;
	/** 上线（0:下线，1：上线） */
	private Integer online;
	/** 显示顺序 */
	private Integer indexs;
	/** 周期 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "week_id")
	private WeekService week;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getPrams() {
		return prams;
	}

	public void setPrams(String prams) {
		this.prams = prams;
	}

	public String getPramsName() {
		return pramsName;
	}

	public void setPramsName(String pramsName) {
		this.pramsName = pramsName;
	}

	public WeekService getWeek() {
		return week;
	}

	public void setWeek(WeekService week) {
		this.week = week;
	}

}
