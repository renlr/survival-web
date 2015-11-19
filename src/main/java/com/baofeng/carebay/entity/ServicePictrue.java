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
 * 描述：服务图片
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "servicePictrue")
public class ServicePictrue extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2245358014574845478L;

	/** 文件名称 */
	private String file;
	/** 文件唯一值 */
	private String fileSha1;
	/** 后缀名 */
	private String fileExt;
	/** 文件大小 */
	private Long fileSize;
	/** 图片文本 */
	private String text;
	/** 显示顺序 */
	private Integer indexs;
	/** 父类图片 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "sp_id")
	private ServicePictrue sp;
	/** 图片类型 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "st_id")
	private ServiceType st;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileSha1() {
		return fileSha1;
	}

	public void setFileSha1(String fileSha1) {
		this.fileSha1 = fileSha1;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public ServicePictrue getSp() {
		return sp;
	}

	public void setSp(ServicePictrue sp) {
		this.sp = sp;
	}

	public ServiceType getSt() {
		return st;
	}

	public void setSt(ServiceType st) {
		this.st = st;
	}
}
