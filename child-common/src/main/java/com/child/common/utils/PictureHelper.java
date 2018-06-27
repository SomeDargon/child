package com.child.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PictureHelper {
	private static final Logger logger = LoggerFactory
			.getLogger(PictureHelper.class);


	private static final DateTimeFormatter DATE_PATH_FORMATER = DateTimeFormatter
			.ofPattern("yyyy" + File.separator + "MM");


	private static final String DEFAULT_WEB_NorImage_STORE_DIR = File.separator + "pics" + File.separator + "business" + File.separator + "NorImages";


	public static byte[] convertBinaryToByteArray(String base64String) {
		byte[] NorImageData = java.util.Base64.getDecoder().decode(base64String);
		return NorImageData;
	}

	public static String savePic(byte[] data,PicProcessType type,String fileType,String filePath) {

		final String relativePath =DEFAULT_WEB_NorImage_STORE_DIR
				+ File.separator +type.name().toLowerCase()+ File.separator
				+ DATE_PATH_FORMATER.format(LocalDate.now()) + File.separator
				+ UUID.randomUUID().toString()+fileType;

		persistFile(data,filePath+relativePath);
		return relativePath;
	}

	private static void persistFile(byte[] binary,String fileName){
		FileOutputStream output = null;
		try {
			File file = new File(fileName);
			File parent = file.getParentFile();
			if (!parent.exists()) {
				System.out.println("创建文件夹");
				parent.mkdirs();
			}
			output = new FileOutputStream(fileName);
			output.write(binary);
			output.flush();
			System.out.println("保存文件");
		} catch (IOException e) {
			logger.error("Cannot write " + fileName + " to disk.");
			logger.error(e.getMessage(), e);
		} finally {
			if (output != null) {
				try {
					output.close();
					binary = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
