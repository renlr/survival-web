package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：轮播按周期推荐
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "posterCarousel")
public class PosterCarousel extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5899610887663504185L;

	/** 周期名称 */
	private Integer name;
	/** 默认（其他周期没有添加时取此周期图片） */
	private Integer defaults;

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Integer getDefaults() {
		return defaults;
	}

	public void setDefaults(Integer defaults) {
		this.defaults = defaults;
	}
}
