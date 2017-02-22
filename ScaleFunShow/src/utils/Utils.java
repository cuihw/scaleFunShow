package utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;


public class Utils {

	public static void startActivity(Context c, Class<?> cls) {

		Intent intent = new Intent();
		intent.setClass(c, cls);
		c.startActivity(intent);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("InlinedApi")
	public static void hideNavigationBar(Activity activity) {
		int uiFlags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | // hide nav bar
				View.SYSTEM_UI_FLAG_FULLSCREEN | // hide status bar
				View.SYSTEM_UI_FLAG_IMMERSIVE;

		if (android.os.Build.VERSION.SDK_INT >= 19) {
			uiFlags |= 0x00001000; // SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide
									// navigation bars - compatibility: building
									// API level is lower thatn 19, use magic
									// number directly for higher API target
									// level
		} else {
			uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
		}

		activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
	}
}
