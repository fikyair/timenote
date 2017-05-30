package edu.ztone.timenote.datasource;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TransactionManager {

	private static final String TAG = "TransactionManager";
	
	/**
	 * 方法1：开启一个事务
	 * */
	public void beginTransaction(final SQLiteDatabase sqLiteDatabase){
		if(sqLiteDatabase != null){
			sqLiteDatabase.beginTransaction();
			Log.i(TAG, "开启事务处理……");
		}
	}
	
	/**
	 * 方法2：提交一个事务
	 * */
	public void commitTransaction(final SQLiteDatabase sqLiteDatabase){
		if(sqLiteDatabase != null){
			sqLiteDatabase.setTransactionSuccessful();
			Log.i(TAG, "提交事务……");
		}
	}
	
	/**
	 * 方法3：关闭一个事务
	 * */
	public void closeTransaction(final SQLiteDatabase sqLiteDatabase){
		if(sqLiteDatabase != null){
			sqLiteDatabase.endTransaction();
			Log.i(TAG, "关闭事务处理……");
		}
	}
}
