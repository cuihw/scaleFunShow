package com.example.scalefunshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scalefunshow.tscale.TScale;
import com.example.scalefunshow.tscale.TScale.ZeroAdjustListener;
import com.example.scalefunshow.utils.Utils;
import com.example.scalefunshow.utils.ZzLog;

import java.util.ArrayList;
import java.util.List;

public class AdjustActivity extends Activity {
    // 五点校准界面。

    private static final int GET_WEIGHT = 1;
    Button confirm;

    Button currentButton;

    private boolean setOk;
    TextView textview_weight;
    TextView hint;

    static float zeroAdjustWeight = 0;

    float famaZhiliang = 0; // 使用的砝码重量

    ImageView adjust_imageview;
    AnimationDrawable animationDrawable;
    int adjustPoint;

    List<Float> adjustPointList = new ArrayList<Float>();

    private static boolean IS_ALREADY_ADJUST = false;
    private static final String TAG = "AdjustActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ZzLog.i(TAG, "onCreate()....");
        Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_adjust);
        confirm = (Button) findViewById(R.id.confirm);
        adjust_imageview  = (ImageView) findViewById(R.id.adjust_imageview);

        textview_weight = (TextView) findViewById(R.id.textview_weight);
        hint = (TextView) findViewById(R.id.hint);

        if (IS_ALREADY_ADJUST) {
            // goto the task activity.
        } else {
            Toast.makeText(this, "正在归零中，请稍后.....", Toast.LENGTH_SHORT).show();
            TScale.getInstence().zero();
            Log.i(TAG, "textview_weight");
            // 开始校准......
            TScale.getInstence().startAdjustZeroWeight(textview_weight, new ZeroAdjustListener(){
                @Override
                public void onFinish() {
                    Toast.makeText(AdjustActivity.this, "归零完成。", Toast.LENGTH_SHORT).show();
                    zeroAdjustWeight = TScale.getInstence().getAdjustZeroWeight();
                    beginAdjust();
                }
            });

        }


    }

    private void beginAdjust() {
        int point = 1;
        showHintDialog(point);
    }

    private void showHintDialog(final int point) {
        // 
        new AlertDialog.Builder(this)
            .setTitle("开始校验")
            .setMessage("请把砝码放在位置" + point + " 开始校验" + point + "号位置。")
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startAdjustPoint(point);
                }
            }).show();
    }

    private void startAdjustPoint(int point) {
        adjustPoint = point;
        hint.setText("目前正在校验的点位是：" + point + "号位");
        playAnmi(point, adjust_imageview);
        adjustPointList.clear();
        setOk = false;
        handler.sendEmptyMessage(GET_WEIGHT);
    }

    private void playAnmi(int point, ImageView imageView) {
        int resid = R.drawable.adjust_1;
        if (point == 1) {
            resid = R.drawable.adjust_1;
        } else if (point == 2) {
            resid = R.drawable.adjust_2;
        } else if (point == 3) {
            resid = R.drawable.adjust_3;
        } else if (point == 4) {
            resid = R.drawable.adjust_4;
        } else if (point == 5) {
            resid = R.drawable.adjust_5;
        }
        
        animationDrawable = (AnimationDrawable) this.getResources()
            .getDrawable(resid);
        imageView.setImageDrawable(animationDrawable);
        animationDrawable.start();
    }
 
    public void onClick(View view) {
        if (confirm == view) {
            startTask();
        }
    }

    private void startTask() {
        ZzLog.i(TAG, "startTask");
        Intent intent = new Intent();
        intent.setClass(this, TaskChooseActivity.class);
        startActivity(intent);
        finish();
    }
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == GET_WEIGHT) {
                float weight = TScale.getInstence().getWeight();
                weight = weight - zeroAdjustWeight;
                adjustPointList.add(weight);

                boolean isfinished = isAdjustFinished(adjustPointList);
                if (isfinished) {
                    setCurrentPointOk();
                    return;
                }
                
                if (textview_weight.getText().toString().contains("......")) {
                    textview_weight.setText("校准中...   " + weight);
                } else {
                    textview_weight.setText("校准中......" + weight);
                }

                ZzLog.i(TAG, "read weight...." + weight);
                if (!setOk) {
                    handler.sendEmptyMessageDelayed(GET_WEIGHT, 400);
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        // stop handler message
        setOk = true;
    }

    protected void setCurrentPointOk() {
        setOk = true;
        animationDrawable.stop();
        if (adjustPoint == 1) {
            adjust_imageview.setImageResource(R.drawable.adjust_1_2);
        } else if (adjustPoint == 2){
            adjust_imageview.setImageResource(R.drawable.adjust_2_2);
        } else if (adjustPoint == 3){
            adjust_imageview.setImageResource(R.drawable.adjust_3_2);
        } else if (adjustPoint == 4){
            adjust_imageview.setImageResource(R.drawable.adjust_4_2);
        } else if (adjustPoint == 5){
            adjust_imageview.setImageResource(R.drawable.adjust_5_2);
        }

        if (adjustPoint == 5) {
            confirm.setVisibility(View.VISIBLE);
            IS_ALREADY_ADJUST = true;
        } else {
            adjustPoint ++;
            showHintDialog(adjustPoint);
        }
    }

    protected boolean isAdjustFinished(List<Float> adjustPointList2) {
        if (adjustPointList2.size() < 20) {
            return false;
        }
        int size = adjustPointList2.size();
        for (int i = (size - 20); i < size; i++) {
            float delta = adjustPointList2.get(i) - famaZhiliang;
            Log.i(TAG, "delta = " + delta);
            if (Math.abs(delta) > 0.5){
                return false;
            }
        }
        return true;
    }
}
