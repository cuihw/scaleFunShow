package com.example.scalefunshow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.scalefunshow.adpter.TaskAdpter;
import com.example.scalefunshow.bean.TaskBean;
import com.example.scalefunshow.utils.TaskCache;
import com.example.scalefunshow.utils.Utils;

import java.util.List;

public class TaskChooseActivity extends Activity {

	GridView task_grid;

	List<TaskBean> taskList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_choose_task);
		taskList = TaskCache.getTaskList();
		task_grid = (GridView)findViewById(R.id.task_grid);
		task_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TaskCache.current = taskList.get(position);
			}
		});
		initGrid();
	}
	
	private void initGrid() {
		TaskAdpter adpter = new TaskAdpter(this, taskList);
		adpter.setLayout(R.layout.work_item);
		task_grid.setAdapter(adpter);
	}
}
