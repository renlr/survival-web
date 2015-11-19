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
 * 描述：导航管理
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "navigation")
public class Navigation extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7106515925341239097L;
	/** 菜单名称 */
	private String name;
	/** 菜单Id */
	private String navId;
	/** key */
	private String nkey;
	/** 显示顺序 */
	private Integer indexs;
	/** 图标 */
	private String image;
	/** 图标sha1 */
	private String imageSha1;
	/** 父类 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "nav_id")
	private Navigation nav;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNavId() {
		return navId;
	}

	public void setNavId(String navId) {
		this.navId = navId;
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

	public Navigation getNav() {
		return nav;
	}

	public void setNav(Navigation nav) {
		this.nav = nav;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public String getNkey() {
		return nkey;
	}

	public void setNkey(String nkey) {
		this.nkey = nkey;
	}
}
