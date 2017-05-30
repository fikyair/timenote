package edu.ztone.timenote.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.example.timenote.R;

import edu.ztone.timenote.biz.UserBiz;
import edu.ztone.timenote.biz.impl.UserBizImpl;
import edu.ztone.timenote.login.LoginAcrivity;
import edu.ztone.timenote.po.User;
import edu.ztone.timenote.util.CircularImage;
import edu.ztone.timenote.util.MyApplication;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalActivity extends Activity {

	private static final String TAG = "PersonalActivity";
	private ActionBar personalacbar;
	private edu.ztone.timenote.util.CircularImage imgChangePhoto;
	private Uri imgUri;
	private ImageView imgBirthday;
	private ImageView imgNickname;
	final int DIALOG_DATE = 0; // 设置对话框id
	private TextView TV_birthday;
	private TextView TV_nickname;
	private TextView TV_account;
	private TextView TV_sex;
	private Button EX_button;
	private Message message;
	private MyApplication mApplication;
	private User user;
	private SharedPreferences sharedPreferences;

	Calendar cal = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.personal_layout);
		mApplication = (MyApplication) getApplication();
		personalacbar = getActionBar(); // 实例化ActionBar对象
		personalacbar.setDisplayHomeAsUpEnabled(true); // 设置返回箭头
		personalacbar.setDisplayShowHomeEnabled(false); // 取消箭头icon图标
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		TV_nickname = (TextView) findViewById(R.id.TV_nickname);
		EX_button = (Button) findViewById(R.id.ps_bt_logout);

		UserBiz userBiz = new UserBizImpl();
		List<User> lstMessage = userBiz.find(PersonalActivity.this);
		user = lstMessage.get(mApplication.getUserid());
		String account = "";
		String sex = "";
		String imageuri;
		account = user.getAccount();
		sex = user.getSex();
		imageuri = user.getPhoto();
		TV_account = (TextView) findViewById(R.id.TV_account);
		TV_account.setText(account);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		String sex1 = user.getSex();
		if (sex1.equals("")) {
			TV_sex.setText(sex);
		} else {
			TV_sex.setText(sex1);
		}
		String nname = user.getNickname();
		if(nname.equals("")){
			TV_nickname.setText("未设置");
		} else{
			TV_nickname.setText(nname);
		}
		imgChangePhoto = (CircularImage) findViewById(R.id.layout_imagetx);
		// 代码动态绑定R图片资源（默认头像）
		Bitmap bitmap = null;
		if (imageuri.equals("")) {
			bitmap = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.defaultphoto);
		} else {
			imgUri = Uri.parse(imageuri);
			try {
				bitmap = MediaStore.Images.Media.getBitmap(
						getContentResolver(), imgUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imgChangePhoto.setImageBitmap(bitmap);
		imgChangePhoto.setOnClickListener(new ViewOCL());
		imgBirthday = (ImageView) findViewById(R.id.img5);
		imgNickname = (ImageView) findViewById(R.id.img2);
		EX_button.setOnClickListener(new ViewOCL());
		imgNickname.setOnClickListener(new ViewOCL());
		imgBirthday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DIALOG_DATE);
			}
		});

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				TV_birthday.setText(msg.obj.toString());
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected Dialog onCreateDialog(int id) {

		Dialog dialog = null;
		switch (id) {
		case DIALOG_DATE:
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker datePicker, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(System.currentTimeMillis());
							calendar.set(Calendar.YEAR, 0);
							calendar.set(Calendar.MONTH, 0);
							calendar.set(Calendar.DAY_OF_MONTH, 0);
							message = new Message();
							// Toast.makeText(PersonalActivity.this,
							// ""+Calendar.YEAR, Toast.LENGTH_SHORT).show();
							message.obj = String.valueOf(year) + "-"
									+ String.valueOf(monthOfYear + 1) + "-"
									+ String.valueOf(dayOfMonth);// +cal.get(Calendar.MONTH)+cal.get(Calendar.DAY_OF_MONTH);
							message.what = 1;
							handler.sendMessage(message);
						}
					}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
							.get(Calendar.DAY_OF_MONTH));

			break;

		}
		return dialog;
	}

	private class ViewOCL implements View.OnClickListener {
		Dialog dialog = null;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.layout_imagetx:// 圆形头像按钮
				selectedSDImage();
				break;
			case R.id.img2:

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View view = inflater.inflate(
						R.layout.personal_editdialog, null);
				new AlertDialog.Builder(PersonalActivity.this)
						.setTitle("请设置昵称")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(view)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										EditText mEditText = (EditText) view
												.findViewById(R.id.ET_1);
										String newname = mEditText.getText()
												.toString();
										TV_nickname.setText(newname);
									}
								}).setNegativeButton("取消", null).show();
				// 性别选择的dialog，以及其上的控件
				// View viewSex = getLayoutInflater().inflate(R.layout., null);
				break;
			case R.id.ps_bt_logout:
				sharedPreferences = getSharedPreferences("condition",
						Context.MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				editor.putInt("userid", -1);
				editor.commit();
				Intent intent = new Intent();
				intent.setClass(PersonalActivity.this, LoginAcrivity.class);
				startActivity(intent);
				MainActivity.Maininstance.finish();
				finish();
				break;
			default:
				break;
			}

		}

	}

	/**
	 * 自定义一个方法读取SD卡的图片
	 * */
	private void selectedSDImage() {
		Intent intentAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		intentAlbum.setType("image/*");
		startActivityForResult(intentAlbum, 200); // 200：发送请求码（自定义）
	}

	/**
	 * 重写方法：获取选择后的图片信息并绑定到组件上
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// 判断是否正常接收到返回的数据
		if (resultCode != RESULT_OK) {
			Log.i(TAG, "获取返回结果失败");
			return;
		}

		// 准备解析图片使用的两个类
		// 接受解析对象
		ContentResolver contentResolver = getContentResolver(); // 对象属于应用程序，当前指令征用
		// 创建一个bitmap类型，接受处理后的图片数据
		Bitmap bitmap = null;

		Log.i(TAG, requestCode + "");

		// 解析返回的图片资源
		if (requestCode == 200) {
			// 获取图片资源的uri地址
			imgUri = data.getData();
			Log.i(TAG, imgUri + "");
			// Crel+1 自动添加try-catch
			try {
				// 使用MediaStore类的方法完成解析并将解析的结果存放到bitmap中
				bitmap = MediaStore.Images.Media.getBitmap(contentResolver,
						imgUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// bitmap绑定imageView组件，显示选中的图片
			this.imgChangePhoto.setImageBitmap(bitmap);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			// 销毁当前窗体
			finish();

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
