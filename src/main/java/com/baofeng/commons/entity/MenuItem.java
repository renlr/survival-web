package com.baofeng.commons.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 功能：系统菜单
 * */
@Entity
@Table(name = "menuitem")
public class MenuItem implements Serializable {

	private static final long serialVersionUID = -5046721141875217907L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/** 菜单名称 */
	private String name;
	/** 访问地址 */
	private String url;
	/** 父类菜单 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	private MenuItem item;
	/** 创建时间 */
	private Date createDT;
	/** 页面divId */
	private String divid;
	/** 排序显示 */
	private Integer indexs;

	public MenuItem() {
	}

	public MenuItem(String name, String url, MenuItem item, Date createDT, String divid, Integer indexs) {
		super();
		this.name = name;
		this.url = url;
		this.item = item;
		this.createDT = createDT;
		this.divid = divid;
		this.indexs = indexs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public Date getCreateDT() {
		return createDT;
	}

	public void setCreateDT(Date createDT) {
		this.createDT = createDT;
	}

	public String getDivid() {
		return divid;
	}

	public void setDivid(String divid) {
		this.divid = divid;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}
}
