package edu.ztone.timenote.register;

import com.example.timenote.R;

import edu.ztone.timenote.util.MyApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivityOne extends Activity {

	private Button bt_one;
	private EditText REditTextname;
	private MyApplication rMyApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registerone_layout);
		this.rMyApplication = (MyApplication) getApplication();
		REditTextname = (EditText) findViewById(R.id.RO_register_layout_uname);
		bt_one = (Button) findViewById(R.id.RO_bt_one);
		bt_one.setOnClickListener(new MyListenerClick());
		
	}

	private class MyListenerClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.RO_bt_one:
				String rname = REditTextname.getText().toString();
				if (rname.length() == 0)
					Toast.makeText(RegisterActivityOne.this, "亲~请填写用户名!",
							Toast.LENGTH_LONG).show();
				else {
					rMyApplication.setUser_Account(rname);
					Intent intent = new Intent();
					intent.setClass(RegisterActivityOne.this,
							RegisterActivityTwo.class);
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
