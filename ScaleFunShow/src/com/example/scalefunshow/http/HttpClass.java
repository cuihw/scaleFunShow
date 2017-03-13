package com.example.scalefunshow.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

/**
 */

public class HttpClass {
    private static final String TAG = "HttpClass";

    public static final String REQUEST_ADDRESS = "http://192.168.22.242:7080";

    public interface RequestListener {
        public void onResponse(String response);
    }

    public static void startRequest(final String path,
         final String parameter,  final RequestListener listener) {

//        Thread requestThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String response = post(path, parameter);
//                if (listener != null) {
//                    listener.onResponse(response);
//                }
//            }
//        });
//        requestThread.start();
//        new RequestTask(listener).executeOnExecutor(THREAD_POOL_EXECUTOR,
//            path, parameter);
        new RequestTask(listener).execute(path, parameter);
    }


    private static class RequestTask extends AsyncTask<String, Integer, String> {
        RequestListener listener;

        public RequestTask (RequestListener listener) {
            this.listener = listener;
        }
        @Override
        protected String doInBackground(String... params) {

            String path = null;
            String parameter = null;
            if (params != null && params.length > 1) {
                path = params[0];
                parameter = params[1];
            }

            Log.i(TAG, "request path = " + path);
            Log.i(TAG, "request parameter = " + parameter);
            String response = post(path, parameter);
            Log.i(TAG, "result = " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (listener != null) {
                listener.onResponse(s);
            }
        }
    }



    protected static String post(String path, String parameter) {
        BufferedReader in = null;
        String result = "";
        OutputStream os = null;

        URL url = null;
        try {
            url = new URL(path);
            Log.i(TAG, "parameter = " + parameter);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            os = conn.getOutputStream();
            os.write(parameter.getBytes());
            os.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            if (conn.getResponseCode() == 200) {
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
