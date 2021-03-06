package com.example.scalefunshow;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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

public class TaskChooseActivity extends Activity {

	private static final String TAG = "TaskChooseActivity";

	GridView task_grid;

	List<TaskBean> taskList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_choose_task);
		taskList = TaskCache.getTaskList();
		if (taskList == null || taskList.size() == 0) {

	        Gson gson = new Gson();
	        String task = SharedPrefUtils.getString(this, "task", "");

	        ZzLog.i(TAG, "reading task = " + task);

	        List<TaskBean> retList = null;
	        if (!TextUtils.isEmpty(task)) {
	            retList = gson.fromJson(task, new TypeToken<List<TaskBean>>()
	            		{}.getType());
	        }
	        if (retList != null) {
	            taskList = retList;
	        }
	        TaskCache.setTaskList(taskList);
		}
		initGrid();
		initTitle();
	}

    private void initTitle() {
        TextView title = (TextView)findViewById(R.id.title);
        title.setText("选择任务");
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


	private void initGrid() {
		task_grid = (GridView)findViewById(R.id.task_grid);
		task_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ZzLog.i(TAG, "onItemClick = " + position);
				TaskCache.current = taskList.get(position);
				if (TaskCache.current.isCompleted()) {
				    Toast.makeText(TaskChooseActivity.this, "已经称量完成", Toast.LENGTH_SHORT).show();
				} else {
	                Utils.startActivity(TaskChooseActivity.this, WorkingActivity1.class);
				}
			}
		});
		if (taskList == null || taskList.size() == 0) {
			// no task.
		}
		TaskAdapter adpter = new TaskAdapter(this, taskList);
		adpter.setLayout(R.layout.work_item);
		task_grid.setAdapter(adpter);
	}
}
