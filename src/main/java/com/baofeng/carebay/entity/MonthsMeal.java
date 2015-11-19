package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：月子套餐
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "monthsMeal")
public class MonthsMeal extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5494821175874988237L;

	/** 套餐图片 */
	private String image;
	/** 菜品名称 */
	private String name;
	/** 制作者 */
	private String author;
	/** 制作方法 */
	private String productionMethods;
	/** 菜品品味 */
	private String flavor;
	/** 菜品热量 */
	private String heat;
	/** 菜品介绍 */
	@Column(length = 2000)
	private String describes;
	/** 制作介绍 */
	@Column(length = 2000)
	private String methodDescribes;
	/** 售价 */
	private Float price;
	/** 上下线(0：下线，1：上线) */
	private Integer online;
	/** 显示顺序 */
	private Integer indexs;
	/** 定时时间 */
	@Column(length = 200)
	private String taskDT;
	
	@Transient
	private String chamber;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getProductionMethods() {
		return productionMethods;
	}

	public void setProductionMethods(String productionMethods) {
		this.productionMethods = productionMethods;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public String getHeat() {
		return heat;
	}

	public void setHeat(String heat) {
		this.heat = heat;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getMethodDescribes() {
		return methodDescribes;
	}

	public void setMethodDescribes(String methodDescribes) {
		this.methodDescribes = methodDescribes;
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

	public String getTaskDT() {
		return taskDT;
	}

	public void setTaskDT(String taskDT) {
		this.taskDT = taskDT;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

}
