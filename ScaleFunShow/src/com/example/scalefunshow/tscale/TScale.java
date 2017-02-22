package com.example.scalefunshow.tscale;

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
	public static String getWeight() {
 		 TScale.getInstence();
		 String weight = null;
		 if (mScale!= null) {
			 weight = mScale.getStringGross();
		 }
		 return weight;
	}
}
