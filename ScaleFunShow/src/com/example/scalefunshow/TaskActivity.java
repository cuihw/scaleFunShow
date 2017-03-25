package com.example.scalefunshow;

import com.example.scalefunshow.utils.SharedPrefUtils;
import com.example.scalefunshow.utils.TaskCache;
import com.example.scalefunshow.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import com.example.scalefunshow.adpter.TaskAdapter;
import com.example.scalefunshow.bean.TaskBean;
import com.example.scalefunshow.utils.ZzLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TaskActivity extends Activity {
    
    private static final String TAG = "TaskActivity";
    List<TaskBean> taskList = new ArrayList<TaskBean>();

    TaskBean currentTask;
    String[] peifangnames;
    String[] jiaobanjihao;
    String[] peiLiaoFangShi;
    Spinner peiliaoSpinner;
    Spinner peifangSpinner;
    Spinner jiaobanjiSpinner;
    Button ok_button;
    Button back;
    Button countius;
    EditText countEdit;
    ListView listView1;
    TaskAdapter adpter;
    
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_task);

        ok_button = (Button) findViewById(R.id.ok_button);
        back = (Button) findViewById(R.id.back);
        countius = (Button) findViewById(R.id.add_task);
        countEdit = (EditText) findViewById(R.id.count);
//        Utils.hideEditTextIMM(this, countEdit); // 隐藏输入法。
//        countEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (countEdit == v && hasFocus) {
//
//                }
//            }
//        });
        initTitle();

        peifangnames = getResources().getStringArray(R.array.peifang);
        jiaobanjihao = getResources().getStringArray(R.array.jiaobanjihao);
        peiLiaoFangShi = getResources().getStringArray(R.array.peiliaofangshi);
        currentTask = new TaskBean();
        peifangSpinner = (Spinner) findViewById(R.id.spinner1);
        peifangSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    currentTask.setPeifangming(peifangnames[position]);
                else
                    currentTask.setPeifangming(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        peiliaoSpinner = (Spinner) findViewById(R.id.spinner2);
        peiliaoSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    currentTask.setPeiliaofangshi(peiLiaoFangShi[position]);
                else
                    currentTask.setPeiliaofangshi(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        jiaobanjiSpinner = (Spinner) findViewById(R.id.spinner3);
        jiaobanjiSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    currentTask.setJiaobanjiID(jiaobanjihao[position]);
                else
                    currentTask.setJiaobanjiID(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        initListView();
    }

    private void initTitle() {
         
    }

    private void initListView() {
        // get task
        Gson gson = new Gson();
        String task = SharedPrefUtils.getString(this, "task", "");

        ZzLog.i(TAG, "reading task = " + task);

        List<TaskBean> retList = null;
        if (!TextUtils.isEmpty(task)) {
            retList = gson.fromJson(task, new TypeToken<List<TaskBean>>() {
            }.getType());
        }
        if (retList != null) {
            taskList = retList;
        }
        listView1 = (ListView)findViewById(R.id.listView1);
        adpter = new TaskAdapter(this, taskList);
        listView1.setAdapter(adpter);
    }

    public void onClick(View view) {
        if (ok_button == view) {
            saveTask();
            Utils.startActivity(this, Login.class);
        } else if (back == view) {
            clearCurrentTask();

            Intent intent = new Intent();
            intent.setClass(this, Login.class);
            startActivity(intent);
            finish();
        } else if (countius == view) {
            addTask2List();
            clearCurrentTask();
        }
    }

    private void saveTask() {
        Gson gson = new Gson();
        if (taskList.size() == 0) {
        	return;
        }
        String task = gson.toJson(taskList);
        ZzLog.i(TAG, "task = " + task);
        SharedPrefUtils.SetString(this, "task", task);

        TaskCache.setTaskList(taskList);
    }

    private void addTask2List() {
        String strCount = countEdit.getText().toString();
        if (TextUtils.isEmpty(strCount)) {
            Toast.makeText(this, "输入的“份数”不对，请确认是整数份。", Toast.LENGTH_SHORT).show();
            return;
        }

        int count = Integer.parseInt(strCount);

        if (count < 1 || count > 60) {
            Toast.makeText(this, "输入的“份数”不对，请确认是整数份。份数在1到60之间", Toast.LENGTH_SHORT).show();
            return;
        }
        currentTask.setCount(count);

        if (TextUtils.isEmpty(currentTask.getPeifangming())) {
            Toast.makeText(this, "输入的配方名不对，请确认。", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentTask.getPeiliaofangshi())) {
            Toast.makeText(this, "输入的配料方式不对，请确认。", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentTask.getJiaobanjiID())) {
            Toast.makeText(this, "输入的搅拌机号不对，请确认。", Toast.LENGTH_SHORT).show();
            return;
        }
        taskList.add(currentTask);
        adpter.notifyDataSetChanged();
        saveTask();
    }

    private void clearCurrentTask() {
        currentTask = new TaskBean();
        peiliaoSpinner.setSelection(0);
        jiaobanjiSpinner.setSelection(0);
        peifangSpinner.setSelection(0);
        countEdit.setText("");
    }
}
