package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：高级定制
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "customMade")
public class CustomMade extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1319778837261054114L;
	/** 名称 */
	private String name;
	/** 图片名称 */
	private String image;
	/** 图片sha1 */
	private String imageSha1;
	/** 裁剪(0:未裁剪,1:已裁剪) */
	private Integer tailor;
	/** 上线（0:下线，1：上线） */
	private Integer online;
	/** 显示顺序 */
	private Integer indexs;

	@Transient
	private String chamber;

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

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public Integer getTailor() {
		return tailor;
	}

	public void setTailor(Integer tailor) {
		this.tailor = tailor;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}
}
