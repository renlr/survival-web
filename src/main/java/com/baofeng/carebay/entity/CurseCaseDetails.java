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
 * 描述：护理套餐详细内容
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "curseCaseDetails")
public class CurseCaseDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3752259625163101902L;
	/** 服务标题 */
	private String name;
	/** 图片文件 */
	private String image;
	/** 图片sha1 */
	private String imageSha1;
	/** 护理套餐 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "curse_id")
	private CurseCase curse;
	/** 显示顺序 */
	private Integer indexs;
	/** 上线（0:未上线，1：上线中,2:已下线） */
	private Integer online;
	/** 内容html地址 */
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CurseCase getCurse() {
		return curse;
	}

	public void setCurse(CurseCase curse) {
		this.curse = curse;
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
