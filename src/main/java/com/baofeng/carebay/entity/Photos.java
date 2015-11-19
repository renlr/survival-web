package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：宝宝相册
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "photos")
public class Photos extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9183285008695114659L;

	/** 所属宝宝 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "baby_id")
	private CareBay carebay;
	/** 上传会员 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	/** 描述内容 */
	private String content;
	/** 上传位置 */
	private String location;
	/** 上传时间 */
	private Date uploadDT;
	/** 是否裁剪(0:未处理,2：处理完成) */
	private Integer cutter;

	public CareBay getCarebay() {
		return carebay;
	}

	public void setCarebay(CareBay carebay) {
		this.carebay = carebay;
	}

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}

	public Date getUploadDT() {
		return uploadDT;
	}

	public void setUploadDT(Date uploadDT) {
		this.uploadDT = uploadDT;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getCutter() {
		return cutter;
	}

	public void setCutter(Integer cutter) {
		this.cutter = cutter;
	}
}
