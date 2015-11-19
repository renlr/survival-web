package com.baofeng.carebay.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：馨月宝宝表
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "carebay")
@SuppressWarnings("serial")
public class CareBay extends BaseEntity implements Serializable {

	/** 宝宝昵称 */
	private String nickname;
	/** 宝宝生日 */
	private Date birthday;
	/** 宝宝性别 （0：女孩，1：男孩） */
	private Integer sex;
	/** 宝宝头像 */
	private String image;
	/** 头像sha1 */
	private String imageSha1;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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
