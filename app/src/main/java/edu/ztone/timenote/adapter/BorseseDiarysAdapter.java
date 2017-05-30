package edu.ztone.timenote.adapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.example.timenote.R;

import edu.ztone.timenote.adapter.LiftListAdapter.ViewHold;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.opengl.Visibility;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BorseseDiarysAdapter extends BaseAdapter {

	private Context mContext;// 上下文环境
	private List<Map<String, Object>> lstItems;// 待绑定的数据资源集合
	private LayoutInflater inflater;// 布局动态绑定接口（将传入的参数上下文环境绑定到BaseAdapter的默认属性中）

	public BorseseDiarysAdapter(Context mContext,
			List<Map<String, Object>> lstItems) {
		super();
		this.mContext = mContext;
		this.lstItems = lstItems;
		this.inflater = LayoutInflater.from(this.mContext);
	}

	// 适配器自动获取绑定元素个数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.lstItems.size();
	}

	// 谁配器根据下标获取选中的元素对象
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.lstItems.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		// TODO Auto-generated method stub
		// 创建一个MainListViewHolder对象并设置为空
		BorseseDiarysHolder mainListViewHolder = null;
		// 判断convertView是否为空
		if (convertView == null) {
			mainListViewHolder = new BorseseDiarysHolder();
			// 只能通过convertView来实现findViewById
			// 将R.layout.browse_diarys中的数据读出来
			convertView = inflater
					.inflate(R.layout.boresediarys_listitem, null);
			mainListViewHolder.itemDate = (TextView) convertView
					.findViewById(R.id.main_listitem_date);
			mainListViewHolder.itemWeek = (TextView) convertView
					.findViewById(R.id.main_listitem_week);
			mainListViewHolder.itemContent = (TextView) convertView
					.findViewById(R.id.main_listitem_content);
			mainListViewHolder.itemWeather = (ImageView) convertView
					.findViewById(R.id.main_lilayout_weather_);
			mainListViewHolder.itemMood = (ImageView) convertView
					.findViewById(R.id.main_lilayout_mood);
			mainListViewHolder.imageView1 = (ImageView) convertView
					.findViewById(R.id.main_listitem_img1);
			mainListViewHolder.imageView2 = (ImageView) convertView
					.findViewById(R.id.main_listitem_img2);
			convertView.setTag(mainListViewHolder);
		} else {
			mainListViewHolder = (BorseseDiarysHolder) convertView.getTag();
		}

		// 将获取到的组件对象与传入集合进行绑定
		mainListViewHolder.itemWeek.setText(this.lstItems.get(position)
				.get("week").toString());
		mainListViewHolder.itemContent.setText(this.lstItems.get(position)
				.get("dcontent").toString());

		mainListViewHolder.itemDate.setText(this.lstItems.get(position)
				.get("ddate").toString());

		int dWeather = Integer.parseInt(this.lstItems.get(position)
				.get("weather").toString());

		int dMood = Integer.parseInt(this.lstItems.get(position).get("mood")
				.toString());

		// 设置心情：
		switch (dMood) {
		case 0:
			mainListViewHolder.itemMood
					.setBackgroundResource(R.drawable.happy3);
			break;
		case 1:
			mainListViewHolder.itemMood
					.setBackgroundResource(R.drawable.happy2);
			break;
		case 2:
			mainListViewHolder.itemMood
					.setBackgroundResource(R.drawable.happy1);
			break;
		case 3:
			mainListViewHolder.itemMood
					.setBackgroundResource(R.drawable.normal);
			break;
		case 4:
			mainListViewHolder.itemMood.setBackgroundResource(R.drawable.sad1);
			break;
		case 5:
			mainListViewHolder.itemMood.setBackgroundResource(R.drawable.sad2);
			break;
		case 6:
			mainListViewHolder.itemMood.setBackgroundResource(R.drawable.sad3);
			break;
		case 7:
			mainListViewHolder.itemMood.setBackgroundResource(R.drawable.angry);
			break;
		default:
			break;
		}

		// 设置天气：
		switch (dWeather) {
		case 0:
			Log.i("你大爷", "你大爷");
			mainListViewHolder.itemWeather
					.setBackgroundResource(R.drawable.sunny);
			break;
		case 1:
			mainListViewHolder.itemWeather
					.setBackgroundResource(R.drawable.sunnytocloudy);
			break;
		case 2:
			mainListViewHolder.itemWeather
					.setBackgroundResource(R.drawable.cloudy);
			break;
		case 3:
			mainListViewHolder.itemWeather
					.setBackgroundResource(R.drawable.rainny);
			break;
		case 4:
			mainListViewHolder.itemWeather
					.setBackgroundResource(R.drawable.thunder);
			break;
		case 5:
			mainListViewHolder.itemWeather
					.setBackgroundResource(R.drawable.snow);
			break;
		default:
			break;
		}
		Bitmap bitmap = (Bitmap) this.lstItems.get(position).get("image1");
		if (bitmap == null) {
			mainListViewHolder.imageView1.setVisibility(View.GONE);
		} else {
			mainListViewHolder.imageView1.setVisibility(View.VISIBLE);
			mainListViewHolder.imageView1.setImageBitmap(bitmap);
		}

		return convertView;
	}

	public class BorseseDiarysHolder {

		public TextView itemDate, itemContent, itemWeek;
		public ImageView itemWeather, itemMood;
		public ImageView imageView1, imageView2;

	}
}
