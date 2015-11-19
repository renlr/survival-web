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
 * 描述：上传文件详细记录
 * 
 * @author RENLIANGRONG
 */
@Entity
@Table(name = "photosDetails")
public class PhotosDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9183285008695114659L;

	/** 文件名称 */
	private String file;
	/** 文件唯一值 */
	private String fileSha1;
	/** 后缀名 */
	private String fileExt;
	/** 文件大小 */
	private Long fileSize;
	/** 媒体 */
	private String media;
	/** 媒体sha1 */
	private String mediaSha1;
	/** 媒体后缀名 */
	private String mediaExt;
	/** 媒体大小 */
	private Long mediaSize;
	/** 文件类型 (0:图片,2:视频) */
	private Integer type;
	/** 文件上传批次 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "photos_id")
	private Photos photos;
	/** 切图状态(0:未处理,1:未下载，2：处理完成,3:处理失败) */
	private Integer cutter;
	/** 转码状态(0:未处理,1:未下载，2：处理完成,3:处理失败) */
	private Integer isConvert;
	/** 上传Id */
	private String uploadId;

	public String getFileSha1() {
		return fileSha1;
	}

	public void setFileSha1(String fileSha1) {
		this.fileSha1 = fileSha1;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Photos getPhotos() {
		return photos;
	}

	public void setPhotos(Photos photos) {
		this.photos = photos;
	}

	public Integer getCutter() {
		return cutter;
	}

	public void setCutter(Integer cutter) {
		this.cutter = cutter;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getMediaSha1() {
		return mediaSha1;
	}

	public void setMediaSha1(String mediaSha1) {
		this.mediaSha1 = mediaSha1;
	}

	public String getMediaExt() {
		return mediaExt;
	}

	public void setMediaExt(String mediaExt) {
		this.mediaExt = mediaExt;
	}

	public Long getMediaSize() {
		return mediaSize;
	}

	public void setMediaSize(Long mediaSize) {
		this.mediaSize = mediaSize;
	}

	public Integer getIsConvert() {
		return isConvert;
	}

	public void setIsConvert(Integer isConvert) {
		this.isConvert = isConvert;
	}
}
