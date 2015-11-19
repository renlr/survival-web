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
 * 描述：会所管理详细
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "chamberDetails")
public class ChamberDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6991056989827037332L;

	/** 图片名称 */
	private String image;
	/** 图片sha1 */
	private String imageSha1;
	/** 类型(1:会所介绍,2:会员手册) */
	private Integer type;
	/** 上线（0:下线，1：上线） */
	private Integer online;
	/** 显示顺序 */
	private Integer indexs;
	/** 索引词汇 */
	private String indexTerms;
	/** 所属会所 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "chamber_id")
	private Chamber chamber;

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

	public Chamber getChamber() {
		return chamber;
	}

	public void setChamber(Chamber chamber) {
		this.chamber = chamber;
	}

	public String getIndexTerms() {
		return indexTerms;
	}

	public void setIndexTerms(String indexTerms) {
		this.indexTerms = indexTerms;
	}
}
