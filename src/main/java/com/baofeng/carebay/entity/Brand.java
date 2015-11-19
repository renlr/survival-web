package com.baofeng.carebay.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.baofeng.commons.entity.BaseEntity;

/**
 * 描述：商品品牌
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "brand")
public class Brand extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5763021724774552825L;

}
