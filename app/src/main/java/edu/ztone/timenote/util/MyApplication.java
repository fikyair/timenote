package edu.ztone.timenote.util;

import edu.ztone.timenote.lock.LockPatternUtils;
import android.app.Application;
import android.os.Handler;

public class MyApplication extends Application {
	private static MyApplication mInstance;
	private LockPatternUtils mLockPatternUtils;
	private String User_Account;
	private String User_Pwd;
	private String User_sex;
	private String User_call;
	private int restyle;
	private int userid;
	private String province;
	private String city;
	private Handler mHandler = null;

	public String getUser_call() {
		return User_call;
	}

	public void setUser_call(String user_call) {
		User_call = user_call;
	}

	public String getUser_sex() {
		return User_sex;
	}

	public void setUser_sex(String user_sex) {
		User_sex = user_sex;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}


	public Handler getHandler() {
		return mHandler;
	}

	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	public String getUser_Account() {
		return User_Account;
	}

	public void setUser_Account(String user_Account) {
		User_Account = user_Account;
	}

	public String getUser_Pwd() {
		return User_Pwd;
	}

	public void setUser_Pwd(String user_Pwd) {
		User_Pwd = user_Pwd;
	}

	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setRestyle(0x7f020036);
		mInstance = this;
		mLockPatternUtils = new LockPatternUtils(this);
	}

	public int getRestyle() {
		return restyle;
	}

	public void setRestyle(int restyle) {
		this.restyle = restyle;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}

}
