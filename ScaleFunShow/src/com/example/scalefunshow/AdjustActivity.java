package com.example.scalefunshow;

import com.example.scalefunshow.utils.Utils;

import com.example.scalefunshow.tscale.TScale;
import com.example.scalefunshow.utils.ZzLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdjustActivity  extends Activity{
	// 五点校准界面。

	private static final int GET_WEIGHT = 1;
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button confirm;
	
	Button currentButton;
	
	Button ok_button;
	Button zero;
	
	private boolean setOk;
	TextView textview_weight;
	
	private static final boolean IS_ALREADY_ADJUST = true;
	private static final String TAG = "AdjustActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ZzLog.i(TAG, "onCreate()....");
		Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_adjust);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        confirm = (Button)findViewById(R.id.confirm);
        ok_button = (Button)findViewById(R.id.ok_button);
        zero = (Button)findViewById(R.id.zero);
        textview_weight = (TextView)findViewById(R.id.textview_weight);
        // TScale.getInstence().setUnit();
        // TScale.getInstence().setMainUnitDeci(4);
        // TScale.getInstence().reset();
	}

	public void onClick(View view){
		if (view == button1 || view == button2 || view == button3
				|| view == button4 || view == button5){
			//校准
			adjust((Button)view);
		}

		if (ok_button == view) {
			setOk = true;
			ZzLog.i(TAG, "setok....");
			if (currentButton != null) {

				ZzLog.i(TAG, "currentButton...." + currentButton.getText().toString());
				currentButton.setTag(IS_ALREADY_ADJUST);
				currentButton.setText("校准完成");
			}

			Object obj1 = button1.getTag();
			Object obj2 = button2.getTag();
			Object obj3 = button3.getTag();
			Object obj4 = button4.getTag();
			Object obj5 = button5.getTag();
			if (obj1!= null && obj2!= null &&
					obj3!= null &&obj4!= null &&obj5!= null) {
				boolean isAlearyAdjust1 = (Boolean)obj1;
				boolean isAlearyAdjust2 = (Boolean)obj2;
				boolean isAlearyAdjust3 = (Boolean)obj3;
				boolean isAlearyAdjust4 = (Boolean)obj4;
				boolean isAlearyAdjust5 = (Boolean)obj5;
				
				if (isAlearyAdjust1 && isAlearyAdjust2 && isAlearyAdjust3
						&&isAlearyAdjust4 && isAlearyAdjust5) {
					confirm.setVisibility(View.VISIBLE);
				}
			}
		}
		
		if (confirm == view) {
			startTask();
		} else if (zero == view) {
			TScale.getInstence().zero();
		}
		
	}

	private void startTask() {
		ZzLog.i(TAG, "startTask");
        Intent intent = new Intent();
		intent.setClass(this, TaskChooseActivity.class);
		startActivity(intent);
		finish();
	}

	private void adjust(Button button) {
		currentButton = button;
		button.setText("校准中......");
		// 如果其他的点也显示校准中，回复为原来的未校准状态。
		setOthersButton(button);
		setOk = false;
		handler.sendEmptyMessage(GET_WEIGHT);
	}


	private void setOthersButton(Button button) {
		

		if (button1 != button) {

			Object obj = button1.getTag();
			if (obj != null) {
				boolean isAlearyAdjust = (Boolean)obj;
				if (!isAlearyAdjust) {
					button1.setText("校准左上角");
				} else {
					button1.setText("校准完成");
				}
			} else {
				button1.setText("校准左上角");
			}
		}

		if (button2 != button) {

			Object obj = button2.getTag();
			if (obj != null) {
				boolean isAlearyAdjust = (Boolean)obj;
				if (!isAlearyAdjust) {
					button2.setText("校准右上角");
				} else {
					button2.setText("校准完成");
				}
			} else {
				button2.setText("校准右上角");
			}
		}

		if (button3 != button) {
			Object obj = button3.getTag();
			if (obj != null) {
				boolean isAlearyAdjust = (Boolean)button3.getTag();
				if (!isAlearyAdjust) {
					button3.setText("校准中间");
				} else {
					button3.setText("校准完成");
				}
			} else {
				button3.setText("校准中间");
			}
		}

		if (button4 != button) {
			Object obj = button4.getTag();
			if (obj != null) {			
				boolean isAlearyAdjust = (Boolean)button4.getTag();
				if (!isAlearyAdjust) {
					button4.setText("校准左下角");
				} else {
					button4.setText("校准完成");
				}
			} else {
				button4.setText("校准左下角");
			}

		}

		if (button5 != button) {

			Object obj = button5.getTag();
			if (obj != null) {
				boolean isAlearyAdjust = (Boolean)button5.getTag();
				if (!isAlearyAdjust) {
					button5.setText("校准右下角");
				} else {
					button5.setText("校准完成");
				}
			}else {
				button5.setText("校准右下角");
			}
		}
	}

	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
 			super.handleMessage(msg);
 			
 			if(msg.what == GET_WEIGHT) {
 				String weight = TScale.getInstence().getWeight();
 				int deci = TScale.getInstence().getMainUnitDeci();
 				textview_weight.setText(weight);

			    ZzLog.i(TAG, "read weight...." + weight + ", deci = " + deci);
 				if (!setOk) {
 	 				handler.sendEmptyMessageDelayed(GET_WEIGHT, 1000);
 				}
 			}
		}
	};

	
	
}
