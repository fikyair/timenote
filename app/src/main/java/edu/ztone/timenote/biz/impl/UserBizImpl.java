package edu.ztone.timenote.biz.impl;

import java.util.List;

import edu.ztone.timenote.biz.UserBiz;
import edu.ztone.timenote.dao.UserDao;
import edu.ztone.timenote.dao.impl.UserDaoImpl;
import edu.ztone.timenote.datasource.ConnectionManager;
import edu.ztone.timenote.datasource.TransactionManager;
import edu.ztone.timenote.po.User;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserBizImpl implements UserBiz {

	private UserDao userDao;

	public UserBizImpl() {
		super();
		// TODO Auto-generated constructor stub
		this.userDao = new UserDaoImpl();
	}

	@Override
	public boolean add(Context mContext, User user) {
		// TODO Auto-generated method stub
		// 步骤1：获取数据库连接对象
		ConnectionManager connectionManager = new ConnectionManager();
		SQLiteDatabase sqLiteDatabase = connectionManager.openConnection(
				mContext, "write");

		// 步骤2：开启事务处理
		TransactionManager transactionManager = new TransactionManager();
		transactionManager.beginTransaction(sqLiteDatabase);

		// 步骤3：调用DAO实现添加操作
		try {
			this.userDao.insert(user, sqLiteDatabase);
		} catch (SQLException ex) {
			return false;
		} catch (RuntimeException ex) {
			return false;
		}
		// 步骤4：提交事务
		transactionManager.commitTransaction(sqLiteDatabase);

		// 步骤5：关闭事务
		transactionManager.closeTransaction(sqLiteDatabase);

		// 步骤6：关闭数据库连接
		connectionManager.closeConnection(sqLiteDatabase);

		return true;
	}

	@Override
	public List<User> find(Context mContext) {
		// TODO Auto-generated method stub
		// 步骤1：获取数据库连接对象
		ConnectionManager connectionManager = new ConnectionManager();
		SQLiteDatabase sqLiteDatabase = connectionManager.openConnection(
				mContext, "read");

		// 步骤2：调用Dao层方法完成数据库操作
		List<User> lstMessage = this.userDao.select(sqLiteDatabase);

		// 步骤3：关闭数据库连接
		connectionManager.closeConnection(sqLiteDatabase);

		// 返回结果
		return lstMessage;
	}

	public boolean change(Context mContext, User user, int userid) {
		// 步骤1：获取数据库连接对象
		ConnectionManager connectionManager = new ConnectionManager();
		SQLiteDatabase sqLiteDatabase = connectionManager.openConnection(
				mContext, "write");

		// 步骤2：开启事务处理
		TransactionManager transactionManager = new TransactionManager();
		transactionManager.beginTransaction(sqLiteDatabase);

		// 步骤3：调用DAO实现添加操作
		try {
			this.userDao.update(user, sqLiteDatabase, userid);
		} catch (SQLException ex) {
			return false;
		} catch (RuntimeException ex) {
			return false;
		}
		// 步骤4：提交事务
		transactionManager.commitTransaction(sqLiteDatabase);

		// 步骤5：关闭事务
		transactionManager.closeTransaction(sqLiteDatabase);

		// 步骤6：关闭数据库连接
		connectionManager.closeConnection(sqLiteDatabase);

		return true;

	}

	public boolean drop(Context mContext, int userid) {
		// 步骤1：获取数据库连接对象
		ConnectionManager connectionManager = new ConnectionManager();
		SQLiteDatabase sqLiteDatabase = connectionManager.openConnection(
				mContext, "write");

		// 步骤2：开启事务处理
		TransactionManager transactionManager = new TransactionManager();
		transactionManager.beginTransaction(sqLiteDatabase);

		// 步骤3：调用DAO实现添加操作
		try {
			this.userDao.delete(userid, sqLiteDatabase);
		} catch (SQLException ex) {
			return false;
		} catch (RuntimeException ex) {
			return false;
		}
		// 步骤4：提交事务
		transactionManager.commitTransaction(sqLiteDatabase);

		// 步骤5：关闭事务
		transactionManager.closeTransaction(sqLiteDatabase);

		// 步骤6：关闭数据库连接
		connectionManager.closeConnection(sqLiteDatabase);

		return true;

	}

}
