package edu.ztone.timenote.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import edu.ztone.timenote.dao.DiariesDao;
import edu.ztone.timenote.datasource.SQLManager;
import edu.ztone.timenote.po.Diaries;

public class DiariesDaoImpl implements DiariesDao {

	private SQLManager sqlManager = null;

	public DiariesDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		this.sqlManager = new SQLManager();
	}

	@Override
	public boolean insert(Diaries diaries, SQLiteDatabase sqLiteDatabase)
			throws SQLException {
		// TODO Auto-generated method stub
		// 拆分属性
		int weather = diaries.getWeather();
		String dcontent = diaries.getDcontent();
		int fontsize = diaries.getFontsize();
		int fontcolor = diaries.getFontcolor();
		int fontfamily = diaries.getFontfamily();
		String ddate = diaries.getDdate();
		int mood = diaries.getMood();
		String photo1 = diaries.getPhoto1();
		String photo2 = diaries.getPhoto2();
		String photo3 = diaries.getPhoto3();
		String photo4 = diaries.getPhoto4();
		String dweek = diaries.getDweek();

		// 组织SQL语句
		String strSQL = "insert into diaries(weather,dcontent,fontsize,fontcolor,fontfamily,ddate,mood,photo1,photo2,photo3,photo4,dweek) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] bindArgs = new Object[] { weather, dcontent,
				fontsize, fontcolor, fontfamily, ddate, mood, photo1, photo2, photo3, photo4, dweek};

		// 调用sqlManager对象中的方法完成数据库操作
		return this.sqlManager.execWritableBySQL(sqLiteDatabase, strSQL,
				bindArgs);
	}

	@Override
	public List<Diaries> select(SQLiteDatabase sqLiteDatabase)
			throws SQLException {
		// TODO Auto-generated method stub
		// 组织SQL语句
		String strSQL = "select did,weather,dcontent,fontsize,fontcolor,fontfamily,ddate,mood,photo1,photo2,photo3,photo4,dweek from diaries order by did";
		String[] selectionArgs = new String[] {};

		// 调用sqlManager对象中的方法完成数据库操作
		Cursor cursor = this.sqlManager.execReadableBySQL(sqLiteDatabase,
				strSQL, selectionArgs);

		// ------ 数据库结构的转换------
		// 创建一个空集合
		List<Diaries> lstMessages = new ArrayList<Diaries>();
		// 使用循环遍历游标数据并封装成Message对象装载到List集合中
		while (cursor.moveToNext()) {
			// 创建一个空的Message对象
			Diaries diaries = new Diaries();
			// 为message对象的属性赋值
			diaries.setDid(cursor.getInt(0));
			diaries.setWeather(cursor.getInt(1));
			diaries.setDcontent(cursor.getString(2));
			diaries.setFontsize(cursor.getInt(3));
			diaries.setFontcolor(cursor.getInt(4));
			diaries.setFontfamily(cursor.getInt(5));
			// 将数据库中获取的日期类型进行转换String -> Date 转换

			diaries.setDdate(cursor.getString(6));
			diaries.setMood(cursor.getInt(7));
			diaries.setPhoto1(cursor.getString(8));
			diaries.setPhoto2(cursor.getString(9));
			diaries.setPhoto3(cursor.getString(10));
			diaries.setPhoto4(cursor.getString(11));
			diaries.setDweek(cursor.getString(12));

			// 将封装好的message对象添加到集合中
			lstMessages.add(diaries);
		}

		// 返回集合
		return lstMessages;
	}

	@Override
	public boolean update(Diaries diaries, SQLiteDatabase sqLiteDatabase,
			int did) throws SQLException {
		// TODO Auto-generated method stub
		// 拆分属性
		int did1 = did;
		int weather = diaries.getWeather();
		String dcontent = diaries.getDcontent();
		int fontsize = diaries.getFontsize();
		int fontcolor = diaries.getFontcolor();
		int fontfamily = diaries.getFontfamily();
		int mood = diaries.getMood();
		String photo1 = diaries.getPhoto1();
		String photo2 = diaries.getPhoto2();
		String photo3 = diaries.getPhoto3();
		String photo4 = diaries.getPhoto4();
		String dweek = diaries.getDweek();

		// 组织SQL语句
		String strSQL = "update diaries set weather=?,dcontent=?,fontsize=?,fontcolor=?,fontfamily=?,mood=?,photo1=?,photo2=?,photo3=?,photo4=?,dweek=? where did=?";
		Object[] bindArgs = new Object[] { weather, dcontent,
				fontsize, fontcolor, fontfamily, mood, photo1, photo2, photo3, photo4, dweek,did1 };

		// 调用sqlManager对象中的方法完成数据库操作
		return this.sqlManager.execWritableBySQL(sqLiteDatabase, strSQL,
				bindArgs);
	}

	@Override
	public boolean delete(int did, SQLiteDatabase sqLiteDatabase)
			throws SQLException {
		// TODO Auto-generated method stub
		int did1 = did;
		// 组织SQL语句
		String strSQL = "delete from diaries where did=?";
		Object[] bindArgs = new Object[] { did1 };

		// 调用sqlManager对象中的方法完成数据库操作
		return this.sqlManager.execWritableBySQL(sqLiteDatabase, strSQL,
				bindArgs);
	}

}
