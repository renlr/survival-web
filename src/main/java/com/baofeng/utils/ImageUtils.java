package com.baofeng.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtils {

	private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

	public static String convertThenSave(String imageLocation, BufferedImage bufferedImage, double newWidth, double newHeight, String rname, String small) throws Exception {
		// 这样就不用每次转换都要读取文件生成BufferedImage, 创建过多的对象
		long startTime = System.currentTimeMillis();
		Image image = new Image(bufferedImage, imageLocation);

		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		int actualWidth = image.getWidth();
		int actualHeight = image.getHeight();
		int cropWidth = 0; // 要截取的高度
		int cropHeight = 0;
		double actualRatio = new Double(actualWidth) / new Double(actualHeight);
		double ratio = new Double(newWidth) / new Double(newHeight);

		boolean needCrop = true;
		if (actualRatio == ratio) {
			needCrop = false;
		}
		if (actualRatio < ratio) { // 截去高出的部分
			cropHeight = (int) Math.round(actualWidth / ratio);
			startY = (int) Math.round((actualHeight - cropHeight) / 2);
			// logger.debug("crop new height: " + startY);
			endY = startY + cropHeight;
			endX = actualWidth;
		} else if (actualRatio > ratio) { // 截去宽出的部分
			cropWidth = (int) Math.round(actualHeight * ratio);
			startX = (int) Math.round((actualWidth - cropWidth) / 2);
			// logger.debug("crop width: " + startX);
			endX = startX + cropWidth;
			endY = actualHeight;
		}
		if (needCrop) {
			image.crop(startX, startY, endX, endY);
		}
		image.resize2((int) newWidth, (int) newHeight);

		String originalName = String.valueOf("");
		if (small != null && small.trim().length() > 0) {
			String $temps = "_" + small;
			String beginName = rname.substring(0, rname.lastIndexOf("."));
			String endName = rname.substring(rname.lastIndexOf("."));
			originalName = imageLocation + File.separator + beginName + $temps + endName;
		} else {
			String $temps = "_" + (int) newWidth + "_" + (int) newHeight;
			String beginName = rname.substring(0, rname.lastIndexOf("."));
			String endName = rname.substring(rname.lastIndexOf("."));
			originalName = imageLocation + File.separator + beginName + $temps + endName;
		}

		File $file = new File(originalName);
		if (!$file.getParentFile().exists()) {
			$file.getParentFile().mkdirs();
		}
		image.saveAs($file.getAbsolutePath());
		long entTime = System.currentTimeMillis();
		logger.info("converting: " + imageLocation + " [" + newWidth + "/" + newHeight + "] pre img cost time:" + (entTime - startTime) + "ms");
		return $file.getParent();
	}

	public static void main(String[] args) throws Exception {
		String path = "D:\\Users\\renliangrong\\Desktop\\3a367659a16010f1ad0f199859eb32890e97a496.jpg";
		ImageUtils imageUtils = new ImageUtils();
		File file = new File(path);
		BufferedImage image = new Image(file).getAsBufferedImage();
		imageUtils.convertThenSave("D:\\Users\\renliangrong\\Desktop\\", image, 440, 440, "3a367659a16010f1ad0f199859eb32890e97a496.jpg", null);
	}

}