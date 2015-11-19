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
 * 描述：月子套餐详细内容
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "monthsMealDetails")
public class MonthsMealDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9207745298261442362L;

	/** 图片文件 */
	private String image;
	/** 图片sha1 */
	private String imageSha1;
	/** 月子套餐 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "meal_id")
	private MonthsMeal meal;
	/** 显示顺序 */
	private Integer indexs;
	/** 裁剪(0:未裁剪,1:已裁剪) */
	private Integer tailor;

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

	public MonthsMeal getMeal() {
		return meal;
	}

	public void setMeal(MonthsMeal meal) {
		this.meal = meal;
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
}
