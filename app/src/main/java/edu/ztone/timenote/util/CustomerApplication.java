package edu.ztone.timenote.util;

import edu.ztone.timenote.datasource.DBConfig;
import edu.ztone.timenote.datasource.SQLiteDatabaseHelper;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CustomerApplication extends Application {

	private static final String TAG = "CustoemrApplication";
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 创建数据库
		Log.i(TAG, "开始构建项目SQLite数据库……");
		createDatabase(DBConfig.DBNAME);
		
		
	}
	
	/**
	 * 自定一个方法用于初始化数据库对象并构建数据表
	 * */
	private void createDatabase(final String dbName) {
		Log.i("test", "开始创建数据库 " + dbName);
		// 步骤1：创建一个SQLiteDatabaseHelper对象
		// 若dbName数据库已经存在则返回一个连接，否则创建全新数据库
		SQLiteDatabaseHelper databaseHelper = new SQLiteDatabaseHelper(this,
				dbName);
		// 步骤2：使用getWrite……或getRead……方法获取一个有效的连接（自动调用onCreate方法完成数据表的构建）
		SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
		// 步骤3：关闭数据库连接对象
		sqLiteDatabase.close();
		Log.i("test", "数据库 " + dbName + "创建完毕!");
	}

	
}
