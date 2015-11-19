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
 * 功能：用户菜单页面
 * */
@Entity
@Table(name = "userPages")
public class UserPages implements Serializable {

	private static final long serialVersionUID = 3463137876045409812L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/** 菜单ID */
	private String itemId;

	/** 用户帐号 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Operator user;

	/** 创建时间 */
	private Date createDT;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Operator getUser() {
		return user;
	}

	public void setUser(Operator user) {
		this.user = user;
	}

	public Date getCreateDT() {
		return createDT;
	}

	public void setCreateDT(Date createDT) {
		this.createDT = createDT;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
