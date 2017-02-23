package com.example.scalefunshow;

import com.example.scalefunshow.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {

	
	EditText person_id;
	EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Utils.hideNavigationBar(this);
        setContentView(R.layout.activity_login);
        
        person_id = (EditText)findViewById(R.id.person_id);
        password = (EditText)findViewById(R.id.password);
	}

	public void onClick(View view) {
		Intent intent = new Intent();
		intent.setClass(this, AdjustActivity.class);
		startActivity(intent);
		finish();
//		String id = person_id.getText().toString();
//		String pwd = password.getText().toString();
//		if ("999999".equals(id) && "123456".equals(pwd)) {
//			Intent intent = new Intent();
//			intent.setClass(this, AdjustActivity.class);
//			startActivity(intent);
//			finish();
//		} else {
//			Toast.makeText(this, "工号不正确或者是密码错误，请重新输入。", Toast.LENGTH_LONG).show();
//		}
	}

}
