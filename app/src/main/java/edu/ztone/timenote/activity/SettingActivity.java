package edu.ztone.timenote.activity;

import com.example.timenote.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

public class SettingActivity extends ExpandableListActivity {
	
	
	private ActionBar sActionBar;
	//声明adapter
	private ExpandableListAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.settings_layout);
		
		sActionBar = getActionBar();
		sActionBar.setDisplayHomeAsUpEnabled(true);
		sActionBar.setDisplayShowHomeEnabled(false);
		//设这标题
				setTitle("返回");
				//实例化adapter
				mAdapter = new MyExpandableListAdapter();
				//为列表设置adapter
				setListAdapter(mAdapter);
				//为list注册context菜单
				registerForContextMenu(this.getExpandableListView());
	}
	
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		
		return super.onMenuItemSelected(featureId, item);
		
	}
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		//Toast.makeText(this, " 组元素索引: " + groupPosition + " 子元素索引: " + childPosition, Toast.LENGTH_SHORT).show();
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}


	@Override
	public void onGroupExpand(int groupPosition) {
		//Toast.makeText(this, " 组元素索引: " + groupPosition, Toast.LENGTH_SHORT).show();
		super.onGroupExpand(groupPosition);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("上下文菜单");
		menu.add(0, 0, 0, "单击我");
	}

	// 单击上下文菜单后的逻辑
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
		String title = ((TextView) info.targetView).getText().toString();

		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
			//Toast.makeText(this, title + " 组元素索引: " + groupPos + " 子元素索引: " + childPos, Toast.LENGTH_SHORT).show();
			return true;
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			//Toast.makeText(this, title + " 组元素索引: " + groupPos, Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}
	//自定义Adapter
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		// 父列表数据
		private String[] groups = 
		{ 
			"      同步账户", 
			"      备份到本地", 
			"      产品介绍",
			"      意见反馈" ,
			"      版本信息"
		};
		// 子列表数据
		private String[][] children = 
		{
			{ "您的有效邮箱多余一个，请点击选择。" },
			{ "日记的备份路径：SD/TimeNote/notes.txt" },
			{ "www.Ztone.com" },
			{"请联系邮箱:shiming_xsm@163.com"},
			{ "TimeNote2.0公测版，请放心使用！" }
		};
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return children[groupPosition][childPosition];
		}
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}
		@Override
		public int getChildrenCount(int groupPosition) {
			return children[groupPosition].length;
		}
		// 取子列表中的某一项的 View
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			return textView;
		}
		@Override
		public Object getGroup(int groupPosition) {
			return groups[groupPosition];
		}
		@Override
		public int getGroupCount() {
			return groups.length;
		}
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}
		// 取父列表中的某一项的 View
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setText(getGroup(groupPosition).toString());
			return textView;
		}
		@Override
		public boolean hasStableIds() {
			return true;
		}
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		// 获取某一项的 View 的逻辑
		private TextView getGenericView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 120);
			TextView textView = new TextView(SettingActivity.this);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setPadding(32, 0, 0, 0);
			return textView;
		}
	}

	
}
