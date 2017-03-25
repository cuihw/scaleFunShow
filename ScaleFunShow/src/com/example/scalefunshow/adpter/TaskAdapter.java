package com.example.scalefunshow.adpter;

import java.util.List;

import com.example.scalefunshow.R;
import com.example.scalefunshow.bean.TaskBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cuihuawei on 2/22/2017.
 */
/*用于显示已经添加的任务列表*/
public class TaskAdapter  extends BaseAdapter{

    List<TaskBean> list;
    Context context;
    private int resourceid = 0;

    public TaskAdapter(Context context, List<TaskBean> list) {
        this.list = list;
        this.context = context;
    }

    public void setLayout(int resourceid) {
        this.resourceid = resourceid;
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
            if (resourceid != 0) {
                convertView = LayoutInflater.from(context).inflate(resourceid, null);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.task_item, null);
            }
            convertView.setTag(viewHolder);
            if (resourceid != 0) {
                viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageView_food);
                viewHolder.finished = (ImageView) convertView.findViewById(R.id.finished);
            }
            viewHolder.tvPeifang = (TextView) convertView.findViewById(R.id.name);
            viewHolder.tvcount = (TextView) convertView.findViewById(R.id.count);
            viewHolder.tvjiaobanji = (TextView) convertView.findViewById(R.id.jiaobanji_id);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TaskBean taskBean = list.get(position);
        String name = taskBean.getPeifangming();
        /*
        <item>小油条</item>
        <item>元宵</item>
        <item>韭菜鸡蛋饺子</item>
        <item>小麻团</item>*/
        if (resourceid != 0) {
            if ("小油条".equals(name)){
                viewHolder.imageview.setImageResource(R.drawable.xiaoyoutiao);
            }
            if ("元宵".equals(name)){
                viewHolder.imageview.setImageResource(R.drawable.yuanxiao);
            }
            if ("韭菜鸡蛋饺子".equals(name)){
                viewHolder.imageview.setImageResource(R.drawable.jiaozi);
            }
            if ("小麻团".equals(name)){
                viewHolder.imageview.setImageResource(R.drawable.xiaomatuan);
            }
            if (taskBean.isCompleted()) {
                viewHolder.finished.setVisibility(View.VISIBLE);
            } else {
                viewHolder.finished.setVisibility(View.GONE);
                
            }
        }
        if (name.length()> 5) {
            name = name.substring(0,5);
        }
        viewHolder.tvPeifang.setText(name);
        viewHolder.tvcount.setText("份数：" + taskBean.getCount());
        viewHolder.tvjiaobanji.setText("搅拌机号：" + taskBean.getJiaobanjiID());
        return convertView;
    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvPeifang;
        public TextView tvcount;
        public TextView tvjiaobanji;
        public ImageView finished;
    }
}
