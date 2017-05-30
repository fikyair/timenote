package edu.ztone.timenote.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.timenote.R;

import edu.ztone.timenote.biz.DiariesBiz;
import edu.ztone.timenote.biz.impl.DiariesBizImpl;
import edu.ztone.timenote.login.LoginAcrivity;
import edu.ztone.timenote.login.RegisterActivity;
import edu.ztone.timenote.po.Diaries;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditNoteActivity extends Activity {
	private ActionBar diaryActionBar;
	final static String TAG = "test";
	private ImageView img1, img2, img3, img4;
	private ImageButton b, c, d, a;
	private TextView Datetext;
	private int i = 0;
	private String[] areas1 = new String[] { "小", "中", "大" };
	private String[] areas2 = new String[] { "红色", "绿色", "蓝色", "黑色" };
	private String[] areas3 = new String[] { "呆萌体","时光体", "系统默认" };
	private RadioOnClick OnClick;
	private RadioOnClick1 OnClick1;
	private RadioOnClick2 OnClick2;

	private static final int CAMERA_SUCCESS = 2;
	private String ddate;
	private String dweek;
	private DiariesBiz diariesBiz = null;
	private EditText editnote;
	private Intent eIntent;
	private int did = 0;
	private Spinner mSpinnerWeather;
	private List<Map<String, Object>> lstWeather;
	private Spinner mSpinnerMood;
	private List<Map<String, Object>> lstMood;
	private SharedPreferences sharedPreferences;
	Diaries diaries = new Diaries();
	String photo1, photo2, photo3, photo4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.editnote_layout);

		sharedPreferences = getSharedPreferences("condition", MODE_PRIVATE);
		this.c = (ImageButton) findViewById(R.id.editnote_txtsize);
		this.a = (ImageButton) findViewById(R.id.editnote_txtshapebtn);
		this.d = (ImageButton) findViewById(R.id.editnote_txtcolorbtn);
		this.diaryActionBar = getActionBar(); // 实例化ActionBar对象
		this.diaryActionBar.setDisplayHomeAsUpEnabled(true); // 设置返回箭头
		this.diaryActionBar.setDisplayShowHomeEnabled(false); // 取消箭头icon图标
		this.editnote = (EditText) findViewById(R.id.editnote_edittext);
		OnClick = new RadioOnClick(1);
		OnClick1 = new RadioOnClick1(3);
		OnClick2 = new RadioOnClick2(2);
		diaries.setFontcolor(3);
		diaries.setFontfamily(2);
		diaries.setFontsize(1);
		diaries.setPhoto1("");
		diaries.setPhoto2("");
		diaries.setPhoto3("");
		diaries.setPhoto4("");
		ddate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		dweek = getWeek();
		Datetext = (TextView) findViewById(R.id.editnote_activity_diary_date);
		Datetext.setText(ddate + dweek);

		this.mSpinnerWeather = (Spinner) findViewById(R.id.spiWeather);
		lstWeather = getListDataWeather();
		// 声明一个简单simpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, lstWeather,
				R.layout.weather, new String[] { "npic" },
				new int[] { R.id.imageview });
		// 动态加载数据步骤4：组件绑定适配器
		mSpinnerWeather.setAdapter(simpleAdapter);
		this.mSpinnerWeather
				.setOnItemSelectedListener(new WeatherOnItemSelectedListener());

		this.mSpinnerMood = (Spinner) findViewById(R.id.spiMood);
		lstMood = getListDataMood();
		// 声明一个简单simpleAdapter
		SimpleAdapter simpleAdapter1 = new SimpleAdapter(this, lstMood,
				R.layout.mood, new String[] { "npic" },
				new int[] { R.id.imageviewmood });
		// 动态加载数据步骤4：组件绑定适配器
		mSpinnerMood.setAdapter(simpleAdapter1);
		this.mSpinnerMood
				.setOnItemSelectedListener(new MoodOnItemSelectedListener());

		this.b = (ImageButton) findViewById(R.id.editnote_imgbtn);
		this.img1 = (ImageView) findViewById(R.id.editnote_img1);
		this.img2 = (ImageView) findViewById(R.id.editnote_img2);
		this.img3 = (ImageView) findViewById(R.id.editnote_img3);
		this.img4 = (ImageView) findViewById(R.id.editnote_img4);
		this.b.setOnClickListener(new ViewOCL2());
		this.img1.setOnClickListener(new ViewOCL2());
		this.img2.setOnClickListener(new ViewOCL2());
		this.img3.setOnClickListener(new ViewOCL2());
		this.img4.setOnClickListener(new ViewOCL2());
		img1.setOnLongClickListener(new LOCListener());
		img2.setOnLongClickListener(new LOCListener());
		img3.setOnLongClickListener(new LOCListener());
		img4.setOnLongClickListener(new LOCListener());
		c.setOnClickListener(new RadioClickListener());
		d.setOnClickListener(new RadioClickListener1());
		a.setOnClickListener(new RadioClickListener2());

		/*
		 * 在应用程序图标的左边显示一个向左的箭头，
		 * 
		 * 并且将HomeButtonEnabled设为true。
		 * 
		 * 默认为false。
		 */
		// 接收编辑日记的参数（id）
		eIntent = getIntent();
		if (eIntent.getExtras() != null) {
			int which = eIntent.getExtras().getInt("which");
			did = eIntent.getExtras().getInt("did");
			List<Diaries> lstMessage = GetDiariesList();
			editnote.setText(lstMessage.get(which).getDcontent());
			mSpinnerWeather.setSelection(lstMessage.get(which).getWeather());
			mSpinnerMood.setSelection(lstMessage.get(which).getMood());
			// 1
			Uri uri;
			photo1 = lstMessage.get(which).getPhoto1();
			if (!photo1.equals("")) {
				Log.i("TAG", "你大爷1" + photo1);
				uri = Uri.parse(photo1);
				Bitmap bitmap1 = changebitmap(uri);
				img1.setImageBitmap(bitmap1);
			} else {
				img1.setImageResource(R.drawable.add);
			}
			// 2
			photo2 = lstMessage.get(which).getPhoto2();
			if (!photo2.equals("")) {
				Log.i("TAG", "你大爷2");
				uri = Uri.parse(photo2);
				img2.setVisibility(1);
				Bitmap bitmap2 = changebitmap(uri);
				img2.setImageBitmap(bitmap2);
			} else {
				img2.setVisibility(View.VISIBLE);
				img2.setImageResource(R.drawable.add);
			}
			// 3
			photo3 = lstMessage.get(which).getPhoto3();
			if (!photo3.equals("")) {
				img3.setVisibility(1);
				uri = Uri.parse(photo3);
				Bitmap bitmap3 = changebitmap(uri);
				img3.setImageBitmap(bitmap3);
			} else {
				img3.setVisibility(View.VISIBLE);
				img3.setImageResource(R.drawable.add);
			}
			// 4
			photo4 = lstMessage.get(which).getPhoto4();
			if (!photo4.equals("")) {
				img4.setVisibility(1);
				uri = Uri.parse(photo4);
				Bitmap bitmap4 = changebitmap(uri);
				img4.setImageBitmap(bitmap4);
			} else {
				img4.setVisibility(View.VISIBLE);
				img4.setImageResource(R.drawable.add);
			}

			onClickfontfamily(lstMessage.get(which).getFontfamily());
			onClickfontcolor(lstMessage.get(which).getFontcolor());
			onClickfontsize(lstMessage.get(which).getFontsize());
		}
		diaryActionBar.setDisplayHomeAsUpEnabled(true);
		forceShowOverflowMenu();

	}
	private String getWeek() {
		String mWay;
		Calendar c = Calendar.getInstance();
		mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "天";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return "  星期" + mWay;
	}

	private Bitmap changebitmap(Uri uri) {
		ContentResolver contentResolver = getContentResolver();
		Bitmap bitmap = null;
		try {
			bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	private List<Diaries> GetDiariesList() {
		diariesBiz = new DiariesBizImpl();
		List<Diaries> Message = diariesBiz.find(EditNoteActivity.this);
		return Message;
	}

	// actionbar功能
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_diary_noteacbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void forceShowOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String notecontent = editnote.getText().toString();
		switch (item.getItemId()) {
		/*
		 * 将actionBar的HomeButtonEnabled设为ture，
		 * 
		 * 将会执行此case
		 */
		// 将日记存入数据库
		case R.id.finish:
			Editor editor = sharedPreferences.edit();
			editor.putInt("lock",-1);
			editor.commit();
			diariesBiz = new DiariesBizImpl();
			diaries.setDweek(dweek);
			diaries.setDdate(ddate);
			diaries.setDcontent(notecontent);

			String msg1;
			boolean flag;
			if (did == 0) {

				flag = diariesBiz.add(EditNoteActivity.this, diaries);

				msg1 = flag ? "添加成功" : "添加失败";
				Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_LONG)
						.show();

			} else {
				diaries.setPhoto1(photo1);
				diaries.setPhoto2(photo2);
				diaries.setPhoto3(photo3);
				diaries.setPhoto4(photo4);
				flag = diariesBiz.change(EditNoteActivity.this, diaries, did);
				msg1 = flag ? "修改成功" : "修改失败";
				Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_LONG)
						.show();
			}

			Intent intent = new Intent();
			intent.setClass(EditNoteActivity.this, MainActivity.class);
			startActivity(intent);
			MainActivity.Maininstance.finish();
			finish();

			break;
		case android.R.id.home:
			finish();
			break;
		case R.id.add:
			Toast.makeText(this, "对不起，功能尚未开发", Toast.LENGTH_LONG).show();
			break;
		// 其他省略...
		case R.id.share:
			Toast.makeText(this, "对不起，功能尚未开发", Toast.LENGTH_LONG).show();
			break;
	
			
		case R.id.set:
			intent = new Intent();
			intent.setClass(EditNoteActivity.this, SettingActivity.class);
			startActivity(intent);
			
			break;
		case R.id.help:
			    intent = new Intent(Intent.ACTION_DIAL);
			    intent.setData(Uri.parse("tel:" + "18510785896"));
			    startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		setOverflowIconVisible(featureId, menu);
		return super.onMenuOpened(featureId, menu);
	}

	/**
	 * 显示OverflowMenu的Icon
	 * 
	 * @param featureId
	 * @param menu
	 */
	private void setOverflowIconVisible(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					Log.d("OverflowIconVisible", e.getMessage());
				}
			}
		}
	}

	// -----------------------------------
	// 照相相册功能
	// 另一种字体模式
	// private class ViewOCL1 implements View.OnClickListener{
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// final CharSequence[] items = { "黑体", "楷体","隶书","宋体","圆体" };
	// AlertDialog dlg = new
	// AlertDialog.Builder(DiaryActivity1.this).setTitle("选择字体").setItems(items,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,int item) {
	// //这里item是根据选择的方式,
	// //在items数组里面定义了两种方式, 拍照的下标为1所以就调用拍照方法
	// switch(item){
	// case 0:
	// e.setTypeface(Typeface.DEFAULT_BOLD,Typeface.ITALIC);
	// e.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/heiti"));
	// break;
	// case 1:
	// e.setTypeface(Typeface.DEFAULT_BOLD,Typeface.ITALIC);
	// e.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/kaiti"));
	// break;
	// case 2:
	// e.setTypeface(Typeface.DEFAULT_BOLD,Typeface.ITALIC);
	// e.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/lishuti"));
	// break;
	// case 3:
	// e.setTypeface(Typeface.DEFAULT_BOLD,Typeface.ITALIC);
	// e.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/songti"));
	// break;
	// case 4:
	// e.setTypeface(Typeface.DEFAULT_BOLD,Typeface.ITALIC);
	// e.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/yuanti"));
	// break;
	// default: break;
	// }
	//
	//
	// }}).create();
	// dlg.show();
	//
	//
	// }
	// }
	private class ViewOCL2 implements View.OnClickListener {
		public void onClick(View v) {
			final CharSequence[] items = { "手机相册", "相机拍摄" };
			i = i + 1;
			AlertDialog dlg;
			switch (v.getId()) {
			case R.id.editnote_img1:

				dlg = new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选择图片")
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// 这里item是根据选择的方式,
								// 在items数组里面定义了两种方式, 拍照的下标为1所以就调用拍照方法
								if (item == 1) {
									i = 1;

									Intent getImageByCamera = new Intent(
											"android.media.action.IMAGE_CAPTURE");
									startActivityForResult(getImageByCamera,
											CAMERA_SUCCESS);
								} else {
									selectedSDImage1();
								}
							}
						}).create();
				dlg.show();
				break;
			case R.id.editnote_img2:

				dlg = new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选择图片")
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// 这里item是根据选择的方式,
								// 在items数组里面定义了两种方式, 拍照的下标为1所以就调用拍照方法
								if (item == 1) {
									i = 2;

									Intent getImageByCamera = new Intent(
											"android.media.action.IMAGE_CAPTURE");
									startActivityForResult(getImageByCamera,
											CAMERA_SUCCESS);
								} else {
									selectedSDImage2();
								}
							}
						}).create();
				dlg.show();
				break;
			case R.id.editnote_img3:

				dlg = new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选择图片")
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// 这里item是根据选择的方式,
								// 在items数组里面定义了两种方式, 拍照的下标为1所以就调用拍照方法
								if (item == 1) {
									i = 3;

									Intent getImageByCamera = new Intent(
											"android.media.action.IMAGE_CAPTURE");
									startActivityForResult(getImageByCamera,
											CAMERA_SUCCESS);
								} else {
									selectedSDImage3();
								}
							}
						}).create();
				dlg.show();
				break;
			case R.id.editnote_img4:

				dlg = new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选择图片")
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// 这里item是根据选择的方式,
								// 在items数组里面定义了两种方式, 拍照的下标为1所以就调用拍照方法
								if (item == 1) {
									i = 4;

									Intent getImageByCamera = new Intent(
											"android.media.action.IMAGE_CAPTURE");
									startActivityForResult(getImageByCamera,
											CAMERA_SUCCESS);
								} else {
									selectedSDImage4();
								}
							}
						}).create();
				dlg.show();
				break;
			default:

				dlg = new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选择图片")
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// 这里item是根据选择的方式,
								// 在items数组里面定义了两种方式, 拍照的下标为1所以就调用拍照方法
								if (item == 1) {
									Intent getImageByCamera = new Intent(
											"android.media.action.IMAGE_CAPTURE");
									startActivityForResult(getImageByCamera,
											CAMERA_SUCCESS);
								} else {

									switch (i) {
									case 1:

										selectedSDImage1();

										break;
									case 2:

										selectedSDImage2();
										break;

									case 3:

										selectedSDImage3();

										break;
									case 4:

										selectedSDImage4();
										break;

									default:
										break;
									}
								}
							}
						}).create();
				dlg.show();
				break;
			}
		}
	}

	/**
	 * 自定义一个方法读取SD卡的图片
	 * */
	private void selectedSDImage1() {
		Intent intentAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		intentAlbum.setType("image/*");
		startActivityForResult(intentAlbum, 200); // 200：发送请求码（自定义）
	}

	private void selectedSDImage2() {
		Intent intentAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		intentAlbum.setType("image/*");
		startActivityForResult(intentAlbum, 201); // 200：发送请求码（自定义）
	}

	private void selectedSDImage3() {
		Intent intentAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		intentAlbum.setType("image/*");
		startActivityForResult(intentAlbum, 202); // 200：发送请求码（自定义）
	}

	private void selectedSDImage4() {
		Intent intentAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		intentAlbum.setType("image/*");
		startActivityForResult(intentAlbum, 203); // 200：发送请求码（自定义）
	}

	/**
	 * 重写方法：获取选择后的图片信息并绑定到组件上
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bitmap = null;
		// 判断是否正常接收到返回的数据
		if (resultCode != RESULT_OK) {
			Log.i(TAG, "获取返回结果失败");
			return;
		}
		switch (requestCode) {
		case CAMERA_SUCCESS:
			Bundle extras = data.getExtras();
			bitmap = (Bitmap) extras.get("data");

			if (i == 1) {
				img2.setVisibility(1);
				this.img1.setImageBitmap(bitmap);
			}
			if (i == 2) {
				img3.setVisibility(1);
				this.img2.setImageBitmap(bitmap);
			}
			if (i == 3) {
				img4.setVisibility(1);
				this.img3.setImageBitmap(bitmap);
			}
			if (i == 4) {

				this.img4.setImageBitmap(bitmap);
			}
			break;
		default:
			// 准备解析图片使用的两个类
			// 接受解析对象
			ContentResolver contentResolver = getContentResolver(); // 对象属于应用程序，当前指令征用
			// 创建一个bitmap类型，接受处理后的图片数据

			Log.i(TAG, requestCode + "");

			// 解析返回的图片资源
			if (requestCode == 200) {
				// 获取图片资源的额uri地址
				Uri imgUri = data.getData();
				Log.i(TAG, imgUri + "");
				// Ctrl+1 自动添加try-catch
				try {
					// 使用MediaStore类的方法完成解析并将解析的结果存放到bitmap中
					bitmap = MediaStore.Images.Media.getBitmap(contentResolver,
							imgUri);
					photo1 = imgUri.toString();
					diaries.setPhoto1(photo1);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				img2.setVisibility(1);
				// bitmap绑定imageView组件，显示选中的图片
				this.img1.setImageBitmap(bitmap);
			}
			// 解析返回的图片资源
			if (requestCode == 201) {
				// 获取图片资源的额uri地址
				Uri imgUri = data.getData();
				Log.i(TAG, imgUri + "");
				// Ctrl+1 自动添加try-catch
				try {
					// 使用MediaStore类的方法完成解析并将解析的结果存放到bitmap中
					bitmap = MediaStore.Images.Media.getBitmap(contentResolver,
							imgUri);
					photo2 = imgUri.toString();
					diaries.setPhoto2(photo2);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// bitmap绑定imageView组件，显示选中的图片
				img3.setVisibility(1);
				this.img2.setImageBitmap(bitmap);

			}
			// 解析返回的图片资源
			if (requestCode == 202) {
				// 获取图片资源的额uri地址
				Uri imgUri = data.getData();
				Log.i(TAG, imgUri + "");
				// Ctrl+1 自动添加try-catch
				try {
					// 使用MediaStore类的方法完成解析并将解析的结果存放到bitmap中
					bitmap = MediaStore.Images.Media.getBitmap(contentResolver,
							imgUri);
					photo3 = imgUri.toString();
					diaries.setPhoto3(photo3);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				img4.setVisibility(1);
				// bitmap绑定imageView组件，显示选中的图片
				this.img3.setImageBitmap(bitmap);

			}
			// 解析返回的图片资源
			if (requestCode == 203) {
				// 获取图片资源的额uri地址
				Uri imgUri = data.getData();
				Log.i(TAG, imgUri + "");
				// Ctrl+1 自动添加try-catch
				try {
					// 使用MediaStore类的方法完成解析并将解析的结果存放到bitmap中
					bitmap = MediaStore.Images.Media.getBitmap(contentResolver,
							imgUri);
					photo4 = imgUri.toString();
					diaries.setPhoto4(photo4);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// bitmap绑定imageView组件，显示选中的图片
				Toast.makeText(getApplicationContext(), "最多可放四张",
						Toast.LENGTH_LONG).show();
				this.img4.setImageBitmap(bitmap);
			}
		}
	}

	private class LOCListener implements View.OnLongClickListener {

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.editnote_img1:
				new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选项")
						.setItems(new String[] { "删除" },
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											// 通过position下标获取用户点中的选项对象item(Map)
											img1.setImageResource(R.drawable.add);
											break;

										default:
											break;
										}
									}
								}).setNegativeButton(null, null).show();
				i = 0;
				break;
			case R.id.editnote_img2:
				new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选项")
						.setItems(new String[] { "删除" },
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											// 通过position下标获取用户点中的选项对象item(Map)
											img2.setImageResource(R.drawable.add);
											break;

										default:
											break;
										}
									}
								}).setNegativeButton(null, null).show();
				i = 1;
				break;
			case R.id.editnote_img3:
				new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选项")
						.setItems(new String[] { "删除" },
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											// 通过position下标获取用户点中的选项对象item(Map)
											img3.setImageResource(R.drawable.add);
											break;

										default:
											break;
										}
									}
								}).setNegativeButton(null, null).show();
				i = 2;
				break;
			case R.id.editnote_img4:
				new AlertDialog.Builder(EditNoteActivity.this)
						.setTitle("选项")
						.setItems(new String[] { "删除" },
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											// 通过position下标获取用户点中的选项对象item(Map)
											img4.setImageResource(R.drawable.add);
											break;

										default:
											break;
										}
									}
								}).setNegativeButton(null, null).show();
				i = 3;
				break;

			default:
				break;
			}
			return false;
		}

	}

	class RadioClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			AlertDialog ad = new AlertDialog.Builder(EditNoteActivity.this)
					.setTitle("字体大小")
					.setSingleChoiceItems(areas1, OnClick.getIndex(), OnClick)
					.create();
			ad.show();
		}
	}

	class RadioOnClick implements DialogInterface.OnClickListener {
		private int index;

		public RadioOnClick(int index) {
			this.index = index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void onClick(DialogInterface dialog, int whichButton) {
			setIndex(whichButton);
			switch (index) {
			case 0:
				editnote.setTextSize(15);
				diaries.setFontsize(0);

				break;
			case 1:
				editnote.setTextSize(22);
				diaries.setFontsize(1);

				break;
			case 2:
				editnote.setTextSize(35);
				diaries.setFontsize(2);
				break;
			default:
				break;
			}
			dialog.dismiss();
		}
	}

	// 查看日记时显示设置好的字体大小
	private void onClickfontsize(int which) {

		switch (which) {
		case 0:
			editnote.setTextSize(15);
	
			diaries.setFontsize(0);
			

			break;
		case 1:
			editnote.setTextSize(22);
			diaries.setFontsize(1);
		
			break;
		case 2:
			editnote.setTextSize(35);
			diaries.setFontsize(2);
	
			break;
		default:
			break;
		}
	}

	class RadioClickListener1 implements OnClickListener {
		@Override
		public void onClick(View v) {
			AlertDialog cd = new AlertDialog.Builder(EditNoteActivity.this)
					.setTitle("字体颜色")
					.setSingleChoiceItems(areas2, OnClick1.getIndex(), OnClick1)
					.create();
			cd.show();

		}
	}

	class RadioOnClick1 implements DialogInterface.OnClickListener {
		private int index;

		public RadioOnClick1(int index) {
			this.index = index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			setIndex(which);
			// TODO Auto-generated method stub
			switch (index) {
			case 0:
				editnote.setTextColor(Color.rgb(255, 0, 0));
				diaries.setFontcolor(0);

				break;
			case 1:
				editnote.setTextColor(Color.rgb(0, 255, 0));
				diaries.setFontcolor(1);

				break;
			case 2:
				editnote.setTextColor(Color.rgb(0, 0, 255));
				diaries.setFontcolor(2);
				break;
			case 3:
				editnote.setTextColor(Color.rgb(0, 0, 0));
				diaries.setFontcolor(3);
				break;
			default:	
				break;
			}
			dialog.dismiss();
		}
	}

	// 查看日记时显示设置好的字体颜色
	private void onClickfontcolor(int which) {
		switch (which) {
		case 0:
			editnote.setTextColor(Color.rgb(255, 0, 0));
			diaries.setFontcolor(0);

			break;
		case 1:
			editnote.setTextColor(Color.rgb(0, 255, 0));
			diaries.setFontcolor(1);

			break;
		case 2:
			editnote.setTextColor(Color.rgb(0, 0, 255));
			diaries.setFontcolor(2);
			break;
		case 3:
			editnote.setTextColor(Color.rgb(0, 0, 0));
			diaries.setFontcolor(3);
			break;
		default:	
			break;
		}
	}

	class RadioClickListener2 implements OnClickListener {
		@Override
		public void onClick(View v) {
			AlertDialog cd = new AlertDialog.Builder(EditNoteActivity.this)
					.setTitle("选择字体")
					.setSingleChoiceItems(areas3, OnClick2.getIndex(), OnClick2)
					.create();
			cd.show();

		}
	}

	class RadioOnClick2 implements DialogInterface.OnClickListener {
		private int index;

		public RadioOnClick2(int index) {
			this.index = index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			setIndex(which);
			// TODO Auto-generated method stub
			switch (index) {
			

			case 0:
				
				editnote.setTypeface(Typeface.createFromAsset(getAssets(),
						"fonts/manhua.ttf"));
				diaries.setFontfamily(0);
				break;

			case 1:
				
				editnote.setTypeface(Typeface.createFromAsset(getAssets(),
						"fonts/lixuke.ttf"));
				diaries.setFontfamily(1);
				break;
			
			
				
			case 2:
				editnote.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL);
				diaries.setFontfamily(2);
break;
			default:
				break;
			}
			dialog.dismiss();
		}
	}

	// 查看日记时显示设置好的字体颜色
	private void onClickfontfamily(int which) {
		switch (which) {
		case 0:
			
			editnote.setTypeface(Typeface.createFromAsset(getAssets(),
					"fonts/manhua.ttf"));
			diaries.setFontfamily(0);
			break;

		case 1:
			
			editnote.setTypeface(Typeface.createFromAsset(getAssets(),
					"fonts/lixuke.ttf"));
			diaries.setFontfamily(1);
			break;

		
		case 2:
			editnote.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL);
			diaries.setFontfamily(2);
break;
		default:editnote.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL);
		diaries.setFontfamily(2);
			break;
		}
	}

	public List<Map<String, Object>> getListDataWeather() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 每个Map结构为一条数据，key与Adapter中定义的String数组中定义的一一对应。

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("npic", R.drawable.sunny);
		list.add(map);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("npic", R.drawable.sunnytocloudy);
		list.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("npic", R.drawable.cloudy);
		list.add(map3);

		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("npic", R.drawable.rainny);
		list.add(map4);

		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("npic", R.drawable.thunder);
		list.add(map5);

		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("npic", R.drawable.snow);
		list.add(map6);

		return list;
	}

	public List<Map<String, Object>> getListDataMood() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 每个Map结构为一条数据，key与Adapter中定义的String数组中定义的一一对应。

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("npic", R.drawable.happy3);
		list.add(map);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("npic", R.drawable.happy2);
		list.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("npic", R.drawable.happy1);
		list.add(map3);

		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("npic", R.drawable.normal);
		list.add(map4);

		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("npic", R.drawable.sad1);
		list.add(map5);

		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("npic", R.drawable.sad2);
		list.add(map6);

		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("npic", R.drawable.sad3);
		list.add(map7);

		HashMap<String, Object> map8 = new HashMap<String, Object>();
		map8.put("npic", R.drawable.angry);
		list.add(map8);

		return list;
	}

	private class WeatherOnItemSelectedListener implements
			AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			diaries.setWeather(position);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	private class MoodOnItemSelectedListener implements
			AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			diaries.setMood(position);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}
}
