package edu.ztone.timenote.datasource;

import java.io.IOException;
import java.util.Properties;

public class DBConfig {

	private static final Properties PROPERTIES = new Properties();
	
	// static静态块
	static{		
		try {
			// 使用stream和反射机制reflact动态读取配置文件
			PROPERTIES.load(DBConfig.class.getResourceAsStream("database.cfg.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 设置常量读取配置文件中所有数据
	public static final String DBNAME = PROPERTIES.getProperty("DBNAME");
}
