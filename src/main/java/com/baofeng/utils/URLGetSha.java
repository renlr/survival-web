package com.baofeng.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import com.baofeng.carebay.util.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class URLGetSha {
	public static String getImgAndSha(String url) {

		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "gb2312");
			BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
			String tempPath = System.getProperty("java.io.tmpdir");
			String fileName = Constants.UUIDToString() + ".jpg";
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", new File(tempPath + File.separator + fileName));

			InputStream input = new FileInputStream(tempPath + File.separator + fileName);
			String sha1 = DigestUtils.shaHex(input);
			String newName = sha1 + ".jpg";
			String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
			Constants.mkdirs(path);
			InputStream input1 = new FileInputStream(tempPath + File.separator + fileName);
			FileUtils.copyInputStreamToFile(input1, new File(path, newName));
			input.close();
			File f = new File(tempPath + File.separator + fileName);
			f.delete();
			return sha1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
		getImgAndSha("http://pan.baidu.com/s/1kT9R6OB");
	}
}
