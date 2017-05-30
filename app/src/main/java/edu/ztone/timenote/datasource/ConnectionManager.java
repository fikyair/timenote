package edu.ztone.timenote.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ConnectionManager {

	private static final String TAG = "ConnectionManager";
	
	/**
	 * 方法1：开启数据库的连接(读/写)
	 * */
	public SQLiteDatabase openConnection(final Context mContext, final String model){
		// 实例化SQLiteDataBaseHelper对象
		SQLiteDatabaseHelper databaseHelper = new SQLiteDatabaseHelper(mContext);
		Log.i(TAG, "开始连接数据库……");
		// 根据调用的参数model确定读取连接还是写入链接
		SQLiteDatabase sqLiteDatabase =  null;
		if(model.equals("read")){
			sqLiteDatabase = databaseHelper.getReadableDatabase();
			Log.i(TAG, "成功获取数据库读取模式的连接对象");
		}else{
			sqLiteDatabase = databaseHelper.getWritableDatabase();
			Log.i(TAG, "成功获取数据库写入模式的连接对象");
		}
		// 返回有效的数据库连接
		return sqLiteDatabase;
	}
	
	/**
	 * 方法2：关闭数据库的连接
	 * */
	public void closeConnection(final SQLiteDatabase sqLiteDatabase){
		// 判断连接是否有效
		if(sqLiteDatabase != null){
			sqLiteDatabase.close();  // 关闭连接
			Log.i(TAG, "成功关闭数据库连接对象");
		}	
	}
	
}
