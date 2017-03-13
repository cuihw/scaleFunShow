package com.example.scalefunshow.tscale;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.tscale.scalelib.jniscale.JNIScale;

import java.util.ArrayList;
import java.util.List;

public class TScale {
	private static final String TAG = "TScale";
	private static TScale instence;
	
	private static JNIScale mScale;

	public static TScale getInstence(){
		if (instence == null ) {
			instence = new TScale();
			mScale = JNIScale.getScale();
		}
		return instence;
	}

	private static final int GET_WEIGHT = 1;

	Handler mHandler ;
	float weight;
	boolean isStop = false;

	public void startWeight(final String preText, final String suffix, final TextView view) {
		mHandler = new Handler();
		isStop = false;
		mHandler.post(new Runnable() {
			@Override
			public void run(){
				weight = getWeight();
				view.setText(preText + weight + suffix);
				if (isStop) {
					mHandler = null;
				} else {
					mHandler.postDelayed(this, 1000);
				}
			}
		});
	}

	public float stopAndGetWeight () {
		isStop = true;
		return weight;
	}

	public static void reset(){
		mScale = JNIScale.getScale();
	}
	
	public float getWeight() {
		TScale.getInstence();
		String temp = null;
		if (mScale != null) {
			temp = mScale.getStringGross();
		}
		if (TextUtils.isEmpty(temp)) {
			return 0;
		}
		try {
			weight = Float.parseFloat(temp);
			weight = weight - floatAdjustWeight.get(0);
		} catch (Exception e) {
			Log.i(TAG, "weight = " + temp);
		}

		return weight;
	}

	List<Float> floatAdjustWeight = new ArrayList<Float>(4);

	public void startAdjustZeroWeight(final TextView view) {
		view.setTag("OK");
		mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run(){
				weight = getWeight();
				floatAdjustWeight.add(0, weight);
				if (equalsItem(floatAdjustWeight)) {
					Log.i(TAG, "completed set zero.");
					view.setText("归零完成：" + 0 + "克");
				} else {
					Log.i(TAG, "progress set zero.");
					String tag = (String) view.getTag();
					if (tag.equals("OK")) {
						view.setText("归零中..  " + weight + "克");
						view.setTag("aaaa");
					} else {
						view.setText("归零中...." + weight + "克");
						view.setTag("OK");
					}
					mHandler.postDelayed(this, 1000);
				}
			}
		}, 500);

	}

	private boolean equalsItem(List<Float> floatWeight) {

		Log.i(TAG, "floatWeight.size() = " + floatWeight.size());

		if (floatWeight.size() > 4) {
			floatWeight.remove(floatWeight.size()-1);
		} else if (floatWeight.size() < 4){
			return false;
		}
		Log.i(TAG, "floatWeight.size() = " + floatWeight.size());

		for (int i = 1; i<floatWeight.size(); i++) {
			if (floatWeight.get(i) != floatWeight.get(i-1)) {
				return false;
			}
		}
		return true;
	}

	enum UnitIndex
    {
        UNIT_KG,  //千克
        UNIT_G,   //克
        UNIT_TJ,  //台斤
        UNIT_GJ,  //港斤
        UNIT_LB,  //磅
        UNIT_OZ,  //盎司
        UNIT_LBOZ,//磅盎司
        UNIT_END
    };
	
	public void setUnit(){
		if (mScale!= null) {
			//int unit = (int)UnitIndex.UNIT_G;
			mScale.setUnit(1); // 设置单位为G
		}
	}
	
	public void zero(){
		if (mScale!= null) {
			mScale.zero(); // 设置单位为G
		}
	}


	public int getMainUnitDeci() {
		
		return mScale.getMainUnitDeci();
	}
	
	public void setMainUnitDeci(int deci) {
		mScale.setMainUnitDeci(deci);
	}
}
