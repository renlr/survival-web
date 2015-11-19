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
 * 描述：服务精选择
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "customService")
public class CustomService extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3134812939861219516L;
	/** 服务名称 */
	private String name;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "tos_id")
	private CurseToS tos;
	/** 图片名称 */
	private String image;
	/** 图片sha1 */
	private String imageSha1;
	/** 裁剪(0:未裁剪,1:已裁剪) */
	private Integer tailor;
	/** 显示顺序 */
	private Integer indexs;
	/** 上下线 */
	private Integer online;

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

	public CurseToS getTos() {
		return tos;
	}

	public void setTos(CurseToS tos) {
		this.tos = tos;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getTailor() {
		return tailor;
	}

	public void setTailor(Integer tailor) {
		this.tailor = tailor;
	}
}
