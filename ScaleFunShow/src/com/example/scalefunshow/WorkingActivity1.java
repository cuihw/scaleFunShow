package com.example.scalefunshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.scalefunshow.adpter.FoodAdapter;
import com.example.scalefunshow.bean.Material;
import com.example.scalefunshow.bean.TaskBean;
import com.example.scalefunshow.bean.XiaoYouTiaopeifang;
import com.example.scalefunshow.tscale.TScale;
import com.example.scalefunshow.utils.SharedPrefUtils;
import com.example.scalefunshow.utils.TaskCache;
import com.example.scalefunshow.utils.Utils;
import com.example.scalefunshow.utils.ZzLog;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WorkingActivity1 extends Activity {

    private static final String TAG = "WorkingActivity1";

    TaskBean task;

    LinearLayout weight_of_skin_layout;

    private int currentIndex;
    TextView weight_of_skin;
    TextView peifang_name;
    TextView skin_weight;
    TextView current_total;
    TextView xiaoliaoName;
    TextView next_name;
    
    TextView current_weight;
    Button skin_ok;
    ProgressBar progressBar1;
    
    float skinweight;
    List<Material> listMaterial;

    List<Float> listWeight;
    float totalWeight;
    int count;
    
    Button ok_button;

    boolean isStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_working1);

        weight_of_skin_layout = (LinearLayout) findViewById(R.id.weight_of_skin_layout);
        weight_of_skin_layout.setVisibility(View.INVISIBLE);
        weight_of_skin = (TextView) findViewById(R.id.weight_of_skin);
        skin_weight = (TextView) findViewById(R.id.skin_weight);
        current_total = (TextView) findViewById(R.id.current_total);
        xiaoliaoName = (TextView) findViewById(R.id.xiaoliaoname);
        next_name = (TextView) findViewById(R.id.next_name);
        current_weight = (TextView) findViewById(R.id.current_weight);
        progressBar1  = (ProgressBar) findViewById(R.id.progressBar1);
        peifang_name = (TextView) findViewById(R.id.peifang_name);
        skin_ok = (Button) findViewById(R.id.skin_ok);
        ok_button= (Button) findViewById(R.id.ok_button);
        ok_button.setEnabled(false);
        initTaskInfo();
    }

    private void initTaskInfo() {
 
        task = TaskCache.current;
        count = task.getCount();
        if (count > 60) {
            count = 60;
            task.setCount(60);
        }
        
        String peifangming = task.getPeifangming();
        peifang_name.setText("配方名称：" + peifangming);
        
        //
        
        if (task.getPeifangming().equals("小油条")) {
            // 小油条配方
            XiaoYouTiaopeifang xiaoyoutiao = new XiaoYouTiaopeifang();
            listMaterial = xiaoyoutiao.getList();
        }
        XiaoYouTiaopeifang xiaoyoutiao = new XiaoYouTiaopeifang();
        listMaterial = xiaoyoutiao.getList();
        
        startQupi(0);
    }

    // 去皮
    private void startQupi(int position) {
        Log.i(TAG, "startQupi()");
        currentIndex = position;
        weight_of_skin_layout.setVisibility(View.VISIBLE);
        TScale.getInstence().startWeight("皮重：","克", weight_of_skin);
        Material material = listMaterial.get(position);
        totalWeight = material.getCount()*count;
        
        current_total.setText("当前小料总总量：" + totalWeight + "克");
        Log.i(TAG, "xiaoliaoName = " + material.getName());
        xiaoliaoName.setText(material.getName());
        ok_button.setEnabled(false);
    }

    public void onClick(View view) {
        if (skin_ok == view) {
            weight_of_skin_layout.setVisibility(View.GONE);
            skinweight = TScale.getInstence().getWeight();
            skin_weight.setText("皮重：" + skinweight);
            startWeight(currentIndex);
        } else if(ok_button == view) {
            if (listWeight == null) {
                listWeight = new ArrayList<Float>();
            }
            if (currentIndex == 0) listWeight.clear();
            
            listWeight.add(TScale.getInstence().getWeight()-skinweight);
            if (listMaterial.size() - 1 > currentIndex) {
                currentIndex ++;
                startQupi(currentIndex);
            } else {
                //称量完毕
                task.setCompleted(true);
                saveTask(task);
                finish();
            }
        }
    }
    private void saveTask(TaskBean task) {
        
            Gson gson = new Gson();
            List<TaskBean> taskList = TaskCache.getTaskList();
            if (taskList.size() == 0) {
                return;
            }
            String taskstring = gson.toJson(taskList);
            ZzLog.i(TAG, "task = " + taskstring);
            SharedPrefUtils.SetString(this, "task", taskstring);
    }
    
    private void startWeight(int currentIndex) {
        if (listMaterial.size() - 1 > currentIndex) {
            Material material = listMaterial.get(currentIndex + 1); 
            next_name.setText("下一小料：" + material.getName()
                    + ", " + material.getCount()* count + "克" );
        } else {
            next_name.setText("没有下一小料" );
        }

        TScale.getInstence().startWeight("当前重量：","克", current_weight);
        int max = (int) (totalWeight * 105/100);
        progressBar1.setMax(max);
        
        final Handler mHandler = new Handler();
        isStop = false;
        mHandler.post(new Runnable() {
            @Override
            public void run(){ 
                if (isStop) {
                    
                } else {
                    int weight = (int) (TScale.getInstence().getWeight()-skinweight);
                    progressBar1.setProgress(weight);
                    mHandler.postDelayed(this, 200);
                    if (Math.abs(totalWeight - weight) < totalWeight*5/100) {
                        // 可以点击确定键。
                        isStop = true;
                        ok_button.setEnabled(true);
                    }
                }
            }
        });
     }
}
