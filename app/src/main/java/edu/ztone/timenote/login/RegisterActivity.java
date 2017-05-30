package edu.ztone.timenote.login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.timenote.R;

import edu.ztone.timenote.biz.UserBiz;
import edu.ztone.timenote.biz.impl.UserBizImpl;
import edu.ztone.timenote.po.User;
import edu.ztone.timenote.util.MyApplication;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText REditTextname;
	private EditText REditTextpwd;
	private EditText REditTextrpwd;
	private Spinner RSpinner1;
	private Spinner RSpinner2;
	// private Button btnRegister;
	private RadioGroup RRadioGroup;
	private ImageView RImageViewtx;
	private ActionBar RActionBar;
	private User user;

	private Uri imageuri;
	private MyApplication RMyApplication;
	private List<String> citylist;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register_layout);
		user = new User();
		user.setPhoto("");

		REditTextname = (EditText) findViewById(R.id.register_layout_uname);
		REditTextpwd = (EditText) findViewById(R.id.register_layout_upwd);
		REditTextrpwd = (EditText) findViewById(R.id.register_layout_rpwd);
		// btnRegister = (Button) findViewById(R.id.btn_register);
		// btnRegister.setOnClickListener(new MyClickListener());

		RSpinner1 = (Spinner) findViewById(R.id.register_layout_spinner1);
		RSpinner2 = (Spinner) findViewById(R.id.register_layout_spinner2);

		RActionBar = getActionBar();
		RActionBar.setDisplayHomeAsUpEnabled(true);
		RActionBar.setDisplayShowHomeEnabled(false);

		RSpinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());

		RImageViewtx = (ImageView) findViewById(R.id.register_layout_imagetx);
		RImageViewtx.setOnClickListener(new MyClickListener());
		
		
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionbar:
			String rname = REditTextname.getText().toString().trim();
			String rpwd = REditTextpwd.getText().toString().trim();
			String rrpwd = REditTextrpwd.getText().toString().trim();
			String msg = "";
			if (rname.length() == 0 || rpwd.length() == 0
					|| rrpwd.length() == 0)
				Toast.makeText(RegisterActivity.this, "请输入完整信息!",
						Toast.LENGTH_LONG).show();
			else if (rpwd.length() < 6) {
				Toast.makeText(RegisterActivity.this, "密码不能少于六位！",
						Toast.LENGTH_LONG).show();
			} else {
				if (rpwd.equals(rrpwd)) {
					msg = "注册成功";
					Toast.makeText(RegisterActivity.this, msg,
							Toast.LENGTH_LONG).show();


					// 将数据导入数据库
					boolean flag1 = false;
					UserBiz userBiz = new UserBizImpl();
					List<User> lstMessage = userBiz.find(RegisterActivity.this);
					String rspinner1 = RSpinner1.toString().trim();
					String rspinner2 = RSpinner2.toString().trim();
					
					user.setAccount(rname);
					// 比较是否账号已存在
					for (User user1 : lstMessage) {
						String account1 = user1.getAccount();
						if (account1.equals(rname)) {
							flag1 = true;
						}
					}
					if (!flag1) {
						user.setPassword(rpwd);
						user.setProvince(rspinner1);
						user.setCity(rspinner2);
						

						boolean flag = userBiz.add(RegisterActivity.this, user);

						String msg1 = flag ? "添加成功" : "添加失败";
						Toast.makeText(getApplicationContext(), msg1,
								Toast.LENGTH_LONG).show();

						Intent intent = new Intent();
						intent.putExtra("uname", rname);
						intent.setClass(RegisterActivity.this,
								LoginAcrivity.class);
						LoginAcrivity.Logininstance.finish();
						RegisterActivity.this.finish();
						startActivity(intent);
					} else {
						msg = "账号已存在";
						Toast.makeText(getApplicationContext(), msg,
								Toast.LENGTH_LONG).show();

					}
				} else {
					msg = "注册失败";
					Toast.makeText(RegisterActivity.this, msg,
							Toast.LENGTH_LONG).show();
				}
			}
			break;

		case android.R.id.home:
			Log.i("text", "111111");
			finish();
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuinflater = getMenuInflater();
		menuinflater.inflate(R.menu.actionbar_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		// 判断是否正常接收到返回的数据
		if (resultCode != RESULT_OK) {
			return;
		}
		
		Bitmap bitmap = null;
		ContentResolver contentResolver = getContentResolver();
		// 解析返回的图片资源
		if (requestCode == 200) {
			// 获取图片资源的uri地址
			imageuri = data.getData();
			try {
				//使用MediaStore类的方法完成解析并将解析的结果存放到bitmap中
				bitmap = MediaStore.Images.Media.getBitmap(contentResolver,
						imageuri);
				
				//将用户头像存入数据库中
				user.setPhoto(imageuri.toString());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// bitmap绑定imageView组件，显示选中的图片
			this.RImageViewtx.setImageBitmap(bitmap);
		}
	}

	private class MyOnItemSelectedListener implements
			AdapterView.OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			citylist = new ArrayList<String>();

			switch (arg2) {
			case 0:
				citylist.add("̫太原市");
				citylist.add("阳泉市");
				citylist.add("大同市");
				adapter = new ArrayAdapter<String>(RegisterActivity.this,
						android.R.layout.simple_spinner_item, citylist);
				RSpinner2.setAdapter(adapter);
				break;
			case 1:
				citylist.add("张家口");
				citylist.add("石家庄");
				citylist.add("保定市");
				adapter = new ArrayAdapter<String>(RegisterActivity.this,
						android.R.layout.simple_spinner_item, citylist);
				RSpinner2.setAdapter(adapter);
				break;
			case 2:
				citylist.add("安阳市");
				citylist.add("开封市");
				citylist.add("洛阳市");
				adapter = new ArrayAdapter<String>(RegisterActivity.this,
						android.R.layout.simple_spinner_item, citylist);
				RSpinner2.setAdapter(adapter);
				break;

			default:
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}

	private class MyClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.register_layout_imagetx:
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, 200);

			default:
				break;
			}

		}

	}
}
