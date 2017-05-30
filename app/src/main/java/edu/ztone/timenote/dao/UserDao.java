package edu.ztone.timenote.dao;

import java.util.List;

import edu.ztone.timenote.po.User;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public interface UserDao {

	public abstract boolean insert(final User user,
			final SQLiteDatabase sqLiteDatabase) throws SQLException;

	public abstract List<User> select(final SQLiteDatabase sqLiteDatabase)
			throws SQLException;

	public abstract boolean update(final User user,
			final SQLiteDatabase sqLiteDatabase, final int userid)
			throws SQLException;

	public abstract boolean delete(final int userid,
			final SQLiteDatabase sqLiteDatabase) throws SQLException;
}
