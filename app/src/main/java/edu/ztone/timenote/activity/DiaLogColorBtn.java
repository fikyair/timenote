package edu.ztone.timenote.activity;

import com.example.timenote.R;

import edu.ztone.timenote.util.MyApplication;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiaLogColorBtn extends Dialog {

	private Context mContext;
	private MyApplication MyApplication;
	private int i;
	private TextView txtStyle1;
	private TextView txtStyle2;
	private TextView txtStyle3;
	private TextView txtStyle4;
	private TextView txtStyle5;
	private TextView txtStyle6;
	private Handler mHandler;

	public DiaLogColorBtn(Context context) {
		super(context);
		mContext = context;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.colordialog_layout, null);
		setContentView(view);

		txtStyle1 = (TextView) view.findViewById(R.id.style1);
		txtStyle2 = (TextView) view.findViewById(R.id.style2);
		txtStyle3 = (TextView) view.findViewById(R.id.style3);
		txtStyle4 = (TextView) view.findViewById(R.id.style4);
		txtStyle5 = (TextView) view.findViewById(R.id.style5);
		txtStyle6 = (TextView) view.findViewById(R.id.style6);

		txtStyle1.setOnClickListener(new ColorClickListener());
		txtStyle2.setOnClickListener(new ColorClickListener());
		txtStyle3.setOnClickListener(new ColorClickListener());
		txtStyle4.setOnClickListener(new ColorClickListener());
		txtStyle5.setOnClickListener(new ColorClickListener());
		txtStyle6.setOnClickListener(new ColorClickListener());
		MainActivity mainActivity = (MainActivity) mContext;
		MyApplication = (edu.ztone.timenote.util.MyApplication) mainActivity.getApplication();
		mHandler = MyApplication.getHandler();

	}
	
	public int getI() {
		return i;
	}
	

	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		super.setTitle(title);
	}

	private class ColorClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Message msg = Message.obtain();
			switch (v.getId()) {
			case R.id.style1:
				i = R.drawable.leftbackground1;
				msg.obj = i;
				msg.what = 2;
				MyApplication.setRestyle(i);
				mHandler.sendMessage(msg);
				
				break;
			case R.id.style2:
				i = R.drawable.leftbackground2;
				msg.obj = i;
				msg.what = 2;
				MyApplication.setRestyle(i);
				mHandler.sendMessage(msg);
				break;
			case R.id.style3:
				i = R.drawable.leftbackground3;
				msg.obj = i;
				msg.what = 2;
				MyApplication.setRestyle(i);
				mHandler.sendMessage(msg);
				break;
			case R.id.style4:
				i = R.drawable.leftbackground4;
				msg.obj = i;
				msg.what = 2;
				MyApplication.setRestyle(i);
				mHandler.sendMessage(msg);
				break;
			case R.id.style5:
				i = R.drawable.leftbackground5;
				msg.obj = i;
				msg.what = 2;
				MyApplication.setRestyle(i);
				mHandler.sendMessage(msg);
				break;
			case R.id.style6:
				i = R.drawable.leftbackground6;
				msg.obj = i;
				msg.what = 2;
				MyApplication.setRestyle(i);
				mHandler.sendMessage(msg);
				break;

			default:
				break;
			}

		}

	}

}
