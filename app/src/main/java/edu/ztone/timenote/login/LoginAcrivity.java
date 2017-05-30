package edu.ztone.timenote.login;

import java.util.List;

import com.example.timenote.R;

import edu.ztone.timenote.activity.MainActivity;
import edu.ztone.timenote.biz.UserBiz;
import edu.ztone.timenote.biz.impl.UserBizImpl;
import edu.ztone.timenote.po.User;
import edu.ztone.timenote.register.RegisterActivityOne;
import edu.ztone.timenote.util.MyApplication;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginAcrivity extends Activity {
	public static LoginAcrivity Logininstance = null;
	private EditText LEditTextname;
	private EditText LEditTextpwd;
	private Button LButtonsubmit;
	private Button LButtonRegister;
	private MyApplication LMyApplication;
	private SharedPreferences sharedPreferences;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.login_layout);
		sharedPreferences = getSharedPreferences("condition",
				Context.MODE_PRIVATE);
		if (sharedPreferences.getInt("userid", -1) != -1) {
			intent = new Intent();
			intent.setClass(LoginAcrivity.this, MainActivity.class);
			startActivity(intent);
			LoginAcrivity.this.finish();
			Log.i("text", "111111");

		}
		LMyApplication = (MyApplication) getApplication();
		Logininstance = this;
		LEditTextname = (EditText) findViewById(R.id.login_layout_uname);
		LEditTextpwd = (EditText) findViewById(R.id.login_layout_upwd);
		LButtonsubmit = (Button) findViewById(R.id.login_layout_btnsubmit);
		LButtonsubmit.setOnClickListener(new MyClickListenerListener());

		LButtonRegister = (Button) findViewById(R.id.login_layout_btnregister);
		LButtonRegister.setOnClickListener(new MyClickListenerListener());

		Intent intent = getIntent();
		if (intent.getStringExtra("uname") != null) {
			LEditTextname.setText(intent.getStringExtra("uname"));
		}

	}

	private class MyClickListenerListener implements OnClickListener {
		UserBiz userBiz = new UserBizImpl();

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.login_layout_btnsubmit:
				String msg = "";
				String uname = LEditTextname.getText().toString().trim();
				String upwd = LEditTextpwd.getText().toString().trim();
				// 数据库
				List<User> lstMessage = userBiz.find(LoginAcrivity.this);
				if (uname.length() == 0 || upwd.length() == 0)
					Toast.makeText(LoginAcrivity.this, "请输入完整信息!",
							Toast.LENGTH_LONG).show();
				else {
					for (User user : lstMessage) {

						String password = user.getPassword();
						String account = user.getAccount();

						if (uname.equals(account)) {
							if (upwd.equals(password)) {
								msg = "欢迎" + account;
								Toast.makeText(LoginAcrivity.this, msg,
										Toast.LENGTH_LONG).show();
								intent = new Intent();

								LMyApplication.setUserid(user.getUserid() - 1);
								Editor editor = sharedPreferences.edit();
								editor.putInt("userid", user.getUserid() - 1);
								editor.commit();
								Log.i("text", "2" + sharedPreferences.getInt("userid", -1));
								intent.setClass(LoginAcrivity.this,
										MainActivity.class);
								startActivity(intent);
								LoginAcrivity.this.finish();
								break;
							} else {
								msg = "密码错误或找不到账户名。";
								Toast.makeText(LoginAcrivity.this, msg,
										Toast.LENGTH_LONG).show();
							}
						}else{
							msg = "密码错误或找不到账户名。";
							Toast.makeText(LoginAcrivity.this, msg,
									Toast.LENGTH_LONG).show();
						}

					}

				}
				break;
			case R.id.login_layout_btnregister:
				Intent intent = new Intent();
				intent.setClass(LoginAcrivity.this, RegisterActivityOne.class);
				startActivity(intent);
				break;

			default:
				break;
			}

		}
	}

}
