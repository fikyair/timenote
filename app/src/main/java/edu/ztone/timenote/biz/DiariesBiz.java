package edu.ztone.timenote.biz;

import java.util.List;

import android.content.Context;
import edu.ztone.timenote.po.Diaries;

public interface DiariesBiz {

	public abstract boolean add(final Context mContext, final Diaries diaries);
	public abstract List<Diaries> find(final Context mContext);
	public abstract boolean change(final Context mContext, final Diaries diaries, final int did);
	public abstract boolean drop(final Context mContext, final int did);
}
