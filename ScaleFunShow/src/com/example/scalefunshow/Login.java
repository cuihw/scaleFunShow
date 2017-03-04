package com.example.scalefunshow;

import com.example.scalefunshow.bean.LoginBean;
import com.example.scalefunshow.bean.ResponseBean;
import com.example.scalefunshow.http.HttpClass;
import com.example.scalefunshow.utils.Utils;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
        person_id = (EditText)findViewById(R.id.person_id);
        password = (EditText)findViewById(R.id.password);

	}

	public void onClick(View view) {

		String id = person_id.getText().toString();
		String pwd = password.getText().toString();
		if ("999999".equals(id) && "123456".equals(pwd)) {
			Intent intent = new Intent();
			intent.setClass(this, TaskActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent();
			intent.setClass(this, AdjustActivity.class);
			startActivity(intent);
			finish();
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
		HttpClass.startRequest(HttpClass.REQUEST_ADDRESS,param, new HttpClass.RequestListener(){
			@Override
			public void onResponse(String response) {
				if (TextUtils.isEmpty(response)) {
					Log.i(TAG, "response = null");
				} else {
					parserResponse(response);
				}
			}
		});

	}

	private void parserResponse(String response) {
		Gson gson = new Gson();
		ResponseBean responseBean = gson.fromJson(response, ResponseBean.class);
		int status = responseBean.getStatus();
		if (status == 0) {
			Log.i(TAG, "登录成功。");
		}
	}
	
}
