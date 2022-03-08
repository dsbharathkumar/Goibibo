package com.goibibo.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataHandlers extends ReusableMethods{

	public static String getProperty(String fileName, String key) {
		File f = new File(fileName);
		Properties prop = null;
		String value = null;
		try {
			FileInputStream fis = new FileInputStream(f);
			prop = new Properties();
			prop.load(fis);
			value = prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
}

