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
 * 描述：期刊内容
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "mookDetails")
public class MookDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8877765631924284663L;
	/** 第几页 */
	private Integer name;
	/** 期刊 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "mook_id")
	private Mook mook;
	/** 期刊内容 */
	private String content;

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Mook getMook() {
		return mook;
	}

	public void setMook(Mook mook) {
		this.mook = mook;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
