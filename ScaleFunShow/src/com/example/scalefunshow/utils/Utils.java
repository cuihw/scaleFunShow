package com.example.scalefunshow.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.security.MessageDigest;


public class Utils {

	public static void startActivity(Context c, Class<?> cls) {

		Intent intent = new Intent();
		intent.setClass(c, cls);
		c.startActivity(intent);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("InlinedApi")
	public static void hideNavigationBar(Activity activity) {
		int uiFlags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  // hide nav bar
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_FULLSCREEN  // hide status bar
		        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

//		if (android.os.Build.VERSION.SDK_INT >= 19) {
//			uiFlags |= 0x00001000; // SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide
//									// navigation bars - compatibility: building
//									// API level is lower thatn 19, use magic
//									// number directly for higher API target
//									// level
//		} else {
//			uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
//		}

		activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
	}

	public static void hideEditTextIMM(final Context context, final EditText editText) {
		editText.setInputType(InputType.TYPE_NULL);
		editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					InputMethodManager imm = (InputMethodManager)context.getSystemService
						(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
				}
			}
		});
	}


	public static String getDeviceId(Context context ) {
		String deviceid = Settings.Secure.getString(context.getContentResolver(),
			Settings.Secure.ANDROID_ID);
		return deviceid;
	}


	public static String string2MD5(String inStr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = inStr.getBytes();
		byte[] md5Bytes = md5.digest(byteArray);
		return byte2String (md5Bytes);
	}

	public static String byte2String(byte[] data){

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < data.length; i++){
			int val = ((int) data[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
