package com.example.scalefunshow;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by cuihuawei on 2/24/2017.
 */

public class AutoUpdate {

    public static boolean checkApk(Context context,String apk_path ) {

        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(apk_path, PackageManager.GET_ACTIVITIES);
        int version = packageInfo.versionCode;
        ApplicationInfo appInfo = packageInfo.applicationInfo;

        return false;
    }
}
