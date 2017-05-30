package edu.ztone.timenote.adapter;

import java.util.List;

import com.example.timenote.R;

import edu.ztone.timenote.util.MainActivityLiftListModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LiftListAdapter extends BaseAdapter {
	private Context context;
	private List<MainActivityLiftListModel> list;

	public LiftListAdapter(Context context, List<MainActivityLiftListModel> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (list != null) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold hold;
		if (convertView == null) {
			hold = new ViewHold();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.leftlist_item, null);
			convertView.setTag(hold);
		} else {
			hold = (ViewHold) convertView.getTag();
		}

		hold.imageView = (ImageView) convertView
				.findViewById(R.id.leftlist_image);
		hold.textView = (TextView) convertView.findViewById(R.id.leftlist_text);

		hold.imageView.setImageResource(list.get(position).getImageView());
		hold.textView.setText(list.get(position).getText());
		return convertView;
	}

	class ViewHold {
		public ImageView imageView;
		public TextView textView;
	}

}
