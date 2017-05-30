package edu.ztone.timenote.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.timenote.R;

import edu.ztone.timenote.adapter.BorseseDiarysAdapter;
import edu.ztone.timenote.adapter.LiftListAdapter;
import edu.ztone.timenote.biz.DiariesBiz;
import edu.ztone.timenote.biz.UserBiz;
import edu.ztone.timenote.biz.impl.DiariesBizImpl;
import edu.ztone.timenote.biz.impl.UserBizImpl;
import edu.ztone.timenote.lock.UnlockGesturePasswordActivity;
import edu.ztone.timenote.po.Diaries;
import edu.ztone.timenote.po.User;
import edu.ztone.timenote.util.AnimButtons;
import edu.ztone.timenote.util.KCalendar;
import edu.ztone.timenote.util.MyApplication;
import edu.ztone.timenote.util.AnimButtons.OnButtonClickListener;
import edu.ztone.timenote.util.KCalendar.OnCalendarClickListener;
import edu.ztone.timenote.util.KCalendar.OnCalendarDateChangedListener;
import edu.ztone.timenote.util.MainActivityLiftListModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static MainActivity Maininstance = null;
	private static final String SETTINGS = "user_configurations";
	private DrawerLayout mDrawerLayout;
	private RelativeLayout mLeftRelativeLayout;
	private RelativeLayout mMainRelativeLayout;
	private ListView mListViews;
	private ListView mListViewBorese;
	private edu.ztone.timenote.util.CircularImage mCircularImage2;
	private AnimButtons mAnimButtons;
	private Button mSetButton;
	private Button mCalendButton;
	private TextView mCalendnotetext;
	private LinearLayout mCalendnote;
	private TextView mUsername;
	private View mLine1;
	private View mLine2;
	private String mCdate = null;
	private MyApplication mMyApplication;
	private int style = 1;
	private int count = 0;
	private int uid;
	private final int DIALOG_TIME = 0;
	private SharedPreferences sharedPreferences;
	private BorseseDiarysAdapter mBorseseDiarysAdapter = null;
	private LiftListAdapter mListAdapter = null;
	private List<MainActivityLiftListModel> list = null;
	private List<Map<String, Object>> mBoreseList = null;
	private RelativeLayout relativeLayout;
    private long exitTime=0;
	private List<Diaries> lstMessage = null;
	private List<User> lstUser = null;
	private Intent mIntent = null;
	private AlarmManager mAlarmManager;
	private PendingIntent mPi;

	private DiariesBiz diariesBiz = null;
	private UserBiz userBiz = null;

	// 日历下的控件：
	private TextView Style2_did;
	private TextView Style2_ddate;
	private ImageView Style2_dweather;
	private ImageView Style2_dmood;
	private TextView Style2_dcontent;
	private TextView Style2_dfontsize;
	private TextView Style2_dfontcolor;
	private TextView Style2_dfontfamily;
	private TextView Style2_dchange;
	private TextView Style2_ddelete;
	private ImageView Style2_dimg1;
	private ImageView Style2_dimg2;
	private ImageView Style2_dimg3;
	private ImageView Style2_dimg4;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		Maininstance = this;
		mMyApplication = (MyApplication) getApplication();
		sharedPreferences = getSharedPreferences("condition",
				Context.MODE_PRIVATE);
		//判断是否添加图形锁的flag
				if (sharedPreferences.getInt("lock", -1) == 1){
					mIntent = new Intent();
					mIntent.setClass(MainActivity.this, UnlockGesturePasswordActivity.class);
					startActivity(mIntent);
				}

		mUsername = (TextView) findViewById(R.id.main_lilayout_text_name);
		// 设置用户信息（头像，名字）
		uid = sharedPreferences.getInt("userid", -1);
		Log.i("text", "3" + uid);
		userBiz = new UserBizImpl();
		lstUser = userBiz.find(MainActivity.this);
		mUsername.setText(lstUser.get(uid).getAccount());
		String imguri = "";
		imguri = lstUser.get(uid).getPhoto();
		Bitmap bitmaptx = null;
		mCircularImage2 = (edu.ztone.timenote.util.CircularImage) findViewById(R.id.main_lilayout_image_tx);
		if (imguri.equals("")) {
			bitmaptx = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.dousen);
			mCircularImage2.setImageBitmap(bitmaptx);
		} else {
			Uri imgUri = Uri.parse(imguri);
			Log.i("text", imguri);
			ContentResolver contentEesolver = MainActivity.this
					.getContentResolver();
			Log.i("text", "2111");
			try {
				Log.i("text", "3111");
				// 使用MediaStore类的方法完成解析并将解析的结果存放到bitmap中
				bitmaptx = MediaStore.Images.Media.getBitmap(contentEesolver,
						imgUri);
				Log.i("text", "3111");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mCircularImage2.setImageBitmap(bitmaptx);

		mListViews = (ListView) findViewById(R.id.left_listview);
		SetLeftList();
		mListViews.setOnItemClickListener(new MyListViewOCL());
		mMainRelativeLayout = (RelativeLayout) findViewById(R.id.main_layout_mainrelayout);
		mCalendButton = (Button) findViewById(R.id.main_relayout_calendbtn);
		mListViewBorese = (ListView) findViewById(R.id.main_relayout_boresediarys);
		mLeftRelativeLayout = (RelativeLayout) findViewById(R.id.main_leftlayout);
		mLeftRelativeLayout.setBackgroundResource(R.drawable.leftbackground1);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
		mSetButton = (Button) findViewById(R.id.main_relayout_setbtn);
		mSetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}
		});

		mCalendnote = (LinearLayout) findViewById(R.id.main_relayout_calendnote);

		if (style == 1) {

			mListViewBorese.setVisibility(View.VISIBLE);
			// 初始化集合数据
			try {
				mBoreseList = SetBoreseList();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 集合装载适配器
			mBorseseDiarysAdapter = new BorseseDiarysAdapter(MainActivity.this,
					mBoreseList);
			// 列表组件绑定适配器
			mListViewBorese.setAdapter(mBorseseDiarysAdapter);
			// 列表组件绑定单击监听器
			this.mListViewBorese.setOnItemClickListener(new AdapterViewOCL());
			// 列表组将绑定长点击监听器
			this.mListViewBorese
					.setOnItemLongClickListener(new AdapterViewLongOCL());
		}

		mAnimButtons = (AnimButtons) findViewById(R.id.main_animButtons);
		mAnimButtons.setOnButtonClickListener(new MyOnButtonClickListener());

		// 日历视图下的控件：
		Style2_did = (TextView) findViewById(R.id.mainlayout_style2_did);
		Style2_ddate = (TextView) findViewById(R.id.mainlayout_style2_ddate);
		Style2_dweather = (ImageView) findViewById(R.id.mainlayout_style2_weather);
		Style2_dmood = (ImageView) findViewById(R.id.mainlayout_style2_mood);
		Style2_dcontent = (TextView) findViewById(R.id.mainlayout_style2_content);
		Style2_dfontsize = (TextView) findViewById(R.id.mainlayout_style2_fontsize);
		Style2_dfontcolor = (TextView) findViewById(R.id.mainlayout_style2_fontcolor);
		Style2_dfontfamily = (TextView) findViewById(R.id.mainlayout_style2_fontfamily);
		Style2_dimg1 = (ImageView) findViewById(R.id.mainlayout_style2_image1);
		Style2_dimg2 = (ImageView) findViewById(R.id.mainlayout_style2_image2);
		Style2_dimg3 = (ImageView) findViewById(R.id.mainlayout_style2_image3);
		Style2_dimg4 = (ImageView) findViewById(R.id.mainlayout_style2_image4);
		// Style2_dchange = (TextView)
		// findViewById(R.id.mainlayout_style2_change);
		// Style2_ddelete = (TextView)
		// findViewById(R.id.mainlayout_style2_delete);

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Editor editor = sharedPreferences.edit();
		editor.putInt("lock", 1);
		editor.commit();
	}


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Diaries diaries_now = (Diaries) msg.obj;
				SetStyle2View(diaries_now);
				break;
			case 2:
				Log.i("text", "color : " + msg.obj);
				mLeftRelativeLayout.setBackgroundResource((Integer) msg.obj);
			case 3:
				Log.i("text", "color : " + msg.obj);
				break;
			default:
				break;
			}
		}
	};

	// 设置日历视图：
	private void SetStyle2View(Diaries diaries) {
		Style2_ddate.setText(diaries.getDdate() + diaries.getDweek());

		Style2_dcontent.setText("  " + diaries.getDcontent());
		 switch(diaries.getFontfamily()){
		  case 0:
				Style2_dfontsize.setText("字体样式：" + "呆萌体"+ "   " );break;
			case 1:
				Style2_dfontsize.setText("字体样式：" + "时光体" + "   ");break;
			case 2:
				Style2_dfontsize.setText("字体样式：" + "系统默认"+ "   ");break;
				default:break;
		  }
		switch( diaries.getFontsize()){
		case 0:
		Style2_dfontfamily.setText("字体大小：" + "小");
		break;
		case 1:
			Style2_dfontfamily.setText("字体大小：" + "中");
			break;
		case 2:
			Style2_dfontfamily.setText("字体大小：" + "大");
			break;
		
		}
		switch (diaries.getFontcolor()) {
		case 0:
			Style2_dfontcolor.setText("文字颜色：" + "红色"+"   ");
			break;
		case 1:
			Style2_dfontcolor.setText("文字颜色：" + "绿色"+"   ");
			break;
		case 2:
			Style2_dfontcolor.setText("文字颜色：" + "蓝色"+"   ");
			break;
		case 3:
			Style2_dfontcolor.setText("文字颜色：" + "黑色"+"   ");
			break;
		default:
			break;
		}  
			  
		 
	
		
		int dMood = diaries.getMood();
		int dWeather = diaries.getWeather();
		switch (dMood) {
		case 0:
			Style2_dmood.setBackgroundResource(R.drawable.happy3);
			break;
		case 1:
			Style2_dmood.setBackgroundResource(R.drawable.happy2);
			break;
		case 2:
			Style2_dmood.setBackgroundResource(R.drawable.happy1);
			break;
		case 3:
			Style2_dmood.setBackgroundResource(R.drawable.normal);
			break;
		case 4:
			Style2_dmood.setBackgroundResource(R.drawable.sad1);
			break;
		case 5:
			Style2_dmood.setBackgroundResource(R.drawable.sad2);
			break;
		case 6:
			Style2_dmood.setBackgroundResource(R.drawable.sad3);
			break;
		case 7:
			Style2_dmood.setBackgroundResource(R.drawable.angry);
			break;
		default:
			break;
		}
		switch (dWeather) {
		case 0:
			
			Style2_dweather.setBackgroundResource(R.drawable.sunny);
			break;
		case 1:
			Style2_dweather.setBackgroundResource(R.drawable.sunnytocloudy);
			break;
		case 2:
			Style2_dweather.setBackgroundResource(R.drawable.cloudy);
			break;
		case 3:
			Style2_dweather.setBackgroundResource(R.drawable.rainny);
			break;
		case 4:
			Style2_dweather.setBackgroundResource(R.drawable.thunder);
			break;
		case 5:
			Style2_dweather.setBackgroundResource(R.drawable.snow);
			break;
		default:
			break;
		}
		Uri uri;
		String photo1 = diaries.getPhoto1();
		if (!photo1.equals("")) {
			
			uri = Uri.parse(photo1);
			Bitmap bitmap1 = changebitmap(uri);
			Style2_dimg1.setImageBitmap(bitmap1);
		} else {
			Style2_dimg1.setImageBitmap(null);
		}
		// 2
		String photo2 = diaries.getPhoto2();
		if (!photo2.equals("")) {
		
			uri = Uri.parse(photo2);
			Bitmap bitmap2 = changebitmap(uri);
			Style2_dimg2.setImageBitmap(bitmap2);
		} else {
			Style2_dimg2.setImageBitmap(null);
		}
		// 3
		String photo3 = diaries.getPhoto3();
		if (!photo3.equals("")) {
			uri = Uri.parse(photo3);
			Bitmap bitmap3 = changebitmap(uri);
			Style2_dimg3.setImageBitmap(bitmap3);
		} else {
			Style2_dimg3.setImageBitmap(null);
		}
		// 4
		String photo4 = diaries.getPhoto4();
		if (!photo4.equals("")) {
			uri = Uri.parse(photo4);
			Bitmap bitmap4 = changebitmap(uri);
			Style2_dimg4.setImageBitmap(bitmap4);
		} else {
			Style2_dimg4.setImageBitmap(null);
		}
	}

	private Bitmap changebitmap(Uri uri) {
		ContentResolver contentResolver = MainActivity.this
				.getContentResolver();
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

	// 日历视图
	public class PopupWindows extends PopupWindow {

		@SuppressWarnings("deprecation")
		public PopupWindows(Context mContext, View parent) {

			View view = View.inflate(mContext, R.layout.calend_layout, null);
			mCalendnotetext = (TextView) view
					.findViewById(R.id.calend_layout_note);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_in));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.calend_linearlayout1);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_1));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			final TextView calendar_month = (TextView) view
					.findViewById(R.id.calendar_month);
			final KCalendar calendar = (KCalendar) view
					.findViewById(R.id.calendar_layout);
			Button calendar_bt_enter = (Button) view
					.findViewById(R.id.calendar_bt_enter);

			calendar_month.setText(calendar.getCalendarYear() + "年"
					+ calendar.getCalendarMonth() + "月");

			if (null != mCdate) {

				int years = Integer.parseInt(mCdate.substring(0,
						mCdate.indexOf("-")));
				int month = Integer.parseInt(mCdate.substring(
						mCdate.indexOf("-") + 1, mCdate.lastIndexOf("-")));
				calendar_month.setText(years + "年" + month + "月");

				calendar.showCalendar(years, month);
				calendar.setCalendarDayBgColor(mCdate,
						R.drawable.calendar_date_focused);
			}

			List<String> list = new ArrayList<String>(); // 设置标记列表
			list.add("2014-04-01");
			list.add("2014-04-02");
			calendar.addMarks(list, 0);

			// 监听所选中的日期
			calendar.setOnCalendarClickListener(new OnCalendarClickListener() {

				public void onCalendarClick(int row, int col, String dateFormat) {
					int month = Integer.parseInt(dateFormat.substring(
							dateFormat.indexOf("-") + 1,
							dateFormat.lastIndexOf("-")));

					if (calendar.getCalendarMonth() - month == 1// 跨年跳转
							|| calendar.getCalendarMonth() - month == -11) {
						calendar.lastMonth();

					} else if (month - calendar.getCalendarMonth() == 1 // 跨年跳转
							|| month - calendar.getCalendarMonth() == -11) {
						calendar.nextMonth();

					} else {
						calendar.removeAllBgColor();
						calendar.setCalendarDayBgColor(dateFormat,
								R.drawable.calendar_date_focused);

						mCdate = dateFormat;// 最后返回给全局 date
						lstMessage = GetDiariesList();
						for (Diaries diaries : lstMessage) {
							String ddate = diaries.getDdate();
							if (ddate.equalsIgnoreCase(mCdate)) {
								Log.i("text", "$$$");
								String content = diaries.getDcontent();
								mCalendnotetext.setText(content);
								Message msg = Message.obtain();
								msg.obj = diaries;
								msg.what = 1;
								handler.sendMessage(msg);
								break;
							}
						}
						Toast.makeText(MainActivity.this, "date :" + mCdate,
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			// 监听当前月份
			calendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
				public void onCalendarDateChanged(int year, int month) {
					calendar_month.setText(year + "年" + month + "月");
				}
			});

			// 上月监听按钮
			RelativeLayout popupwindow_calendar_last_month = (RelativeLayout) view
					.findViewById(R.id.calendar_last_month);
			popupwindow_calendar_last_month
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							calendar.lastMonth();
						}

					});

			// 下月监听按钮
			RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) view
					.findViewById(R.id.calendar_next_month);
			popupwindow_calendar_next_month
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							calendar.nextMonth();
						}
					});

			// 关闭窗口
			calendar_bt_enter.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					dismiss();
				}
			});
		}
	}

	// 侧拉栏列表
	private void SetLeftList() {

		list = new ArrayList<MainActivityLiftListModel>();

		list.add(new MainActivityLiftListModel(R.drawable.left_user1, "我的页面", 1));
		list.add(new MainActivityLiftListModel(R.drawable.left_clock1, "日记提醒",
				2));
		list.add(new MainActivityLiftListModel(R.drawable.left_style1, "主题切换",
				3));
		list.add(new MainActivityLiftListModel(R.drawable.left_watch1, "视图切换",
				4));
		list.add(new MainActivityLiftListModel(R.drawable.left_lock1, "密码锁", 5));
		list.add(new MainActivityLiftListModel(R.drawable.left_setting1, "设置",
				6));
		mListAdapter = new LiftListAdapter(MainActivity.this, list);
		mListViews.setAdapter(mListAdapter);
	}

	// 获取Diaries数据
	private List<Diaries> GetDiariesList() {
		diariesBiz = new DiariesBizImpl();
		List<Diaries> Message = diariesBiz.find(MainActivity.this);
		return Message;
	}

	// 为每个item导入数据
	private List<Map<String, Object>> SetBoreseList() throws FileNotFoundException, IOException {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		lstMessage = GetDiariesList();
		for (Diaries diaries : lstMessage) {
			mMainRelativeLayout.setBackground(null);
			String ddate = diaries.getDdate();
			String dcontent = diaries.getDcontent();

			// 从数据库中取出天气图标
			int dweather = diaries.getWeather();
			int dmood = diaries.getMood();

			String photo1 = diaries.getPhoto1();
			Bitmap bitmapphoto1 = null;
			if (photo1.equals("")) {
			} else {
				bitmapphoto1 = MediaStore.Images.Media.getBitmap(
						getContentResolver(), Uri.parse(photo1));
				bitmapphoto1 = ThumbnailUtils.extractThumbnail(bitmapphoto1,
						72, 72);
			}
			String dweek = diaries.getDweek();

			Map<String, Object> item = new HashMap<String, Object>();
			item.put("week", dweek);
			item.put("ddate", ddate);
			item.put("dcontent", dcontent);
			item.put("did", diaries.getDid());
			item.put("weather", dweather);
			item.put("mood", dmood);
			item.put("image1", bitmapphoto1);

			items.add(item);

		}
		return items;
	}

	// 十字按钮监听事件
	private class MyOnButtonClickListener implements OnButtonClickListener {

		@Override
		public void onButtonClick(View v, int id) {
			mIntent = new Intent();
			switch (v.getId()) {
			case R.id.main_relayout_btn_chat:
				
				mIntent.setClass(MainActivity.this, EditNoteActivity.class);
				startActivity(mIntent);

				break;
			case R.id.main_relayout_btn_camera:
				Intent intent = new Intent();
				intent.setAction("android.media.action.IMAGE_CAPTURE");
				intent.addCategory("android.intent.category.DEFAULT");
				  File file = new File(Environment.getExternalStorageDirectory()+"/000.jpg");  
				  Uri uri = Uri.fromFile(file); 
				  intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivity(intent);break;
			case R.id.main_relayout_btn_video:
				intent = new Intent();
				intent.setAction("android.media.action.VIDEO_CAPTURE");
				intent.addCategory("android.intent.category.DEFAULT");
				 file = new File(Environment.getExternalStorageDirectory()+"/002.jpg");  
				   uri = Uri.fromFile(file); 
				  intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivity(intent);break;

			default:
				break;
			}
			mAnimButtons.closeMenu();
		}
	}


	// 侧拉栏按钮监听事件
	private class MyListViewOCL implements AdapterView.OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			mIntent = new Intent();
			switch (arg2) {
			case 0:

				mIntent.setClass(MainActivity.this, PersonalActivity.class);
				startActivity(mIntent);
				break;
			case 1:
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setIcon(R.drawable.clock);
				builder.setTitle("闹钟设置");
				final String[] clock = { "开启闹钟", "关闭闹钟" };
				builder.setItems(clock, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

						mIntent = new Intent(MainActivity.this,
								ClockActivity.class);

						mPi = PendingIntent.getActivity(MainActivity.this, 0,
								mIntent, 0);

						switch (which) {
						case 0:
							Calendar currentTime = Calendar.getInstance();
							Log.i("text", "1");
							// 弹出一个时间设置的对话框,供用户选择时间
							new TimePickerDialog(
									MainActivity.this,
									0,
									new OnTimeSetListener() {
										public void onTimeSet(TimePicker view,
												int hourOfDay, int minute) {
											// 设置当前时间
											Calendar c = Calendar.getInstance();
											Log.i("text", "2");
											c.setTimeInMillis(System
													.currentTimeMillis());
											Log.i("text", "3");
											// 根据用户选择的时间来设置Calendar对象
											c.set(Calendar.HOUR, hourOfDay);
											c.set(Calendar.MINUTE, minute);
											// ②设置AlarmManager在Calendar对应的时间启动Activity
											mAlarmManager.set(
													AlarmManager.RTC_WAKEUP,
													c.getTimeInMillis(), mPi);
											Log.i("text", "4");
											// 提示闹钟设置完毕:
											Toast.makeText(MainActivity.this,
													"闹钟设置完毕~",
													Toast.LENGTH_SHORT).show();
										}
									}, currentTime.get(Calendar.HOUR_OF_DAY),
									currentTime.get(Calendar.MINUTE), false)
									.show();
							break;
						case 1:
							mAlarmManager.cancel(mPi);
							Toast.makeText(MainActivity.this, "闹钟已取消",
									Toast.LENGTH_SHORT).show();
							break;

						default:
							break;
						}
					}

				});
				builder.show();
				break;
			case 2:
				mMyApplication.setHandler(handler);
				final DiaLogColorBtn diaLog = new DiaLogColorBtn(
						MainActivity.this);
				diaLog.setTitle("色彩斑斓");
				diaLog.show();

				break;
			case 3:
				if (style == 1) {
					style = 2;
					mListViewBorese.setVisibility(View.GONE);
					mCalendButton.setVisibility(View.VISIBLE);
					mCalendnote.setVisibility(View.VISIBLE);
					mCalendButton.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							new PopupWindows(MainActivity.this, mCalendButton);
						}
					});
				} else {
					style = 1;
					mCalendButton.setVisibility(View.GONE);
					mCalendnote.setVisibility(View.GONE);
					mListViewBorese.setVisibility(View.VISIBLE);
					try {
						mBoreseList = SetBoreseList();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mBorseseDiarysAdapter = new BorseseDiarysAdapter(
							MainActivity.this, mBoreseList);
					mListViewBorese.setAdapter(mBorseseDiarysAdapter);

					mListViewBorese
							.setOnItemClickListener(new AdapterViewOCL());
					mListViewBorese
							.setOnItemLongClickListener(new AdapterViewLongOCL());
				}

				break;
			case 4:
				mIntent = new Intent();
				mIntent.setClass(MainActivity.this, UnlockGesturePasswordActivity.class);
				startActivity(mIntent);
				break;
			case 5:
				mIntent.setClass(MainActivity.this, SettingActivity.class);
				startActivity(mIntent);
				break;

			default:
				break;
			}

		}

	}

	/**
	 * 视图浏览列表单击事件类
	 * */
	private class AdapterViewOCL implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			// 通过position下标获取用户点中的选项对象item(Map)

			int did = Integer.valueOf(
					mBoreseList.get(position).get("did").toString()).intValue();

			mIntent = new Intent();
			mIntent.putExtra("which", position);
			mIntent.putExtra("did", did);
			mIntent.setClass(MainActivity.this, EditNoteActivity.class);
			startActivity(mIntent);

		}

	}

	/**
	 * 自定义一个列表长点击事件监听器类
	 * */
	private class AdapterViewLongOCL implements
			AdapterView.OnItemLongClickListener {
		int did;

		@Override
		public boolean onItemLongClick(AdapterView<?> adapter, View view,
				final int position, long id) {
			// TODO Auto-generated method stub
			diariesBiz = new DiariesBizImpl();
			did = Integer.valueOf(
					mBoreseList.get(position)
							.get("did").toString())
					.intValue();
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("选项")
					.setItems(new String[] { "分享", "删除" },
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {
									case 0:
										 String notetext = mBoreseList.get(position)
											.get("dcontent").toString();
										 showShareDialog(notetext); //调用分享方法   

										break;
									case 1:
										// 通过position下标获取用户点中的选项对象item(Map)
										 Dialog a=   new AlertDialog.Builder(MainActivity.this).setTitle("确认删除吗？") 
								            .setIcon(android.R.drawable.ic_dialog_info) 
								            .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
								         
								                @Override 
								                public void onClick(DialogInterface dialog, int which) { 
								                // 退出事件
								                	
													diariesBiz.drop(MainActivity.this, did);
													// 刷新列表
													try {
														mBoreseList = SetBoreseList();
													} catch (FileNotFoundException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (IOException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													mBorseseDiarysAdapter = new BorseseDiarysAdapter(
															MainActivity.this, mBoreseList);
													mListViewBorese
															.setAdapter(mBorseseDiarysAdapter);

								                } 
								            }) 
								            .setNegativeButton("返回", new DialogInterface.OnClickListener() { 
								         
								                @Override 
								                public void onClick(DialogInterface dialog, int which) { 
								                // 点击“返回”后的操作,这里不设置没有任何操作 
								                	dialog.dismiss();
								                } 
								            }).show(); 
										
										break;

									default:
										break;
									}
								}
							}).setNegativeButton(null, null).show();
			return true;
		}

	}
	
	 //点击分享按钮   
    private   void  showShareDialog(final String notetext){  
     final  AlertDialog.Builder builder =  new  AlertDialog.Builder( this );    
          builder.setTitle( "选择分享类型" );    
          builder.setItems( new  String[]{ "邮件" , "短信" },  
                   new  DialogInterface.OnClickListener() {    
                       public   void  onClick(DialogInterface dialog,  int  which) {    
                          dialog.dismiss();    
                           switch  (which) {    
                           case   0 :{    
                              sendMail(notetext);  
                               break ;      
                          }    
                           case   1 :    
                              sendSMS(notetext);  
                               break ;    
                          }    
                      }    
                  });    
           builder.setNegativeButton( "取消" ,  new  DialogInterface.OnClickListener() {    
               @Override     
               public   void  onClick(DialogInterface dialog,  int  which) {    
                  dialog.dismiss();    
              }    
          });    
          builder.create().show();    
   }  
     
     
    //发邮件   
    private   void  sendMail(String emailBody){  
        Intent email =  new  Intent(android.content.Intent.ACTION_SEND);  
        email.setType( "plain/text" );  
        String  emailSubject =  "共享软件" ;  
          
         //设置邮件默认地址   
        // email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);   
         //设置邮件默认标题   
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);  
         //设置要默认发送的内容   
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);  
         //调用系统的邮件系统   
        startActivityForResult(Intent.createChooser(email,  "请选择邮件发送软件" ), 1001 );  
   }  
     
     
     
    //发短信   
    private   void  sendSMS(String notetext){  
   Uri smsToUri = Uri.parse( "smsto:" );  
   Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);  
   sendIntent.putExtra("sms_body", notetext);  
   startActivity(sendIntent);  
   }  

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
