package com.example.scalefunshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scalefunshow.adpter.TaskAdapter;
import com.example.scalefunshow.bean.TaskBean;
import com.example.scalefunshow.utils.SharedPrefUtils;
import com.example.scalefunshow.utils.TaskCache;
import com.example.scalefunshow.utils.Utils;
import com.example.scalefunshow.utils.ZzLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ManageActivity extends Activity {
 
    Button add_task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Utils.hideNavigationBar(this); 
        setContentView(R.layout.activity_manage);
        add_task = (Button)findViewById(R.id.add_task);
		initTitle();
	}

    private void initTitle() {
        TextView title = (TextView)findViewById(R.id.title);
        title.setText("管理台秤");
        updateTaskList();
        ImageView back =  (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener(){
            @Override
           public void onClick(View arg0) {
                finish();
           }});
    }

    private void updateTaskList() {
        TextView total_task  = (TextView)findViewById(R.id.total_task);
        TextView tvtitle = (TextView)findViewById(R.id.tvtitle);

        List<TaskBean> taskList = TaskCache.getTaskList();
        if (taskList != null) {
            total_task.setText("总任务数：" + taskList.size());
            int countCompleted = 0;
            for (int i = 0; i < taskList.size(); i++){
                if (taskList.get(i).isCompleted()) {
                    countCompleted ++;
                }
            }
            tvtitle.setText("已完成：" + countCompleted);
        } 
    }

    public void onClick(View view) {
        if (view == add_task) {
            Intent intent = new Intent();
            intent.setClass(this, TaskActivity.class);
            startActivity(intent);
        } else {
            Utils.startActivity(this, AdjustRecordActivity.class);
        }
    }
 
}
