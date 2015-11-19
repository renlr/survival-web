package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：我要瘦身
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "slimming")
public class Slimming extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3952403724401706869L;
	/** 方案名称 */
	private String name;
	/** 导航图片 */
	private String image;
	/** 图片唯一值 */
	private String imageSha1;
	/** 是否上线 */
	private Integer online;
	/** 显示顺序 */
	private Integer indexs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
