package edu.ztone.timenote.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ztone.timenote.dao.UserDao;
import edu.ztone.timenote.datasource.SQLManager;
import edu.ztone.timenote.po.User;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDaoImpl implements UserDao {

	private SQLManager sqlManager = null;
	private static String TAG = "select";

	public UserDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		this.sqlManager = new SQLManager();
	}

	@Override
	public boolean insert(User user, SQLiteDatabase sqLiteDatabase)
			throws SQLException {
		// TODO Auto-generated method stub
		// 拆分属性
		String account = user.getAccount();
		String password = user.getPassword();
		String sex = user.getSex();
		String province = user.getProvince();
		String city = user.getCity();
		String photo = user.getPhoto();
		String nickname = user.getNickname();

		// 组织SQL语句
		String strSQL = "insert into user(account,password,sex,province,city,photo,nickname) values(?,?,?,?,?,?,?)";
		Object[] bindArgs = new Object[] {account,password,sex,province,city,photo,nickname };

		// 调用sqlManager对象中的方法完成数据库操作
		return this.sqlManager.execWritableBySQL(sqLiteDatabase, strSQL,
				bindArgs);
	}

	@Override
	public List<User> select(SQLiteDatabase sqLiteDatabase) throws SQLException {
		// TODO Auto-generated method stub
		// 组织SQL语句
		String strSQL = "select userid,account,password,sex,province,city,photo,nickname from user order by userid";
		String[] selectionArgs = new String[] {};

		// 调用sqlManager对象中的方法完成数据库操作
		Cursor cursor = this.sqlManager.execReadableBySQL(sqLiteDatabase,
				strSQL, selectionArgs);

		// ------ 数据库结构的转换------
		// 创建一个空集合
		List<User> lstMessages = new ArrayList<User>();
		// 使用循环遍历游标数据并封装成Message对象装载到List集合中
		while (cursor.moveToNext()) {
			// 创建一个空的Message对象
			User user = new User();
			// 为message对象的属性赋值
			user.setUserid(cursor.getInt(0));
			user.setAccount(cursor.getString(1));
			user.setPassword(cursor.getString(2));
			user.setSex(cursor.getString(3));
			user.setProvince(cursor.getString(4));
			user.setCity(cursor.getString(5));
			user.setPhoto(cursor.getString(6));
			user.setNickname(cursor.getString(7));

			// 将封装好的message对象添加到集合中
			lstMessages.add(user);
		}

		// 返回集合
		return lstMessages;
	}
	

	@Override
	public boolean update(User user, SQLiteDatabase sqLiteDatabase, int userid)
			throws SQLException {
		// TODO Auto-generated method stub
		// 拆分属性
		int userid1 = userid;
		String account = user.getAccount();
		String password = user.getPassword();
		String sex = user.getSex();
		String province = user.getProvince();
		String city = user.getCity();
		String photo = user.getPhoto();
		String nickname = user.getNickname();

		// 组织SQL语句
		String strSQL = "update user set account=?,password=?,sex=?,province=?,city=?,photo=?,nickname=? where userid=?";
		Object[] bindArgs = new Object[] { account, password, sex, province,
				city, photo, nickname, userid1 };

		// 调用sqlManager对象中的方法完成数据库操作
		return this.sqlManager.execWritableBySQL(sqLiteDatabase, strSQL,
				bindArgs);
	}

	@Override
	public boolean delete(int userid, SQLiteDatabase sqLiteDatabase)
			throws SQLException {
		// TODO Auto-generated method stub
		// 拆分属性
		int userid1 = userid;

		// 组织SQL语句
		String strSQL = "delete from user where userid=?";
		Object[] bindArgs = new Object[] { userid1 };

		// 调用sqlManager对象中的方法完成数据库操作
		return this.sqlManager.execWritableBySQL(sqLiteDatabase, strSQL,
				bindArgs);
	}

}
