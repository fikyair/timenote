package edu.ztone.timenote.activity;

import com.example.timenote.R;

import edu.ztone.timenote.login.LoginAcrivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome_layout);
		welcomeUI();
	}

	private void welcomeUI() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					Message message = new Message();
					welHandler.sendMessage(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	Handler welHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			welcomeFunction();
		}

	};

	public void welcomeFunction() {
		Intent intent = new Intent();
		intent.setClass(WelcomeActivity.this, LoginAcrivity.class);
		startActivity(intent);
		WelcomeActivity.this.finish();
	}

}
