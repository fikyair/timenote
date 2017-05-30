package edu.ztone.timenote.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

	private final static int VERSION = 1;

	// 构造方法用于自动构建数据库，通过调用时传入数据库名称参数自动完成
	public SQLiteDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DBConfig.DBNAME, factory, version);
		// TODO Auto-generated constructor stub
	}

	// 构造方法重载：当需要实现数据库操作时，使用该重载方法创建或连接数据库
	public SQLiteDatabaseHelper(Context context, String name) {
		super(context, DBConfig.DBNAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	// 构造方法重载：当需要实现数据库更新时，使用该重载方法连接数据库
	public SQLiteDatabaseHelper(Context context, String name, int version) {
		super(context, DBConfig.DBNAME, null, version);
		// TODO Auto-generated constructor stub
	}

	// 构造方法重载：当需要实现数据库更新时，使用该重载方法连接数据库
	public SQLiteDatabaseHelper(Context context) {
		super(context, DBConfig.DBNAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	// 该方法用于创建数据库对象（表、约束、索引……）
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		// TODO Auto-generated method stub
		Log.i("test", "开始创建数据表message……");
		// 编写创建数据表的sql语句
		String sql = "create table user(userid integer primary key autoincrement,account varchar(30),password varchar(30),sex varchar(10),province varchar(30),city varchar(30),photo varchar(100),nickname varchar(30))";
		sqLiteDatabase.execSQL(sql);
		Log.i("test", "用户数据表创建成功");
		sql = "create table diaries(did integer primary key autoincrement,weather integer,dcontent text,fontsize integer,fontcolor integer,fontfamily integer,ddate data,mood integer,photo1 varchar(100),photo2 varchar(100),photo3 varchar(100),photo4 varchar(100),dweek varchar(10))";
		sqLiteDatabase.execSQL(sql);
		Log.i("test", "日记数据表创建成功");
	}

	// 该方法主要用与数据库更新使用
	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int arg1, int arg2) {
		// TODO Auto-generated method stub
		// Log.i("test", "开始更新数据表message……");
		// // 编写更新数据表的SQL语句
		// String sql = "drop table if exists message";
		// Log.i("test", "删除表sql:> " + sql);
		// // 执行
		// sqLiteDatabase.execSQL(sql);
		// sql =
		// "create table message(mid integer primary key autoincrement, title varchar(20), publish date default CURRENT_TIMESTAMP, abstractInfo varchar(100))";
		// Log.i("test", "创建数据表sql:> " + sql);
		//
		// sqLiteDatabase.execSQL(sql);
		// Log.i("test", "数据表更新成功!");
	}

}
