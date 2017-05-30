package edu.ztone.timenote.register;

import com.example.timenote.R;

import edu.ztone.timenote.biz.UserBiz;
import edu.ztone.timenote.biz.impl.UserBizImpl;
import edu.ztone.timenote.po.User;
import edu.ztone.timenote.util.MyApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivityTwo extends Activity {
	private Button bt_two;
	private EditText REditTextpwd;
	private EditText REditTextrpwd;
	private MyApplication rMyApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registertwo_layout);
		REditTextpwd = (EditText) findViewById(R.id.RT_register_layout_upwd);
		REditTextrpwd = (EditText) findViewById(R.id.RT_register_layout_rpwd);
		bt_two = (Button) findViewById(R.id.RT_bt_two);
		bt_two.setOnClickListener(new MyListenerClick());
		rMyApplication = (MyApplication) getApplication();
	}

	private class MyListenerClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.RT_bt_two:
				String rpwd = REditTextpwd.getText().toString().trim();
				String rrpwd = REditTextrpwd.getText().toString().trim();
				if (rpwd.length() == 0 && rrpwd.length() == 0) {
					Toast.makeText(RegisterActivityTwo.this, "亲~请填写密码!",
							Toast.LENGTH_SHORT).show();
				} else if (rpwd.length() < 6) {
					Toast.makeText(RegisterActivityTwo.this, "密码不能少于六位！",
							Toast.LENGTH_SHORT).show();

				} else if (!rpwd.equals(rrpwd)) {
					Toast.makeText(RegisterActivityTwo.this, "两次输入密码不一致！",
							Toast.LENGTH_SHORT).show();

				} else {
					rMyApplication.setUser_Pwd(rpwd);
					Intent intent = new Intent();
					intent.setClass(RegisterActivityTwo.this,
							RegisterActivityThree.class);
					startActivity(intent);
					finish();
				}
				
				break;

			default:
				break;
			}

		}

	}
}
