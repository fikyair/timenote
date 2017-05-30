package edu.ztone.timenote.biz;

import java.util.List;

import edu.ztone.timenote.po.User;
import android.content.Context;

public interface UserBiz {

	public abstract boolean add(final Context mContext, final User user);
	public abstract List<User> find(final Context mContext);
	public abstract boolean change(final Context mContext, final User user, final int userid);
	public abstract boolean drop(final Context mContext, final int userid);
}
