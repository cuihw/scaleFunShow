package com.example.scalefunshow.tscale;



import android.os.Handler;

import com.tscale.scalelib.jniscale.JNIScale;

public class TScale {
	private static TScale instence;
	
	private static JNIScale mScale;
	
	
	public static TScale getInstence(){
		if (instence == null ) {
			instence = new TScale();
			mScale = JNIScale.getScale();
		}
		return instence;
	}
	
	public static void reset(){
		mScale = JNIScale.getScale();
	}
	
	public String getWeight() {
 		 TScale.getInstence();
		 String weight = null;
		 if (mScale!= null) {
			 weight = mScale.getStringGross();
		 }
		 return weight;
	}

    enum UnitIndex
    {
        UNIT_KG,  //千克
        UNIT_G,   //克
        UNIT_TJ,  //台斤
        UNIT_GJ,  //港斤
        UNIT_LB,  //磅
        UNIT_OZ,  //盎司
        UNIT_LBOZ,//磅盎司
        UNIT_END
    };
	
	public void setUnit(){
		if (mScale!= null) {
			//int unit = (int)UnitIndex.UNIT_G;
			mScale.setUnit(1); // 设置单位为G
		}
	}
	
	public void zero(){
		if (mScale!= null) {
			mScale.zero(); // 设置单位为G
		}
	}


	public int getMainUnitDeci() {
		
		return mScale.getMainUnitDeci();
	}
	
	public void setMainUnitDeci(int deci) {
		mScale.setMainUnitDeci(deci);
	}
}
