package edu.ztone.timenote.register;

import java.util.ArrayList;
import java.util.List;

import com.example.timenote.R;

import edu.ztone.timenote.biz.UserBiz;
import edu.ztone.timenote.biz.impl.UserBizImpl;
import edu.ztone.timenote.login.RegisterActivity;
import edu.ztone.timenote.po.User;
import edu.ztone.timenote.util.MyApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivityThree extends Activity {
	private Button bt_three;
	private Spinner PSpinner;
	private Spinner CSpinner;
	private RadioGroup radioGroup;
	private RadioButton male, female;
	private String sex = "男";
	private List<String> citylist;
	private ArrayAdapter<String> adapter;
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registerthree_layout);
		myApplication = (MyApplication) getApplication();
		bt_three = (Button) findViewById(R.id.RTH_bt_three);
		bt_three.setOnClickListener(new MyListenerClick());
		PSpinner = (Spinner) findViewById(R.id.RTH_spnRegisterProvinces);
		CSpinner = (Spinner) findViewById(R.id.RTH_spnRegisterCitys);
		radioGroup = (RadioGroup) findViewById(R.id.RTH_rdoGupRergisterSex);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				sex = "男";
				if (arg1 == female.getId()) {
					sex = "女";
				}

			}
		});
		male = (RadioButton) findViewById(R.id.RTH_rdoRegisterMale);
		female = (RadioButton) findViewById(R.id.RTH_rdoRegisterFemale);
		PSpinner.setOnItemSelectedListener(new MyOISListener());

	}

	private class MyListenerClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.RTH_bt_three:
			
				myApplication.setProvince(PSpinner.toString());
				myApplication.setCity(CSpinner.toString());
				myApplication.setUser_sex(sex);
				Intent intent = new Intent();
				intent.setClass(RegisterActivityThree.this,
						RegisterActivityFour.class);
				startActivity(intent);
				finish();
				break;

			default:
				break;
			}

		}

	}

	private class MyOISListener implements AdapterView.OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			citylist = new ArrayList<String>();

			switch (arg2) {
			case 0:
				citylist.add("̫太原市");
				citylist.add("大同市");
				citylist.add("阳泉市");
				citylist.add("长治市");
				citylist.add("临汾市");
				citylist.add("晋中市");
				citylist.add("运城市");
				citylist.add("晋城市");
				citylist.add("忻州市");
				citylist.add("朔州市");
				citylist.add("吕梁市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 1:
				citylist.add("石家庄");
				citylist.add("保定市");
				citylist.add("秦皇岛");
				citylist.add("唐山市");
				citylist.add("邯郸市");
				citylist.add("邢台市");
				citylist.add("沧州市");
				citylist.add("承德市");
				citylist.add("廊坊市");
				citylist.add("衡水市");
				citylist.add("张家口");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 2:
				citylist.add("郑州市");
				citylist.add("洛阳市");
				citylist.add("焦作市");
				citylist.add("商丘市");
				citylist.add("信阳市");
				citylist.add("新乡市");
				citylist.add("安阳市");
				citylist.add("开封市");
				citylist.add("漯河市");
				citylist.add("南阳市");
				citylist.add("鹤壁市");
				citylist.add("平顶山");
				citylist.add("濮阳市");
				citylist.add("许昌市");
				citylist.add("周口市");
				citylist.add("三门峡");
				citylist.add("驻马店");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 3:
				citylist.add("呼和浩特");
				citylist.add("呼伦贝尔");
				citylist.add("包头市");
				citylist.add("赤峰市");
				citylist.add("乌海市");
				citylist.add("通辽市");
				citylist.add("鄂尔多斯");
				citylist.add("乌兰察布");
				citylist.add("巴彦淖尔");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 4:
				citylist.add("盘锦市");
				citylist.add("鞍山市");
				citylist.add("抚顺市");
				citylist.add("本溪市");
				citylist.add("铁岭市");
				citylist.add("锦州市");
				citylist.add("丹东市");
				citylist.add("辽阳市");
				citylist.add("葫芦岛");
				citylist.add("阜新市");
				citylist.add("朝阳市");
				citylist.add("营口市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 5:
				citylist.add("吉林市");
				citylist.add("通化市");
				citylist.add("白城市");
				citylist.add("四平市");
				citylist.add("辽源市");
				citylist.add("松原市");
				citylist.add("白山市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 6:
				citylist.add("伊春市");
				citylist.add("牡丹江");
				citylist.add("大庆市");
				citylist.add("鸡西市");
				citylist.add("鹤岗市");
				citylist.add("绥化市");
				citylist.add("双鸭山");
				citylist.add("七台河");
				citylist.add("佳木斯");
				citylist.add("黑河市");
				citylist.add("齐齐哈尔市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 7:
				citylist.add("无锡市");
				citylist.add("常州市");
				citylist.add("扬州市");
				citylist.add("徐州市");
				citylist.add("苏州市");
				citylist.add("连云港");
				citylist.add("盐城市");
				citylist.add("淮安市");
				citylist.add("宿迁市");
				citylist.add("镇江市");
				citylist.add("南通市");
				citylist.add("泰州市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 8:
				citylist.add("绍兴市");
				citylist.add("温州市");
				citylist.add("湖州市");
				citylist.add("嘉兴市");
				citylist.add("台州市");
				citylist.add("金华市");
				citylist.add("舟山市");
				citylist.add("衢州市");
				citylist.add("丽水市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 9:
				citylist.add("合肥市");
				citylist.add("芜湖市");
				citylist.add("亳州市");
				citylist.add("马鞍山");
				citylist.add("池州市");
				citylist.add("淮南市");
				citylist.add("淮北市");
				citylist.add("蚌埠市");
				citylist.add("安庆市");
				citylist.add("宿州市");
				citylist.add("宣城市");
				citylist.add("滁州市");
				citylist.add("黄山市");
				citylist.add("六安市");
				citylist.add("阜阳市");
				citylist.add("铜陵市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 10:
				citylist.add("福州市");
				citylist.add("泉州市");
				citylist.add("漳州市");
				citylist.add("南平市");
				citylist.add("三明市");
				citylist.add("龙岩市");
				citylist.add("莆田市");
				citylist.add("宁德市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 11:
				citylist.add("南昌市");
				citylist.add("赣州市");
				citylist.add("景德镇");
				citylist.add("九江市");
				citylist.add("萍乡市");
				citylist.add("新余市");
				citylist.add("抚州市");
				citylist.add("宜春市");
				citylist.add("上饶市");
				citylist.add("鹰潭市");
				citylist.add("吉安市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 12:
				citylist.add("潍坊市");
				citylist.add("淄博市");
				citylist.add("威海市");
				citylist.add("枣庄市");
				citylist.add("泰安市");
				citylist.add("临沂市");
				citylist.add("东营市");
				citylist.add("济宁市");
				citylist.add("烟台市");
				citylist.add("菏泽市");
				citylist.add("日照市");
				citylist.add("德州市");
				citylist.add("聊城市");
				citylist.add("滨州市");
				citylist.add("莱芜市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 13:
				citylist.add("荆门市");
				citylist.add("咸宁市");
				citylist.add("襄樊市");
				citylist.add("荆州市");
				citylist.add("黄石市");
				citylist.add("宜昌市");
				citylist.add("随州市");
				citylist.add("鄂州市");
				citylist.add("孝感市");
				citylist.add("黄冈市");
				citylist.add("十堰市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 14:
				citylist.add("长沙市");
				citylist.add("郴州市");
				citylist.add("娄底市");
				citylist.add("衡阳市");
				citylist.add("株洲市");
				citylist.add("湘潭市");
				citylist.add("岳阳市");
				citylist.add("常德市");
				citylist.add("邵阳市");
				citylist.add("益阳市");
				citylist.add("永州市");
				citylist.add("张家界");
				citylist.add("怀化市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 15:
				citylist.add("江门市");
				citylist.add("佛山市");
				citylist.add("汕头市");
				citylist.add("湛江市");
				citylist.add("韶关市");
				citylist.add("中山市");
				citylist.add("珠海市");
				citylist.add("茂名市");
				citylist.add("肇庆市");
				citylist.add("阳江市");
				citylist.add("惠州市");
				citylist.add("潮州市");
				citylist.add("揭阳市");
				citylist.add("清远市");
				citylist.add("河源市");
				citylist.add("东莞市");
				citylist.add("汕尾市");
				citylist.add("云浮市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 16:
				citylist.add("南宁市");
				citylist.add("贺州市");
				citylist.add("柳州市");
				citylist.add("桂林市");
				citylist.add("梧州市");
				citylist.add("北海市");
				citylist.add("玉林市");
				citylist.add("钦州市");
				citylist.add("百色市");
				citylist.add("防城港");
				citylist.add("贵港市");
				citylist.add("河池市");
				citylist.add("崇左市");
				citylist.add("来宾市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 17:
				citylist.add("海口市");
				citylist.add("三亚市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 18:
				citylist.add("乐山市");
				citylist.add("雅安市");
				citylist.add("广安市");
				citylist.add("南充市");
				citylist.add("自贡市");
				citylist.add("泸州市");
				citylist.add("内江市");
				citylist.add("宜宾市");
				citylist.add("广元市");
				citylist.add("达州市");
				citylist.add("资阳市");
				citylist.add("绵阳市");
				citylist.add("眉山市");
				citylist.add("巴中市");
				citylist.add("攀枝花");
				citylist.add("遂宁市");
				citylist.add("德阳市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 19:
				citylist.add("贵阳市");
				citylist.add("安顺市");
				citylist.add("遵义市");
				citylist.add("六盘水");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 20:
				citylist.add("昆明市");
				citylist.add("玉溪市");
				citylist.add("大理市");
				citylist.add("曲靖市");
				citylist.add("昭通市");
				citylist.add("保山市");
				citylist.add("丽江市");
				citylist.add("临沧市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 21:
				citylist.add("拉萨市");
				citylist.add("阿里");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 22:
				citylist.add("咸阳市");
				citylist.add("榆林市");
				citylist.add("宝鸡市");
				citylist.add("铜川市");
				citylist.add("渭南市");
				citylist.add("汉中市");
				citylist.add("安康市");
				citylist.add("商洛市");
				citylist.add("延安市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 23:
				citylist.add("兰州市");
				citylist.add("白银市");
				citylist.add("武威市");
				citylist.add("金昌市");
				citylist.add("平凉市");
				citylist.add("张掖市");
				citylist.add("嘉峪关");
				citylist.add("酒泉市");
				citylist.add("庆阳市");
				citylist.add("定西市");
				citylist.add("陇南市");
				citylist.add("天水市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 24:
				citylist.add("西宁市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 25:
				citylist.add("银川市");
				citylist.add("固原市");
				citylist.add("青铜峡市");
				citylist.add("石嘴山市");
				citylist.add("中卫市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 26:
				citylist.add("乌鲁木齐");
				citylist.add("克拉玛依市");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 27:
				citylist.add("香港岛");
				citylist.add("九龙");
				citylist.add("新界");
				citylist.add("新界西");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 28:
				citylist.add("澳门半岛");
				citylist.add("澳门离岛");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
				break;
			case 29:
				citylist.add("基隆市");
				citylist.add("台中市");
				citylist.add("新竹市");
				citylist.add("台南市");
				citylist.add("嘉义市");
				citylist.add("台北县");
				citylist.add("台东县");
				citylist.add("澎湖县");
				citylist.add("花莲县");
				citylist.add("屏东县");
				citylist.add("高雄县");
				citylist.add("台南县");
				citylist.add("嘉义县");
				citylist.add("云林县");
				citylist.add("南投县");
				citylist.add("彰化县");
				citylist.add("台中县");
				citylist.add("苗栗县");
				citylist.add("桃园县");
				citylist.add("宜兰县");
				citylist.add("新竹县");
				citylist.add("台北市");
				citylist.add("高雄市");
				citylist.add("马祖县");
				citylist.add("金门县");
				adapter = new ArrayAdapter<String>(RegisterActivityThree.this,
						android.R.layout.simple_spinner_dropdown_item, citylist);
				CSpinner.setAdapter(adapter);
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
}
