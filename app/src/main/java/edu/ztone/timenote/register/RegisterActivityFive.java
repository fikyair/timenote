package edu.ztone.timenote.register;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.timenote.R;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ztone.timenote.biz.UserBiz;
import edu.ztone.timenote.biz.impl.UserBizImpl;
import edu.ztone.timenote.login.LoginAcrivity;
import edu.ztone.timenote.po.User;
import edu.ztone.timenote.util.CircularImage;
import edu.ztone.timenote.util.MyApplication;

public class RegisterActivityFive extends Activity {
	private CircularImage circularImage;
	private Button button;
	private Intent intent;
	private MyApplication myApplication;
	private User user;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registerfive_layout);
		myApplication = (MyApplication) getApplication();
		user = new User();
		user.setPhoto("");

		button = (Button) findViewById(R.id.RFV_bt_five);
		button.setOnClickListener(new MyOCListener());
		bitmap = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.moren);
		circularImage = (CircularImage) findViewById(R.id.RFV_register_layout_imagetx);
		circularImage.setImageBitmap(bitmap);
		circularImage.setOnClickListener(new MyOCListener());

	}

	private class MyOCListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			/*case R.id.RFV_register_layout_imagetx:
				intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image*//**//*");
				startActivityForResult(intent, 200);
				break;*/
			case R.id.RFV_bt_five:
				String user_account = myApplication.getUser_Account();
				String user_pwd = myApplication.getUser_Pwd();
				String user_sex = myApplication.getUser_sex();
				String user_name = myApplication.getUser_call();
				String province = myApplication.getProvince();
				String city = myApplication.getCity();
				user.setAccount(user_account);
				user.setPassword(user_pwd);
				user.setSex(user_sex);
				user.setNickname(user_name);
				user.setProvince(province);
				user.setCity(city);
				boolean flag = false;
				UserBiz userBiz = new UserBizImpl();
				if (userBiz.add(RegisterActivityFive.this, user)) {
					Toast.makeText(RegisterActivityFive.this,
							"注册成功！ 请登录！", Toast.LENGTH_SHORT)
							.show();
					intent = new Intent();
					intent.putExtra("uname", user_account);
					intent.setClass(RegisterActivityFive.this,LoginAcrivity.class);
					LoginAcrivity.Logininstance.finish();
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(RegisterActivityFive.this,
							"注册失败！ 滚回去重注册！mdzz!", Toast.LENGTH_SHORT)
							.show();
				}
				

			default:
				break;
			}
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// 判断是否正常接收到返回的数据
		if (resultCode != RESULT_OK) {
			return;
		}
		Uri imageuri;
		Bitmap bitmap = null;
		ContentResolver contentResolver = getContentResolver();
		// 解析返回的图片资源
		if (requestCode == 200) {
			// 获取图片资源的uri地址
			imageuri = data.getData();
			try {
				// 使用MediaStore类的方法完成解析并将解析的结果存放到bitmap中
				bitmap = MediaStore.Images.Media.getBitmap(contentResolver,
						imageuri);
				user.setPhoto(imageuri.toString());
				// 将用户头像存入数据库中
				user.setPhoto(imageuri.toString());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// bitmap绑定imageView组件，显示选中的图片
			this.circularImage.setImageBitmap(bitmap);
		}
	}

}
