package com.baofeng.utils;

import java.awt.Dimension;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PropertyResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

public class Constants {

	public static final String format1 = "yyyy-MM-dd";
	public static final String format2 = "yyyy-MM-dd HH:mm:ss";
	public static final String format3 = "M月dd";
	public static final String format4 = "yyyy.MM.dd";
	public static final String format5 = "yyyyMMdd";
	public static final String format6 = "M月dd日 HH:mm";
	public static final String format7 = "yyyy-MM";
	public static final String format8 = "yyyy-MM-dd HH:mm";

	/** 当前session用户KEY */
	public static final String CURRENT_USER = "session_user";

	/** 音乐文件 */
	public static String DEFAULT_CAREBAYMUSIC;
	/** html文件 */
	public static String DEFAULT_BUILDHTMLPATH;
	/** 图片文件 */
	public static String DEFAULT_UPLOADIMAGEPATH;
	/**文件 */
	public static String DEFAULT_UPLOADFILEPATH;
	/** 视频文件 */
	public static String DEFAULT_UPLOADVIDEOPATH;

	/** html访问路径 */
	public static String DEFAULT_HTTPHTML;
	/** 图片访问路径 */
	public static String DEFAULT_HTTPIMAGES;
	/** 文件访问路径 */
	public static String DEFAULT_HTTPFILE;
	/** 音乐访问路径 */
	public static String DEFAULT_HTTPMUSIC;
	/** 视频访问路径 */
	public static String DEFAULT_HTTPVIDEO;

	/** 切图尺寸 */
	public static String DEFAULT_IMAGESIZE;
	/** 切图按比例缩放 */
	public static String DEFAULT_IMAGECURTAIL;
	/** 图片缩放后缀名称 */
	public static String DEFAULT_IMAGECURTAILNAME;

	/** 生成系统 */
	public static String COREWEB_BUILDITEMS;

	/** 原文件下载指令 */
	public static final String DOWNLOADCOMMAND = "/usr/local/axel/axel ${1} -o ${2}";

	/** 系统配置 */
	public static final String CONF_FILE_NAME = "variables.properties";

	static {
		try {
			InputStream fis = Constants.class.getClassLoader().getResourceAsStream(CONF_FILE_NAME);
			PropertyResourceBundle props = new PropertyResourceBundle(fis);
			DEFAULT_CAREBAYMUSIC = props.getString("DEFAULT_CAREBAYMUSIC");
			DEFAULT_BUILDHTMLPATH = props.getString("DEFAULT_BUILDHTMLPATH");
			DEFAULT_UPLOADIMAGEPATH = props.getString("DEFAULT_UPLOADIMAGEPATH");
			DEFAULT_UPLOADVIDEOPATH = props.getString("DEFAULT_UPLOADVIDEOPATH");
			DEFAULT_UPLOADFILEPATH = props.getString("DEFAULT_UPLOADFILEPATH");

			DEFAULT_HTTPHTML = props.getString("DEFAULT_HTTPHTML");
			DEFAULT_HTTPIMAGES = props.getString("DEFAULT_HTTPIMAGES");
			DEFAULT_HTTPFILE = props.getString("DEFAULT_HTTPFILE");
			DEFAULT_HTTPMUSIC = props.getString("DEFAULT_HTTPMUSIC");
			DEFAULT_HTTPVIDEO = props.getString("DEFAULT_HTTPVIDEO");
			DEFAULT_IMAGESIZE = props.getString("DEFAULT_IMAGESIZE");
			DEFAULT_IMAGECURTAIL = props.getString("DEFAULT_IMAGECURTAIL");
			DEFAULT_IMAGECURTAILNAME = props.getString("DEFAULT_IMAGECURTAILNAME");

			COREWEB_BUILDITEMS = props.getString("COREWEB_BUILDITEMS");

			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 图片尺寸 */
	public static List<Dimension> getImageSizes() {
		List<Dimension> imageSizes = new ArrayList<Dimension>();
		if (Constants.DEFAULT_IMAGESIZE != null && Constants.DEFAULT_IMAGESIZE.trim().length() > 0) {
			String[] sizes = Constants.DEFAULT_IMAGESIZE.split(",");
			for (String imageSize : sizes) {
				String[] widthHeight = imageSize.split("x");
				Dimension d = new Dimension(Integer.parseInt(widthHeight[0]), Integer.parseInt(widthHeight[1]));
				imageSizes.add(d);
			}
		}
		return imageSizes;
	}

	public static String sha1ToPath(String sha1) {
		String result = String.valueOf("");
		if (sha1 != null && sha1.trim().length() > 0) {
			String s1 = sha1.substring(0, 1);
			String s2 = DigestUtils.shaHex(sha1.getBytes()).substring(0, 2);
			result = s1 + File.separator + s2 + File.separator + sha1;
		}
		return result;
	}

	public static void mkdirs(String path) {
		if (path != null) {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
	}

	/**
	 * 功能：日期转字符
	 * */
	public static String convert(Date date, String format) {
		String retValue = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		retValue = sdf.format(date);
		return retValue;
	}

	/**
	 * 功能：字符转日期
	 * */
	public static Date convert(String date, String format) {
		SimpleDateFormat fmat = new SimpleDateFormat(format);
		Date value = null;
		try {
			value = fmat.parse(date);
		} catch (Exception e) {
		}
		return value;
	}

}
