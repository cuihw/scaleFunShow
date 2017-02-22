package com.example.scalefunshow;


import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import com.example.scalefunshow.bean.TaskBean;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TaskActivity extends Activity {
    
    String[] peifangnames = getResources().getStringArray(R.array.peifang);
    
    List<TaskBean> taskList = new ArrayList<TaskBean> ();
    TaskBean currentTask;

    String[] jiaobanjihao = getResources().getStringArray(R.array.jiaobanjihao);


    String[] peiLiaoFangShi = getResources().getStringArray(R.array.peiliaofangshi);
    Spinner peiliaoSpinner;
    Spinner peifangSpinner;
    Spinner jiaobanjiSpinner;

    Button ok_button;
    Button back;
    Button countius;

    EditText countEdit;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_task);

        ok_button = (Button)  findViewById(R.id.ok_button);
        back = (Button)  findViewById(R.id.back);
        countius = (Button)  findViewById(R.id.countius);
        countEdit = (EditText)findViewById(R.id.count);

        currentTask = new TaskBean();

        peifangSpinner = (Spinner) findViewById(R.id.spinner1);
        peifangSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0)
                    currentTask.setPeifangming(peifangnames[position]);
                else currentTask.setPeifangming(null);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        peiliaoSpinner = (Spinner) findViewById(R.id.spinner2);
        peiliaoSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0)
                    currentTask.setPeiliaofangshi(peiLiaoFangShi[position]);
                else
                    currentTask.setPeiliaofangshi(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        jiaobanjiSpinner = (Spinner) findViewById(R.id.spinner3);
        jiaobanjiSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) currentTask.setJiaobanjiID(jiaobanjihao[position]);
                else currentTask.setJiaobanjiID(null);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
	}

	public void onClick(View view) {
	    if (ok_button == view) {
            saveTask();
        } else if (back == view){
            clearCurrentTask();
        } else if (countius == view){
            addTask2List();
            clearCurrentTask();
        }
	}
    
    private void saveTask() {
    }
    
    private void addTask2List() {
        String strCount = countEdit.getText().toString();
        if (TextUtils.isEmpty(strCount)) {
            Toast.makeText(this, "输入的“份数”不对，请确认是整数份。" , Toast.LENGTH_SHORT).show();
            return;
        }

        int count = Integer.parseInt(strCount);

        if (count < 1) {
            Toast.makeText(this, "输入的“份数”不对，请确认是整数份。" , Toast.LENGTH_SHORT).show();
            return;
        }
        currentTask.setCount(count);

        if (TextUtils.isEmpty(currentTask.getPeifangming())) {
            Toast.makeText(this, "输入的配方名不对，请确认。" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentTask.getPeiliaofangshi())) {
            Toast.makeText(this, "输入的配料方式不对，请确认。" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentTask.getJiaobanjiID())) {
            Toast.makeText(this, "输入的搅拌机号不对，请确认。" , Toast.LENGTH_SHORT).show();
            return;
        }
        taskList.add(currentTask);
    }
    
    private void clearCurrentTask() {
        currentTask = new TaskBean();
        peiliaoSpinner.setSelection(0);
        jiaobanjiSpinner.setSelection(0);
        peifangSpinner.setSelection(0);
        countEdit.setText("");
    }

}
