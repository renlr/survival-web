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
 * 描述：胎教音乐
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "music")
public class Music extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1335796860732178843L;
	/** 音乐名称 */
	private String name;
	/** 音乐图片 */
	private String image;
	/** 播放路径 */
	private String file;
	/** 简单描述 */
	private String describes;
	/** 图片唯一值 */
	private String imageSha1;
	/** 文件唯一值 */
	private String fileSha1;
	/** 周期 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "week_id")
	private WeekService week;

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getImageSha1() {
		return imageSha1;
	}

	public void setImageSha1(String imageSha1) {
		this.imageSha1 = imageSha1;
	}

	public String getFileSha1() {
		return fileSha1;
	}

	public void setFileSha1(String fileSha1) {
		this.fileSha1 = fileSha1;
	}

	public WeekService getWeek() {
		return week;
	}

	public void setWeek(WeekService week) {
		this.week = week;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}
}
