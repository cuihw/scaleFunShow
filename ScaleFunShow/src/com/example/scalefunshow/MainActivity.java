package com.example.scalefunshow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.tscale.scalebuzzerdemo.utils.ScaleBuzzer;
import com.tscale.scaledemo.utils.LCDUtil;
import com.tscale.scalelib.jniscale.JNIScale;
import com.tscale.serialport.SerialUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;  
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
//import android.util.Log; 


@SuppressLint("InlinedApi")
public class MainActivity extends Activity implements OnClickListener {

	private static final String[] arrDeci = { "0", "0.0", "0.00", "0.000",
			"0.0000", "0.00000" };
	private static final String[] arrMode = { "Single", "dual interval",
			"dual range" };
	private static final String[] arrZero = { "0", "2%", "3%", "4%", "10%",
			"20%", "50%" };
	private static final String[] arrDiv = { "1", "2", "5", "10", "20", "50",
			"100"/* ,"500" */};
	private static final String[] arrZtrack = { "OFF", "0.5 d", "1 d", "2 d",
			"4 d" };

	private boolean mZeroFlg;
	private boolean mStabFlg;
	private boolean mTareFlg;

//	LCDUtil lcdUtil = new LCDUtil();
//	SerialUtil serialUtil = new SerialUtil();
//	ScaleBuzzer scaleBuzzer = new ScaleBuzzer(null);
//	private JNIScale mScale = JNIScale.getScale();
	
	LCDUtil lcdUtil;
	SerialUtil serialUtil;
	ScaleBuzzer scaleBuzzer;		
	private JNIScale mScale;	
	
	// private TextView mTvISN = null;
	private TextView mTvWeight = null;
	private Handler mScaleHandle = null;
	private EditText et_calload;
	private EditText et_cap1;
	private EditText et_cap2;
	private TextView tv_weightflg;
	private TextView tv_cap1;
	private TextView tv_cap2;
	private TextView tv_div2;

	private Spinner mSpMode;
	private Spinner mSpDeci;
	private Spinner mSpAzero;
	private Spinner mSpDiv1;
	private Spinner mSpMzero;
	private Spinner mSpDiv2;
	private Spinner mSpZTrack;

	private Button btn_print;
	private Button btn_opencashdraw;
	private Button btn_tare;
	private Button btn_zero;
	private Button btn_default;
	private Button btn_rj45;
	private Button btn_1rs232;
	private Button btn_2rs232;

	private Button btn_cal;
	private CheckBox cb_zero;
	private CheckBox cb_stab;

	private List<String> list;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("InlinedApi")
	public void hideNavigationBar() {
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

		getWindow().getDecorView().setSystemUiVisibility(uiFlags);
	}

	public boolean isNetworkConnected(){  
        ConnectivityManager cm =    (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);  
        NetworkInfo info =cm.getActiveNetworkInfo();  
        if(info!=null&&info.isConnected()){ 
        	//btn_rj45.setText("网络通");
            return true;  
        }else {  
        	//btn_rj45.setText("网络断");
            return false;  
        }  
	}
	
	/** 
     * 检测wifi是否连接 
     *  
     * @return 
     */ 
    private boolean isWifiConnected() {  
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (cm != null) {  
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();  
            if (networkInfo != null 
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {  
                return true;  
            }  
        }  
        return false;  
    }  
   
    /** 
     * 检测3G是否连接 
     *  
     * @return 
     */ 
    private boolean is3gConnected() {  
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (cm != null) {  
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();  
            if (networkInfo != null 
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {  
                return true;  
            }  
        }  
        return false;  
    }  
   
    /** 
     * 检测GPS是否打开 
     *  
     * @return 
     */ 
    private boolean isGpsEnabled() {  
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
        List<String> accessibleProviders = lm.getProviders(true);  
        for (String name : accessibleProviders) {  
            if ("gps".equals(name)) {  
                return true;  
            }  
        }  
        return false;  
    }  

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题
		// hideNavigationBar();
		setContentView(R.layout.activity_main);
		// setStyle(DialogFragment.STYLE_NO_TITLE,
		// R.style.MyTryUseDialogFragment);

		lcdUtil = new LCDUtil();
		serialUtil = new SerialUtil();
		scaleBuzzer = new ScaleBuzzer(null);
//		
		Log.d("JNIScale.getScale()====================", "==start============");
		mScale = JNIScale.getScale();
		Log.d("JNIScale.getScale()====================", "===end===========");		
		
		et_calload = (EditText) findViewById(R.id.et_calload);
		et_cap1 = (EditText) findViewById(R.id.et_cap1);
		et_cap2 = (EditText) findViewById(R.id.et_cap2);		
		tv_weightflg = (TextView) findViewById(R.id.tv_weightflg);
		tv_cap1 = (TextView) findViewById(R.id.tv_cap1);
		tv_cap2 = (TextView) findViewById(R.id.tv_cap2);
		tv_div2 = (TextView) findViewById(R.id.tv_div2);	
					     
		Log.d("JNIScale.getScale()=000================", "==start============");
		mScale.reloadPara();
		initFull();
		Log.d("JNIScale.getScale()=111================", "==start============");
	
		et_cap1.setOnEditorActionListener(new TextView.OnEditorActionListener() {  			             
			@Override  
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
				//当actionId == XX_SEND 或者 XX_DONE时都触发  
				//或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发  
				//注意，这是一定要判断event != null。因为在某些输入法上会返回null。  
				if (actionId == EditorInfo.IME_ACTION_SEND  
				        || actionId == EditorInfo.IME_ACTION_DONE  
				        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
				        && KeyEvent.ACTION_DOWN == event.getAction())) {  
				    //处理事件  
					Log.d("cap1===", ""+et_cap1.getText().toString());
					if (!et_cap1.getText().toString().isEmpty()) {						
						float cap1 = Float.parseFloat(et_cap1.getText().toString());							
						mScale.setMainUnitFull(cap1);
					}
				}
				return false;
			}
		});
		
		et_cap2.setOnEditorActionListener(new TextView.OnEditorActionListener() {  			             
			@Override  
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
				//当actionId == XX_SEND 或者 XX_DONE时都触发  
				//或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发  
				//注意，这是一定要判断event != null。因为在某些输入法上会返回null。  
				if (actionId == EditorInfo.IME_ACTION_SEND  
				        || actionId == EditorInfo.IME_ACTION_DONE  
				        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {  
				    //处理事件  
					Log.d("cap2===", ""+et_cap2.getText().toString());
					if (!et_cap1.getText().toString().isEmpty()) {						
						float cap1 = Float.parseFloat(et_cap2.getText().toString());							
						mScale.setMainUnitMidFull(cap1);
					}
				}  
				return false;  
			}  
		});  
		
//		new NetPing().execute();
		
		mSpMode = (Spinner) findViewById(R.id.sp_mode);
		mSpDeci = (Spinner) findViewById(R.id.sp_deci);
		mSpAzero = (Spinner) findViewById(R.id.sp_azero);
		mSpMzero = (Spinner) findViewById(R.id.sp_mzero);
		mSpDiv1 = (Spinner) findViewById(R.id.sp_div1);
		mSpDiv2 = (Spinner) findViewById(R.id.sp_div2);
		mSpZTrack = (Spinner) findViewById(R.id.sp_ztrack);
		ArrayAdapter<String> ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrMode);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpMode.setAdapter(ada);
		ArrayAdapter<String> ada0 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrDeci);
		ada0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpDeci.setAdapter(ada0);
		ArrayAdapter<String> ada1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrZero);
		ada1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpAzero.setAdapter(ada1);
		mSpMzero.setAdapter(ada1);
		ArrayAdapter<String> ada2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrDiv);
		ada2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpDiv1.setAdapter(ada2);
		mSpDiv2.setAdapter(ada2);
		ArrayAdapter<String> ada3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrZtrack);
		ada3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpZTrack.setAdapter(ada3);

		mSpMode = (Spinner) findViewById(R.id.sp_mode);
		mSpDeci = (Spinner) findViewById(R.id.sp_deci);
		mSpAzero = (Spinner) findViewById(R.id.sp_azero);
		mSpMzero = (Spinner) findViewById(R.id.sp_mzero);
		mSpDiv1 = (Spinner) findViewById(R.id.sp_div1);
		mSpDiv2 = (Spinner) findViewById(R.id.sp_div2);
		mSpMode.setOnItemSelectedListener(new ProvOnItemSelectedListener());
		mSpDeci.setOnItemSelectedListener(new ProvOnItemSelectedListener());
		mSpAzero.setOnItemSelectedListener(new ProvOnItemSelectedListener());
		mSpMzero.setOnItemSelectedListener(new ProvOnItemSelectedListener());
		mSpDiv1.setOnItemSelectedListener(new ProvOnItemSelectedListener());
		mSpDiv2.setOnItemSelectedListener(new ProvOnItemSelectedListener());
		mSpZTrack.setOnItemSelectedListener(new ProvOnItemSelectedListener());

		btn_cal = (Button) findViewById(R.id.btn_cal);
		btn_tare = (Button) findViewById(R.id.btn_tare);
		btn_zero = (Button) findViewById(R.id.btn_zero);
		btn_print = (Button) findViewById(R.id.btn_print);
		btn_default = (Button) findViewById(R.id.btn_default);
		btn_rj45 = (Button) findViewById(R.id.btn_rj45);
		btn_1rs232 = (Button) findViewById(R.id.btn_1rs232);
		btn_2rs232 = (Button) findViewById(R.id.btn_2rs232);
		btn_opencashdraw = (Button) findViewById(R.id.btn_opencashdraw);
		btn_cal.setOnClickListener(this);
		btn_tare.setOnClickListener(this);
		btn_zero.setOnClickListener(this);
		btn_print.setOnClickListener(this);
		btn_default.setOnClickListener(this);
		btn_opencashdraw.setOnClickListener(this);
		btn_rj45.setOnClickListener(this);
		btn_1rs232.setOnClickListener(this);
		btn_2rs232.setOnClickListener(this);

		cb_zero = (CheckBox) findViewById(R.id.cb_zero);
		cb_stab = (CheckBox) findViewById(R.id.cb_stab);
		
		// mTvISN = (TextView) findViewById(R.id.tv_isn);
		mTvWeight = (TextView) findViewById(R.id.tv_weight);
		mScaleHandle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					//mTvISN.setText(String.valueOf(mScale.getInternelCount()));
					mTvWeight.setText(mScale.getStringNet());
					mStabFlg = mScale.getStabFlag();
					mZeroFlg = mScale.getZeroFlag();
					mTareFlg = mScale.getTareFlag();
					cb_zero.setChecked(mZeroFlg);
					cb_stab.setChecked(mStabFlg);
					if (mTareFlg)
						tv_weightflg.setText("Net");
					else
						tv_weightflg.setText("Gross");
					// hideNavigationBar();
					break;
				}
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				mScaleHandle.sendEmptyMessage(1);
			}
		}, 1000, 1);
	}
	
	private void initFull()  
	{
		et_cap1.setText(mScale.getMainUnitFull()+"");
		et_cap2.setText(mScale.getMainUnitMidFull()+"");	
	}	
	
	private int initCombox = 0;
	private boolean initDone = false;
	private class ProvOnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> adapter, View view,
				int position, long id) {
			
			//Log.d("initCombox===", ""+initCombox);
			if(initCombox >= 7)
			{
				initCombox = 10;
				initDone = true;
				scaleBuzzer.Beep();
				Log.d("initDone===", ""+initCombox);
			}
			
			switch (adapter.getId()) {
			case R.id.sp_mode:
				initCombox += 1;Log.d("sp_mode===", ""+initCombox);
				if(initDone)
				{
					if (position > 0) {
						mSpDiv2.setVisibility(View.VISIBLE);
						tv_cap2.setVisibility(View.VISIBLE);
						et_cap2.setVisibility(View.VISIBLE);
						tv_div2.setVisibility(View.VISIBLE);
					} else {
						mSpDiv2.setVisibility(View.GONE);
						tv_cap2.setVisibility(View.GONE);
						et_cap2.setVisibility(View.GONE);
						tv_div2.setVisibility(View.GONE);
					}
					mScale.setRangeMode(position);					
				}
				else
				{
					int tmpMode = mScale.getRangeMode();
					if (tmpMode > 0) {
						mSpDiv2.setVisibility(View.VISIBLE);
						tv_cap2.setVisibility(View.VISIBLE);
						et_cap2.setVisibility(View.VISIBLE);
						tv_div2.setVisibility(View.VISIBLE);
					} else {
						mSpDiv2.setVisibility(View.GONE);
						tv_cap2.setVisibility(View.GONE);
						et_cap2.setVisibility(View.GONE);
						tv_div2.setVisibility(View.GONE);
					}
					adapter.setSelection(tmpMode);
				}
				break;
			case R.id.sp_deci:
				initCombox += 1;Log.d("sp_deci===", ""+initCombox);
				if(initDone)
				{		
					mScale.setMainUnitDeci(position);					
				}
				else
				{
					adapter.setSelection(mScale.getMainUnitDeci());
				}
				break;
			case R.id.sp_azero:
				initCombox += 1;Log.d("sp_azero===", ""+initCombox);
				if(initDone)
				{
					mScale.setAutoZeroRangePtr(position);	
				}
				else
				{
					adapter.setSelection(mScale.getAutoZeroRangePtr());
				}
				
				break;
			case R.id.sp_mzero:
				initCombox += 1;Log.d("sp_mzero===", ""+initCombox);
				if(initDone)
				{
					mScale.setManualZeroRangePtr(position);	
				}
				else
				{
					adapter.setSelection(mScale.getManualZeroRangePtr());	
				}				
				break;
			case R.id.sp_div1:	
				initCombox += 1;Log.d("sp_div1===", ""+initCombox);
				if(initDone)
				{
					mScale.setFdnPtr(position);	
				}
				else
				{
					adapter.setSelection(mScale.getFdnPtr());	
					// mScale.setFdnPtr(Integer.valueOf(arrDiv[position]).intValue());	
				}				
				
				break;
			case R.id.sp_div2:
				initCombox += 1;Log.d("sp_div2===", ""+initCombox);				
				if(initDone)
				{
					mScale.setBigFdnPtr(position);
				}
				else
				{
					adapter.setSelection(mScale.getBigFdnPtr());	
				}				
				// mScale.setBigFdnPtr(Integer.valueOf(arrDiv[position]).intValue());
				break;
			case R.id.sp_ztrack:
				initCombox += 1;Log.d("sp_ztrack===", ""+initCombox);						
				if(initDone)
				{
					mScale.setZeroTrackPtr(position);
				}
				else
				{
					adapter.setSelection(mScale.getZeroTrackPtr());					
				}								
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			String sInfo = "什么也没选！";
			// Toast.makeText(getApplicationContext(),sInfo,
			// Toast.LENGTH_LONG).show();
		}
	}

	private int step = 0;
	private int zeroISN = 0;
	private int calload = 0;

	public void onClick(View v) {
		scaleBuzzer.Beep();
		switch (v.getId()) {
		case R.id.btn_cal:
			if (step == 0) {
				((Button) (v)).setText("标定零点");
				step++;
			} else if (step == 1) {
				if (et_calload.getText().toString().isEmpty()) {
					step = 0;
					((Button) (v)).setText("标定");
					break;
				}
				calload = Integer.valueOf(et_calload.getText().toString())
						.intValue();
				zeroISN = mScale.getInternelCount();
				((Button) (v)).setText("标定重量" + calload + "kg");
				step++;
			} else if (step == 2) {
				int adload = mScale.getInternelCount();
				mScale.saveNormalCalibration(zeroISN, adload, (float) calload);
				((Button) (v)).setText("标定");
				step = 0;
			}
			break;
		case R.id.btn_tare:
			mScale.tare();
			break;
		case R.id.btn_zero:
			mScale.zero();
			break;
		case R.id.btn_print:
			printTest();
			break;
		case R.id.btn_opencashdraw:
			scaleBuzzer.OpenCashbox();
			break;
		case R.id.btn_default:
			//resetScale();
			break;
		case R.id.btn_rj45:
		{
			Log.d("scale->desc=======", ""+mScale.getMainUnitDeci());			
			
			String state = "";
			if(isNetworkConnected())state += "网络通";
			else                    state += "网络断";
			if(isWifiConnected()) state+="_Wifi";     
		    if(is3gConnected()) state+="_3G";
		    if(isGpsEnabled()) state+="_GPS";
		    
		    btn_rj45.setText(state);
		}	
			break;
		case R.id.btn_1rs232:
			try {
				if(serialUtil.TestSerial1()){
					btn_1rs232.setText("Com1收发正常");  
				}else{
					btn_1rs232.setText("Com1收发异常");  
				}
			} catch (Exception e) {				
				e.printStackTrace();
			}
			break;
		case R.id.btn_2rs232:
			try {
				if(serialUtil.TestSerial2()){ 
					btn_2rs232.setText("Com2收发正常");
				}else{
					btn_2rs232.setText("Com2收发异常");
				}
			} catch (Exception e) {				
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	private void printTest() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serialUtil.printString("这是中文");
					serialUtil.printCode("这是中文zhe shi yingwen", 250, 250, 100,
							SerialUtil.BARCODETYPE_QRCODE);
					serialUtil.printString("zhe shi yingwen");
					serialUtil.printString("");
					serialUtil.printString("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void resetScale() {
		mScale.setMainUnitDeci(2);
		mScale.setAutoZeroRangePtr(4);
		mScale.setManualZeroRangePtr(1);
		mScale.setFdnPtr(1);
		mScale.setBigFdnPtr(2);
		mScale.setRangeMode(0);
	}

	
	/*
	public String Ping(String str) {
		String resault = "";
		Process p;
		try {
			//ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
			p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + str);
			int status = p.waitFor();
			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null){
			buffer.append(line);
			}
			System.out.println("Return ============" + buffer.toString());
			if (status == 0) {
			resault = "success";
			} else {
			resault = "faild";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return resault;
	}
	
	private class NetPing extends AsyncTask<String, String, String> {
		protected String doInBackground(String... params) {
			String s = "";
			s = Ping("10.5.52.1");
			Log.i("ping-------+++++++----", s);
			return s;
		}
	}
*/
	
	
	
	// private void printBarcode() {
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// try {
	// serialUtil.printCodeText("567.89kg", 384, 150, 0,
	// SerialUtil.BARCODETYPE_CODE_128, 20);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }).start();
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
//		mScale.setScalePause(1);
		super.onPause();
	}

	@Override
	protected void onResume() {
//		mScale.setScalePause(0);
		super.onResume();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
