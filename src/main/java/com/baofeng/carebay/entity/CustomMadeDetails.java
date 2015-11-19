package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：高级定制内容
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "customMadeDetails")
public class CustomMadeDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1319778837261054114L;
	/** 定制名称 */
	private String name;
	/** 图片名称 */
	private String image;
	/** 图片sha1 */
	private String imageSha1;
	/** 上线（0:下线，1：上线） */
	private Integer online;
	/** 显示顺序 */
	private Integer indexs;
	/** 内容描述 */
	@Column(length = 1500)
	private String content;
	/** 定制类型 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "custom_id")
	private CustomMade custom;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CustomMade getCustom() {
		return custom;
	}

	public void setCustom(CustomMade custom) {
		this.custom = custom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
