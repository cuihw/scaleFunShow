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
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

    private static final String TAG = "Login";
    EditText person_id;
    EditText password;

    EditText currentEdit;

    String inputNumbers;

    Button login;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button0;
    Button button_ok;
    Button button_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_login);
        person_id = (EditText) findViewById(R.id.person_id);
        password = (EditText) findViewById(R.id.password);

        login = (Button)findViewById(R.id.login);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        button0 = (Button)findViewById(R.id.button0);
        button_ok = (Button)findViewById(R.id.button_ok);
        button_delete = (Button)findViewById(R.id.button_delete);


        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(person_id.getWindowToken(),0);
        imm.hideSoftInputFromWindow(password.getWindowToken(),0);
        person_id.setInputType(InputType.TYPE_NULL);
        person_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService
                        (Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(person_id.getWindowToken(),0);
                }
            }
        });
        password.setInputType(InputType.TYPE_NULL);
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService
                        (Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(password.getWindowToken(),0);
                }
            }
        });
        Log.i(TAG, "onCreate...");
        //playFinish(this);

        person_id.setOnFocusChangeListener(listener);
        password.setOnFocusChangeListener(listener);
    }

    View.OnFocusChangeListener listener = new View.OnFocusChangeListener(){

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v == person_id && hasFocus) {
                currentEdit = person_id;
            } else if (v == password && hasFocus) {
                currentEdit = password;
            } else {
                currentEdit = null;
            }

        }
    };


    @Override
    protected void onResume() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onResume();
    }

    public void onClick(View view) {
        if (view == login) {
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
    }

    public void onClickNumber(View view) {
        if (currentEdit == null)  {
            return;
        }
        //http://blog.csdn.net/zhuyb829/article/details/49178563
        Editable editable = currentEdit.getText();
        int start = currentEdit.getSelectionStart();
        if (start < 0) start = 0;
        switch (view.getId()) {
            case R.id.button0:
                editable.insert(start, "0");
                break;
            case R.id.button1:
                editable.insert(start, "1");
                break;
            case R.id.button2:
                editable.insert(start, "2");
                break;
            case R.id.button3:
                editable.insert(start, "3");
                break;
            case R.id.button4:
                editable.insert(start, "4");
                break;
            case R.id.button5:
                editable.insert(start, "5");
                break;
            case R.id.button6:
                editable.insert(start, "6");
                break;
            case R.id.button7:
                editable.insert(start, "7");
                break;
            case R.id.button8:
                editable.insert(start, "8");
                break;
            case R.id.button9:
                editable.insert(start, "9");
                break;
            case R.id.button_ok:
                if (currentEdit == person_id) {
                    password.requestFocus();
                }
                break;
            case R.id.button_delete:
                if (start > 0) editable.delete(start - 1, start);
                break;
        }
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


