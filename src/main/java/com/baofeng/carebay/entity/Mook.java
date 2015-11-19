package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：母婴杂志
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "mook")
public class Mook extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -207651201775463390L;
	/** 第几期 */
	@Column(length = 1500)
	private String name;
	/** 文件名称 */
	private String image;
	/** 文件唯一 */
	private String imageSha1;
	/** 是否上线 */
	private Integer online;
	/** 是否会员（0：普通，1：会员） */
	private Integer vip;
	/** 上线时间 */
	private Date onlineDt;
	/** 显示顺序 */
	private Integer indexs;
	/** 跳转类型(1:母婴乐购,2:高级定制,3:服务精选,4:月子套餐,5:最新活动) */
	private String type;
	/** 跳转参数 */
	private String params;
	/** 跳转名称 */
	private String paramsName;

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

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public Date getOnlineDt() {
		return onlineDt;
	}

	public void setOnlineDt(Date onlineDt) {
		this.onlineDt = onlineDt;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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
