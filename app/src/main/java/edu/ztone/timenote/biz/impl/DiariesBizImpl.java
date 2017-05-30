package edu.ztone.timenote.biz.impl;

import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import edu.ztone.timenote.biz.DiariesBiz;
import edu.ztone.timenote.dao.DiariesDao;
import edu.ztone.timenote.dao.impl.DiariesDaoImpl;
import edu.ztone.timenote.datasource.ConnectionManager;
import edu.ztone.timenote.datasource.TransactionManager;
import edu.ztone.timenote.po.Diaries;
import edu.ztone.timenote.po.User;

public class DiariesBizImpl implements DiariesBiz {

	private DiariesDao diariesDao;

	public DiariesBizImpl() {
		super();
		// TODO Auto-generated constructor stub
		this.diariesDao = new DiariesDaoImpl();
	}

	@Override
	public boolean add(Context mContext, Diaries diaries) {
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
			this.diariesDao.insert(diaries, sqLiteDatabase);
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
	public List<Diaries> find(Context mContext) {
		// TODO Auto-generated method stub
		// 步骤1：获取数据库连接对象
		ConnectionManager connectionManager = new ConnectionManager();
		SQLiteDatabase sqLiteDatabase = connectionManager.openConnection(
				mContext, "read");

		// 步骤2：调用Dao层方法完成数据库操作
		List<Diaries> lstMessage = this.diariesDao.select(sqLiteDatabase);

		// 步骤3：关闭数据库连接
		connectionManager.closeConnection(sqLiteDatabase);

		// 返回结果
		return lstMessage;
	}

	public boolean change(Context mContext, Diaries diaries, int did) {
		// 步骤1：获取数据库连接对象
		ConnectionManager connectionManager = new ConnectionManager();
		SQLiteDatabase sqLiteDatabase = connectionManager.openConnection(
				mContext, "write");

		// 步骤2：开启事务处理
		TransactionManager transactionManager = new TransactionManager();
		transactionManager.beginTransaction(sqLiteDatabase);

		// 步骤3：调用DAO实现添加操作
		try {
			this.diariesDao.update(diaries, sqLiteDatabase, did);
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

	public boolean drop(Context mContext, int did) {
		// 步骤1：获取数据库连接对象
		ConnectionManager connectionManager = new ConnectionManager();
		SQLiteDatabase sqLiteDatabase = connectionManager.openConnection(
				mContext, "write");

		// 步骤2：开启事务处理
		TransactionManager transactionManager = new TransactionManager();
		transactionManager.beginTransaction(sqLiteDatabase);

		// 步骤3：调用DAO实现添加操作
		try {
			this.diariesDao.delete(did, sqLiteDatabase);
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
