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
 * 描述：胎教音乐背景图片
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "musicbg")
public class MusicBg extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1335796860732178843L;
	/** 音乐图片 */
	private String image;
	/** 图片唯一值 */
	private String imageSha1;
	/** 周期 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "week_id")
	private WeekService week;
	/** 上线（0:下线，1：上线） */
	private Integer online;

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

	public WeekService getWeek() {
		return week;
	}

	public void setWeek(WeekService week) {
		this.week = week;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}
}
