package com.example.scalefunshow;

import com.example.scalefunshow.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
	}

}
