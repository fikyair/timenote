package edu.ztone.timenote.dao;

import java.util.List;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import edu.ztone.timenote.po.Diaries;

public interface DiariesDao {
	public abstract boolean insert(final Diaries diaries,
			final SQLiteDatabase sqLiteDatabase) throws SQLException;

	public abstract List<Diaries> select(final SQLiteDatabase sqLiteDatabase)
			throws SQLException;

	public abstract boolean update(final Diaries diaries,
			final SQLiteDatabase sqLiteDatabase, final int did)
			throws SQLException;

	public abstract boolean delete(final int did,
			final SQLiteDatabase sqLiteDatabase) throws SQLException;
}
