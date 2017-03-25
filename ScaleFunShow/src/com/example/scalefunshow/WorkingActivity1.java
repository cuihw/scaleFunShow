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
import com.example.scalefunshow.bean.XiaoYouTiao;
import com.example.scalefunshow.tscale.TScale;
import com.example.scalefunshow.utils.TaskCache;
import com.example.scalefunshow.utils.Utils;
import com.example.scalefunshow.utils.ZzLog;

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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WorkingActivity1 extends Activity {
 
	private static final String TAG = "WorkingActivity1";

	TaskBean task;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.hideNavigationBar(this);
		setContentView(R.layout.activity_working1);
		task = TaskCache.current;

		startQupi(0);
 	}

    // 去皮
    private void startQupi(int position) {
        Log.i(TAG, "startQupi()" );
        currentIndex = position;
        weight_of_skin_layout.setVisibility(View.VISIBLE);
        startWeightHandler(weight_of_skin);
        showCurrentChengzhong();
    }
}
