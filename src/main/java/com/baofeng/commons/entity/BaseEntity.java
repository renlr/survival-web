package com.baofeng.commons.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：实体基类
 * 
 * @author RENLIANGRONG
 */
@MappedSuperclass
@SuppressWarnings("serial")
public class BaseEntity implements java.io.Serializable {

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	// 实体创建者
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operator_id")
	private Operator operator;

	// 实体状态（业务无关）
	private EntityStatus status = EntityStatus.NORMAL;

	// 创建时间（业务无关）
	private Date createDT = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public Date getCreateDT() {
		return createDT;
	}

	public void setCreateDT(Date createDT) {
		this.createDT = createDT;
	}
}
