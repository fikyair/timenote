package edu.ztone.timenote.datasource;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLManager {

	private static final String TAG = "SQLManager";

	/**
	 * 方法1：专门处理写入的操作的方法（insert/delete/update）
	 * */
	public boolean execWritableBySQL(final SQLiteDatabase sqLiteDatabase,
			final String strSQL, final Object... bindArgs) {

		if (sqLiteDatabase != null) {
			try {
				Log.i(TAG, "执行写入操作SQL:> " + strSQL);
				sqLiteDatabase.execSQL(strSQL, bindArgs);
				return true;
			} catch (SQLException ex) {
				Log.e(TAG, "执行数据库写入操作异常：" + ex.getMessage());
				return false;
			}
		} else {
			Log.w(TAG, "异常：数据库连接对象SQLiteDatabase失效");
			return false;
		}
	}
	
	/**
	 * 方法2：专门处理读取的操作的方法（select）
	 * */
	public Cursor execReadableBySQL(final SQLiteDatabase sqLiteDatabase,
			final String strSQL, final String... selectionArgs) {

		if (sqLiteDatabase != null) {
			try {
				Log.i(TAG, "执行读取操作SQL:> " + strSQL);
				Cursor cursor = sqLiteDatabase.rawQuery(strSQL, selectionArgs);
				return cursor;
			} catch (SQLException ex) {
				Log.e(TAG, "执行数据库读取操作异常：" + ex.getMessage());
				return null;
			}
		} else {
			Log.w(TAG, "异常：数据库连接对象SQLiteDatabase失效");
			return null;
		}
	}
}
