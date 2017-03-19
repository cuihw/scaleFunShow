package com.example.scalefunshow;

import com.example.scalefunshow.bean.LoginBean;
import com.example.scalefunshow.bean.PersonBean;
import com.example.scalefunshow.bean.ResponseBean;
import com.example.scalefunshow.http.HttpClass;
import com.example.scalefunshow.utils.Utils;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Login extends Activity {

    private static final String TAG = "Login";
    EditText person_id;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_login);
        person_id = (EditText) findViewById(R.id.person_id);
        password = (EditText) findViewById(R.id.password);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(person_id.getWindowToken(),0);
        imm.hideSoftInputFromWindow(password.getWindowToken(),0);
        Log.i(TAG, "onCreate...");
        playFinish(this);
    }

    public static void playFinish(Context contex) {
        MediaPlayer mp1 = MediaPlayer.create(contex, R.raw.finished);
        mp1.start();

        mp1.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
            }
        });
    }

    @Override
    protected void onResume() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onResume();
    }

    public void onClick(View view) {

        String id = person_id.getText().toString();
        String pwd = password.getText().toString();
        if ("999999".equals(id) && "123456".equals(pwd)) {
            Intent intent = new Intent();
            intent.setClass(this, TaskActivity.class);
            startActivity(intent);
         } else {
            Intent intent = new Intent();
            intent.setClass(this, AdjustActivity.class);
            startActivity(intent);
        }
        LoginBean bean = new LoginBean();
        bean.setUserid(id);
        bean.setPassword(Utils.string2MD5(pwd));
        bean.setDeviceid(Utils.getDeviceId(Login.this));
        login(bean);
    }

    
    private void login(LoginBean bean) {
        Gson gson = new Gson();
        String param = gson.toJson(bean);
        HttpClass.startRequest(HttpClass.REQUEST_ADDRESS, param,
                new HttpClass.RequestListener() {
                    @Override
                    public void onResponse(String response) {

                        if (TextUtils.isEmpty(response)) {
                            Log.i(TAG, "response = null");
                            LoadLocalPerson();
                        } else {
                            parserResponse(response);
                        }
                     }
                });
    }

    protected void LoadLocalPerson() {
        PersonBean person = new PersonBean();
        person.setC_ryxm("张强");
    }
    
    private void parserResponse(String response) {
        Gson gson = new Gson();
        ResponseBean responseBean = null;
        try {
            responseBean = gson.fromJson(response, ResponseBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (responseBean != null) {
            int status = responseBean.getStatus();
            if (status == 0) {
                Log.i(TAG, "登录成功。");
            }
        }
    }

}


