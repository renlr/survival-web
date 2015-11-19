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
 * 描述：服务精选
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "curseCase")
public class CurseCase extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2875133762987206822L;
	/** 服务名称 */
	private String name;
	/** 图片 */
	private String image;
	/** 图片唯一值 */
	private String imageSha1;
	/** 显示顺序 */
	private Integer indexs;
	/** 上线（0:下线，1：上线） */
	private Integer online;
	/** 内容介绍 */
	@Column(length = 1500)
	private String content;
	/** 服务类型 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "custom_id")
	private CustomService custom;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CustomService getCustom() {
		return custom;
	}

	public void setCustom(CustomService custom) {
		this.custom = custom;
	}

}
