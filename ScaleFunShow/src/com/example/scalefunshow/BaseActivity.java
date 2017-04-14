package com.example.scalefunshow;

import com.example.scalefunshow.tscale.TScale;
import com.example.scalefunshow.tscale.TScale.ZeroAdjustListener;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG,  "keyCode = " + keyCode + ", event = " + event);
        
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

            TScale.getInstence().startAdjustZeroWeight(null, new ZeroAdjustListener(){
                @Override
                public void onFinish() {
                    Toast.makeText(BaseActivity.this, "归零完成。", Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
        
        
        return super.onKeyDown(keyCode, event);
    }
 
}
