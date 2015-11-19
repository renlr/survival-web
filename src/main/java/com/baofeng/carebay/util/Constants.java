package com.baofeng.carebay.util;

import java.awt.Dimension;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;

public class Constants {

	/** 当前session用户KEY */
	public static final String CURRENT_USER = "session_user";

	/** 音乐文件 */
	public static String DEFAULT_CAREBAYMUSIC;
	/** html文件 */
	public static String DEFAULT_BUILDHTMLPATH;
	/** 图片文件 */
	public static String DEFAULT_UPLOADIMAGEPATH;
	/** 视频文件 */
	public static String DEFAULT_UPLOADVIDEOPATH;

	/** html访问路径 */
	public static String DEFAULT_HTTPHTML;
	/** 图片访问路径 */
	public static String DEFAULT_HTTPIMAGES;
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
	/** 视频转码后缀名称 */
	public static String DEFAULT_VIDEOCONVERTNAME;
	/** 视频转码日志 */
	public static String DEFAULT_VIDEOCONVERTLOG;

	/** 聊天主机 */
	public static String DEFAULT_OPENFIREHOST;
	/** 聊天端口 */
	public static String DEFAULT_OPENFIREPORT;
	/** 管理用户 */
	public static String DEFAULT_OPENFIREUSERNAME;
	/** 管理密码 */
	public static String DEFAULT_OPENFIREPASSWORD;
	/** 主机域名 */
	public static String DEFAULT_OPENFIRELOCALDOMAIN;

	/** 系统配置 */
	public static final String CONF_FILE_NAME = com.baofeng.utils.Constants.COREWEB_BUILDITEMS + ".properties";
	/** 原文件下载指令 */
	public static final String DOWNLOADCOMMAND = "/usr/local/axel/axel ${1} -o ${2}";
	/** html文件自动适配 */
	public static final String HTMLCENTERCODE = "<meta name=\"viewport\" content=\"width=decice-width,uer-scalable=no\">";

	/** 正在转码中的视频 */
	public static final ConcurrentHashMap<String, Date> tasks = new ConcurrentHashMap<String, Date>();

	static {
		try {
			InputStream fis = Constants.class.getClassLoader().getResourceAsStream(CONF_FILE_NAME);
			PropertyResourceBundle props = new PropertyResourceBundle(fis);
			try {
				DEFAULT_CAREBAYMUSIC = props.getString("DEFAULT_CAREBAYMUSIC");
			} catch (Exception e) {
			}
			try {
				DEFAULT_BUILDHTMLPATH = props.getString("DEFAULT_BUILDHTMLPATH");
			} catch (Exception e) {
			}
			try {
				DEFAULT_UPLOADIMAGEPATH = props.getString("DEFAULT_UPLOADIMAGEPATH");
			} catch (Exception e) {
			}
			try {
				DEFAULT_UPLOADVIDEOPATH = props.getString("DEFAULT_UPLOADVIDEOPATH");
			} catch (Exception e) {
			}
			try {
				DEFAULT_HTTPHTML = props.getString("DEFAULT_HTTPHTML");
			} catch (Exception e) {
			}
			try {
				DEFAULT_HTTPIMAGES = props.getString("DEFAULT_HTTPIMAGES");
			} catch (Exception e) {
			}
			try {
				DEFAULT_HTTPMUSIC = props.getString("DEFAULT_HTTPMUSIC");
			} catch (Exception e) {
			}
			try {
				DEFAULT_HTTPVIDEO = props.getString("DEFAULT_HTTPVIDEO");
			} catch (Exception e) {
			}
			try {
				DEFAULT_IMAGESIZE = props.getString("DEFAULT_IMAGESIZE");
			} catch (Exception e) {
			}
			try {
				DEFAULT_IMAGECURTAIL = props.getString("DEFAULT_IMAGECURTAIL");
			} catch (Exception e) {
			}
			try {
				DEFAULT_IMAGECURTAILNAME = props.getString("DEFAULT_IMAGECURTAILNAME");
			} catch (Exception e) {
			}
			try {
				DEFAULT_VIDEOCONVERTNAME = props.getString("DEFAULT_VIDEOCONVERTNAME");
			} catch (Exception e) {
			}
			try {
				DEFAULT_VIDEOCONVERTLOG = props.getString("DEFAULT_VIDEOCONVERTLOG");
			} catch (Exception e) {
			}
			try {
				DEFAULT_OPENFIREHOST = props.getString("DEFAULT_OPENFIREHOST");
			} catch (Exception e) {
			}
			try {
				DEFAULT_OPENFIREPORT = props.getString("DEFAULT_OPENFIREPORT");
			} catch (Exception e) {
			}
			try {
				DEFAULT_OPENFIREUSERNAME = props.getString("DEFAULT_OPENFIREUSERNAME");
			} catch (Exception e) {
			}
			try {
				DEFAULT_OPENFIREPASSWORD = props.getString("DEFAULT_OPENFIREPASSWORD");
			} catch (Exception e) {
			}
			try {
				DEFAULT_OPENFIRELOCALDOMAIN = props.getString("DEFAULT_OPENFIRELOCALDOMAIN");
			} catch (Exception e) {
			}
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

	/**
	 * 功能：获取UUID
	 * */
	public static String UUIDToString() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
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

	public static String sha1ToPath(String sha1, String ext) {
		String result = String.valueOf("");
		if (sha1 != null && sha1.trim().length() > 0) {
			String s1 = sha1.substring(0, 1);
			String s2 = DigestUtils.shaHex(sha1.getBytes()).substring(0, 2);
			String s3 = sha1;
			result = s1 + File.separator + s2 + File.separator + s3 + ext;
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

	public static void main(String[] args) {
		System.out.println(Constants.sha1ToPath(""));
	}
}
