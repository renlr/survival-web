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
 * 描述：用户图片数据
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "photosCover")
public class PhotosCover extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9183285008695114659L;
	/** 相册背景图片 */
	private String photosImage;
	/** 相册背景图片sha1 */
	private String photosImageSha1;
	/** 宝宝头像图片 */
	private String babyImage;
	/** 宝宝头像图片sha1 */
	private String babyImageSha1;
	/** 个人中心背景图片 */
	private String personalImage;
	/** 个人中心背景图片sha1 */
	private String personalImageSha1;
	/** 用户头像图片 */
	private String userImage;
	/** 用户头像图片sha1 */
	private String userImageSha1;
	/** 是否默认 (0:否，1：是) */
	private Integer defaults;
	/** 上传用户 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private CareBayUser user;
	
	public String getPhotosImage() {
		return photosImage;
	}

	public void setPhotosImage(String photosImage) {
		this.photosImage = photosImage;
	}

	public String getPhotosImageSha1() {
		return photosImageSha1;
	}

	public void setPhotosImageSha1(String photosImageSha1) {
		this.photosImageSha1 = photosImageSha1;
	}

	public String getBabyImage() {
		return babyImage;
	}

	public void setBabyImage(String babyImage) {
		this.babyImage = babyImage;
	}

	public String getBabyImageSha1() {
		return babyImageSha1;
	}

	public void setBabyImageSha1(String babyImageSha1) {
		this.babyImageSha1 = babyImageSha1;
	}

	public String getPersonalImage() {
		return personalImage;
	}

	public void setPersonalImage(String personalImage) {
		this.personalImage = personalImage;
	}

	public String getPersonalImageSha1() {
		return personalImageSha1;
	}

	public void setPersonalImageSha1(String personalImageSha1) {
		this.personalImageSha1 = personalImageSha1;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserImageSha1() {
		return userImageSha1;
	}

	public void setUserImageSha1(String userImageSha1) {
		this.userImageSha1 = userImageSha1;
	}

	public Integer getDefaults() {
		return defaults;
	}

	public void setDefaults(Integer defaults) {
		this.defaults = defaults;
	}

	public CareBayUser getUser() {
		return user;
	}

	public void setUser(CareBayUser user) {
		this.user = user;
	}
}
