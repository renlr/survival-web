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
 * 描述：我要瘦身方案详细内容
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "slimmingDetails")
public class SlimmingDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2803460680468489981L;
	/** 内容标题 */
	private String name;
	/** 图片文件 */
	private String image;
	/** 图片sha1 */
	private String imageSha1;
	/** 瘦身方案 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "slim_id")
	private Slimming slim;
	/** 显示顺序 */
	private Integer indexs;
	/** 上线（0:未上线，1：上线中,2:已下线） */
	private Integer online;
	/** 瘦身内容 */
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Slimming getSlim() {
		return slim;
	}

	public void setSlim(Slimming slim) {
		this.slim = slim;
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
}
