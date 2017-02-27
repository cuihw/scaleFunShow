package com.example.scalefunshow.tscale;

/**
 * Created by cuihuawei on 2/27/2017.
 */

public class TScaleJni {
    private static TScaleJni mScale = null;

    static {
        System.loadLibrary("jniscale");
    }

    public static TScaleJni getScale() {
        if(mScale == null) {
            mScale = new TScaleJni();
        }

        return mScale;
    }

    private TScaleJni() {
        this.createScale();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.deleteScale();
    }

    private native void createScale();

    public native void deleteScale();

    public native String getStringNet();

    public native String getStringTare();

    public native String getStringGross();

    public native float getFloatNet();

    public native float getFloatTare();

    public native float getFloatGross();

    public native boolean getStabFlag();

    public native boolean getTareFlag();

    public native boolean getZeroFlag();

    public native boolean tare();

    public native boolean zero();

    public native boolean pretare(float var1);

    public native int getInternelCount();

    public native int getUnit();

    public native String getStringUnit();

    public native boolean setUnit(int var1);

    public native boolean setStringUnit(String var1);

    public native int getFdnPtr();

    public native int getBigFdnPtr();

    public native int getMainUnitDeci();

    public native int getMainUnit();

    public native float getMainUnitFull();

    public native float getMainUnitMidFull();

    public native boolean setFdnPtr(int var1);

    public native boolean setBigFdnPtr(int var1);

    public native boolean setMainUnitDeci(int var1);

    public native boolean setMainUnit(int var1);

    public native boolean setMainUnitFull(float var1);

    public native boolean setMainUnitMidFull(float var1);

    public native boolean saveNormalCalibration(int var1, int var2, float var3);

    public native boolean saveLinearCalibration(int var1, int[] var2, float var3);

    public native int getAutoZeroRangePtr();

    public native boolean setAutoZeroRangePtr(int var1);

    public native boolean setManualZeroRangePtr(int var1);

    public native int getManualZeroRangePtr();

    public native boolean setZeroTrackPtr(int var1);

    public native int getZeroTrackPtr();

    public native boolean setRangeMode(int var1);

    public native boolean setTareMode(int var1);

    public native boolean setApproval(int var1);

    public native int approval();

    public native int smallFdn();

    public native int bigFdn();

    public native int currFdn();

    public native void setScalePause(int var1);

    public native void reloadPara();

    public native int getRangeMode();
}
