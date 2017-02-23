package com.example.scalefunshow.adpter;

import java.util.List;

import com.example.scalefunshow.R;
import com.example.scalefunshow.bean.TaskBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by cuihuawei on 2/22/2017.
 */

public class TaskAdpter  extends BaseAdapter{

    List<TaskBean> list;
    Context context;

    public TaskAdpter(Context context, List<TaskBean> list) {
        
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.task_item, null);
            convertView.setTag(viewHolder);
            viewHolder.tvPeifang = (TextView) convertView.findViewById(R.id.name);
            viewHolder.tvcount = (TextView) convertView.findViewById(R.id.count);
            viewHolder.tvjiaobanji = (TextView) convertView.findViewById(R.id.jiaobanji_id);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        TaskBean taskBean = list.get(position);

        viewHolder.tvPeifang.setText("配方名称： " + taskBean.getPeifangming());
        viewHolder.tvcount.setText("生产份数： " + taskBean.getCount());
        viewHolder.tvjiaobanji.setText("搅拌机号： " + taskBean.getJiaobanjiID());
        
        return convertView;
    }

    static class ViewHolder {

        public TextView tvPeifang;
        public TextView tvcount;
        public TextView tvjiaobanji;
    }
}
