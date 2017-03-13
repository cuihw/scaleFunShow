package com.example.scalefunshow.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.scalefunshow.R;
import com.example.scalefunshow.adpter.TaskAdapter.ViewHolder;
import com.example.scalefunshow.bean.Material;
import com.example.scalefunshow.bean.TaskBean;

public class FoodAdapter extends BaseAdapter {

	List<Material> list;
	Context context;
	private int totalCount;
	private int selection = -1;

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public FoodAdapter(Context context, List<Material> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		if (list != null)
			return list.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (list != null)
			return list.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.food_item, null);

			convertView.setTag(viewHolder);
			viewHolder.tvPeifang = (TextView) convertView
					.findViewById(R.id.name);
			viewHolder.tvcount = (TextView) convertView
					.findViewById(R.id.count);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Material m = list.get(position);

		viewHolder.tvPeifang.setText("" + m.getName());
		viewHolder.tvcount.setText("" + (m.getCount() * totalCount) + "KG");

		if (selection == position) {
			convertView.setBackgroundResource(R.drawable.corners_bg_pressed);
		} else {
			convertView.setBackgroundResource(R.drawable.corners_bg);
		}
		return convertView;
	}

	static class ViewHolder {
		public TextView tvPeifang;
		public TextView tvcount;
	}
}
