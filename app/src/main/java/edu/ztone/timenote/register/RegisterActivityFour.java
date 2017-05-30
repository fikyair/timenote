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

public class RegisterActivityFour extends Activity {
	private Button bt_four;
	private EditText editText;
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registerfour_layout);
		myApplication = (MyApplication) getApplication();
		bt_four = (Button) findViewById(R.id.RF_bt_four);
		bt_four.setOnClickListener(new MyListenerClick());
		editText = (EditText) findViewById(R.id.RF_register_layout_uname);
	}

	private class MyListenerClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.RF_bt_four:
				String user_name = editText.getText().toString();
				if (user_name != null) {
					myApplication.setUser_call(user_name);
					Intent intent = new Intent();
					intent.setClass(RegisterActivityFour.this,
							RegisterActivityFive.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(RegisterActivityFour.this, "昵称输入有误！请重新输入！",
							Toast.LENGTH_SHORT).show();
				}

				break;

			default:
				break;
			}

		}

	}
}
