package com.example.scalefunshow.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ZzLog {

    private static final String TAG = "Mylog";

    private static boolean outFlag = true;

    private static String filename = "/log/log.txt";

    private static String logFlagFilename = "/log/chris/logflag";

    private static String logPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    private static boolean logToSdFlag = false;

    public static void init() {
        String logflagfile = logPath + logFlagFilename;
        Log.i(TAG, "init() logflagfile = " + logflagfile);
        File file = new File(logflagfile);
        if (file.exists()) {
            logToSdFlag = true;
        }
    }

    public static void i(String tag, String msg) {
        out(tag, msg);
    }

    public static void out(String tag, String msg) {
        if (outFlag) {
            Log.i(tag, msg);
        }
        if (logToSdFlag) {
            if (Environment.getExternalStorageState().equals("mounted")) {
                try {
                    writeToSdCard(logPath + filename, tag, msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    public static void eOut(Exception e) {
        StackTraceElement[] ste = e.getStackTrace();
        for (StackTraceElement element :ste) {
            try {
                writeToSdCard (logPath + filename,  null, element.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void writeToSdCard(String filepath, String tag, String msg) throws IOException {
        File file = new File(filepath);
        int size_1Mb = 1 * 1024 * 1024;
        if (!file.exists()) {
            file.createNewFile();
        } else if (file.length() > size_1Mb) {
            file = cutFile(file);
        }
        

        FileOutputStream fos = new FileOutputStream(file, true);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bfw = new BufferedWriter(osw);
        String data = getNowDateTime() + ": " + tag + ", " + msg;
        bfw.write(data);
        bfw.flush();
        bfw.close();
    }

    private static String getNowDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = new Date();
        return sdf.format(date);
    }

    private static File cutFile(File file) {
        String path = file.getAbsolutePath();
        String parent = file.getParent();
        
        File file_name = new File(parent + "/log0.txt");
        Log.i(TAG, "path = " + path + ", parent = " + parent + ", file_name = " + file_name.getAbsolutePath());
        if (file_name.exists()) {
            file_name.delete();
        }
        file.renameTo(file_name);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}

