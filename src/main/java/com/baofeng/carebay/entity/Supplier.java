package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：商品供应商
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "supplier")
public class Supplier extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -2918881953025835327L;

}
