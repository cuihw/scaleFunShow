package com.example.scalefunshow;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scalefunshow.bean.AdjustBean;
import com.example.scalefunshow.database.DBHelper;
import com.example.scalefunshow.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdjustRecordActivity extends Activity {

	private static final String TAG = "AdjustRecordActivity";

	ListView listView;

	List<AdjustBean> adjustList;

	DBHelper dbHelper = new DBHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_record);
		listView = (ListView)findViewById(R.id.listview_record);
		Log.i(TAG, "listView = " + listView);

		initListview();
	}

	
	private void initListview() {
		Log.i(TAG, "initListview .....");
		listView = (ListView)findViewById(R.id.listview_record);
		Log.i(TAG, "listView = " + listView);
		getAdjustRecord();
	}

	private void getAdjustRecord() {
		new AsyncTask<String, Integer, String>() {
			@Override
			protected String doInBackground(String... params) {
				adjustList = dbHelper.getAllAdjust();
				if (adjustList == null || adjustList.size() == 0) {
					adjustList = new ArrayList<AdjustBean>();
					for (int i = 0; i < 18; i ++) {
						AdjustBean bean = new AdjustBean();
						bean.setId(i);
						bean.setIsRight("是");
						bean.setOperator("测试员" + i);
						bean.setWeight("300克");
						bean.setFamaWeight("300克");
						bean.setPoint( "" + (i%5 + 1));
						bean.setTime("15:30");
						adjustList.add(bean);
					}
				}
				Log.i(TAG, "get list.....");
				return null;
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				showList();
			}
		}.execute();
	}

	private void showList() {
		Log.i(TAG, "showList .....");
		AdjustRecordAdapter adapter = new AdjustRecordAdapter(adjustList);
		listView.setAdapter(adapter);
	}

	public class AdjustRecordAdapter extends BaseAdapter {
		List<AdjustBean> list;
		public AdjustRecordAdapter(List<AdjustBean> list){
			this.list = list;
		}
		@Override
		public int getCount() {
			if (list == null) return 0;
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			if (list == null) return null;
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();

				convertView = LayoutInflater.from(AdjustRecordActivity.this).inflate(
					R.layout.adjust_item, null);
				convertView.setTag(viewHolder);

				viewHolder.tv_index = (TextView) convertView
					.findViewById(R.id.tv_index);
				viewHolder.tv_operator = (TextView) convertView
					.findViewById(R.id.tv_operator);
				viewHolder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
				viewHolder.tv_fama_weight = (TextView) convertView
					.findViewById(R.id.tv_fama_weight);
				viewHolder.tv_weight = (TextView) convertView
					.findViewById(R.id.tv_weight);
				viewHolder.tv_point = (TextView) convertView
					.findViewById(R.id.tv_point);
				viewHolder.tv_pass = (TextView) convertView
					.findViewById(R.id.tv_pass);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			AdjustBean bean = list.get(position);
			viewHolder.tv_index.setText("" + bean.getId());
			viewHolder.tv_operator.setText(bean.getOperator());
			viewHolder.tv_time.setText(bean.getTime());
			viewHolder.tv_fama_weight.setText(bean.getFamaWeight());
			viewHolder.tv_weight.setText(bean.getWeight());
			viewHolder.tv_point.setText(bean.getPoint());
			viewHolder.tv_pass.setText(bean.getIsRight());

			if (0 == position % 2) {
				convertView.setBackgroundColor(getResources().getColor(R.color.bgcolor));
			} else {
				convertView.setBackgroundColor(getResources().getColor(R.color.orange_transparency));
			}
			return convertView;
		}
	}

	static class ViewHolder {
		public TextView tv_index;
		public TextView tv_operator;
		public TextView tv_time;
		public TextView tv_fama_weight;
		public TextView tv_weight;
		public TextView tv_point;
		public TextView tv_pass;
	}
}
