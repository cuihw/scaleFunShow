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

public class WorkingActivity extends Activity {

	TaskBean taskBean;
	private int count;
	ListView listView1;
	ListView listView2;
	FoodAdapter adapter;
	SimpleAdapter simpleAdapter;
	
	// 存储当前配方材料
	List<Material> listMaterial;
	
	LinearLayout weight_of_skin_layout;

	private static final int GET_WEIGHT = 1;
	protected static final String TAG = "WorkingActivity";

	private boolean isStop;
	
	TextView agentWeightView;
	
	TextView weight_of_skin;
	TextView view_of_skin;
	TextView gross_weight;
	TextView tv_net_weight;
	
	//读取当前称量的重量
	String weight ;
	float floatWeight;
	float skinWeight;
	float netweight;

	Button skin_ok_btn;
	Button countius_weight_btn;
	int currentIndex;

	// 存储材料称量结果
	Map<String, List> materialMap = new HashMap<String, List>();
	// 材料称量数据 
	// map 中的存储： "tvName"："第一次称量重量：158.5克"； "weight":"158.5"
	List<Map<String, String>> cailiao;

    RelativeLayout  weightLayout;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.hideNavigationBar(this);
		setContentView(R.layout.activity_working);

		weight_of_skin_layout = (LinearLayout)findViewById(R.id.weight_of_skin_layout);
		weight_of_skin_layout.setVisibility(View.INVISIBLE);

		weight_of_skin = (TextView)findViewById(R.id.weight_of_skin);
		view_of_skin = (TextView)findViewById(R.id.view_of_skin);
		skin_ok_btn = (Button)findViewById(R.id.skin_ok);
		countius_weight_btn = (Button)findViewById(R.id.countius_weight);
		gross_weight = (TextView)findViewById(R.id.gross_weight);
		tv_net_weight = (TextView)findViewById(R.id.tv_net_weight);
		initMaterialView();
		
		initChengliangListView();
	}

	private void initChengliangListView() {
		Log.i(TAG, "initChengliangListView()" );
		simpleAdapter = getSimpleAdapter();

	    listView2 = (ListView)findViewById(R.id.listView2);
	    listView2.setAdapter(simpleAdapter);
	}

	private SimpleAdapter getSimpleAdapter() {
		return new SimpleAdapter(this,
			cailiao ,android.R.layout.simple_expandable_list_item_1,
			new String[]{"tvname"},
			new int[]{android.R.id.text1});
	}


	private void initMaterialView() {

		Log.i(TAG, "initMaterialView()" );
		taskBean = TaskCache.current;
		count = taskBean.getCount();
        setTitle(taskBean);

		if (taskBean.getPeifangming().equals("小油条")) {
			// 小油条配方
			XiaoYouTiao xiaoyoutiao = new XiaoYouTiao();
			List list = xiaoyoutiao.getList();
		} else if (taskBean.getPeifangming().equals("元宵")) {
			
		} else if (taskBean.getPeifangming().equals("韭菜鸡蛋饺子")) {

		} else if (taskBean.getPeifangming().equals("小麻花")) {

		}

		XiaoYouTiao xiaoyoutiao = new XiaoYouTiao();
		listMaterial = xiaoyoutiao.getList();
		initChengliang(listMaterial);
        
		adapter = new FoodAdapter(this, listMaterial);
		adapter.setTotalCount(count);

		listView1 = (ListView)findViewById(R.id.listView1);
		listView1.setAdapter(adapter);
		listView1.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				startQupi(position);
				adapter.setSelection(position);
				adapter.notifyDataSetChanged();
			}
		});
 	}
 
	private void initChengliang(List<Material> list) {
		Log.i(TAG, "initChengliang()" );
		for (Material m:list) {
			cailiao = new ArrayList<Map<String, String>>();
			Log.i(TAG, "create material list, name = " + m.getName());
			materialMap.put(m.getName(), cailiao);
		}
 	}

	protected void beginWeight(int position) {
		Log.i(TAG, "beginWeight()" );
		startWeightHandler(gross_weight);
	}

	// 去皮
	private void startQupi(int position) {
		Log.i(TAG, "startQupi()" );
		currentIndex = position;
		weight_of_skin_layout.setVisibility(View.VISIBLE);
		startWeightHandler(weight_of_skin);
		showCurrentChengzhong();
	}

	private void startWeightHandler(TextView agentView) {
		agentWeightView = agentView;
		isStop = false;
		handler.sendEmptyMessage(GET_WEIGHT);
	}

	private void setTitle(TaskBean bean) {
		String name = bean.getPeifangming();
		TextView title = (TextView)findViewById(R.id.task_name);
		title.setText("称重： " + name + "  " + bean.getCount() + "份");
	}

	public void onClick(View view) {
		if (skin_ok_btn == view) {
			stopWeight();
			weight_of_skin_layout.setVisibility(View.INVISIBLE);
			Log.i(TAG, "skinWeight = " + skinWeight);
			view_of_skin.setText("皮重： " + skinWeight + "克");
			// 开始称量
			agentWeightView = null;
			beginWeight(currentIndex);
			
			cailiao = materialMap.get(listMaterial.get(currentIndex));
			simpleAdapter.notifyDataSetChanged();
		} else if (countius_weight_btn == view) {
			// 添加当前材料的称量记录。
			addCaiLiao();

			// 计算当前材料称量总重量。
			showCurrentChengzhong();
		}
	}

	private void showCurrentChengzhong(){
		String name = listMaterial.get(currentIndex).getName();

		cailiao = materialMap.get(name);

		simpleAdapter = getSimpleAdapter();
		listView2.setAdapter(simpleAdapter);
	}

	private void addCaiLiao() {

		if (netweight < 0.01) {
			Log.i(TAG, "addCaiLiao() net weight is to low.");
			return;
		}

		String name = listMaterial.get(currentIndex).getName();
		Log.i(TAG, "addCaiLiao() " + name);
		cailiao = materialMap.get(name);

		if (cailiao == null) {
			Log.e(TAG, "addCaiLiao() cailiao list is null !!!!" );
			return;
		}

		int times = cailiao.size();
		times ++;
		Map<String, String> map = new HashMap<String, String>();
		map.put("tvname", "第" + times + "次称量： " + netweight + "克");
		map.put("netweight", "" + netweight);

		Log.e(TAG, "第" + times + "次称量： " + netweight + "克" );
		cailiao.add(map);
	}

	private void stopWeight() {
		isStop = true;
	}



	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
 			super.handleMessage(msg);
 			
 			if(msg.what == GET_WEIGHT) {
 				weight = TScale.getInstence().getWeight();
 				try{
 	 				floatWeight = Float.parseFloat(weight);
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 				if (agentWeightView != null) {
 	 				
 	 				if (agentWeightView == weight_of_skin) {
 	 					skinWeight = floatWeight;
 	 	 				agentWeightView.setText("皮重： " + weight + "克");
 	 				} else if (gross_weight == agentWeightView) {
 	 					agentWeightView.setText(weight + "克");
 	 					// 设置净重量
 	 					netweight = floatWeight -  skinWeight;
 	 					tv_net_weight.setText("" + netweight +"克");
 	 				}
 				}

			    ZzLog.i(TAG, "read weight...." + weight);
 				if (!isStop) {
 	 				handler.sendEmptyMessageDelayed(GET_WEIGHT, 300);
 				}
 			}
		}
	};

}
