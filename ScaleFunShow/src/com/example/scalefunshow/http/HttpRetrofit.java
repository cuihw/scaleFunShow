package com.example.scalefunshow.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.scalefunshow.bean.LoginBean;
import com.example.scalefunshow.bean.ResponseBean;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by cuihuawei on 3/4/2017.
 */

public class HttpRetrofit {

    String baseUri = "http://192.168.22.242:7080/rsafinger/";

    public void login(LoginBean bean) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUri)
            .addConverterFactory(GsonConverterFactory.create())
        .build();

        APIServices apiService = retrofit.create(APIServices.class);

        Call<ResponseBean> call = apiService.login(bean);

        call.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(retrofit.Response<ResponseBean> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

    }


    private Context mContext;
    public static HttpRetrofit httpRetrofit;
    private int cacheSize = 10 * 1024 * 1024; // 10 MB
    private Cache cache;
    private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR;

    private File httpCacheDirectory;
    private OkHttpClient client;

    private HttpRetrofit(Context context) {
        this.mContext = context;
        initOkHttpCilent();
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void initOkHttpCilent() {
        if (client == null) {
            client = new OkHttpClient();
            httpCacheDirectory = new File(mContext.getCacheDir(), "httpCache");
            cache = new Cache(httpCacheDirectory, cacheSize);
            REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    if (isNetWorkAvailable(mContext)) {
                        int maxAge = 60; // 在线缓存在1分钟内可读取
                        return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                    } else {
                        int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                        return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale="
                                + maxStale)
                            .build();
                    }
                }
            };
            client.setCache(cache);
            client.interceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        }
    }

    public static HttpRetrofit getInstance(Context context) {
        if (httpRetrofit == null) {
            synchronized (HttpRetrofit.class) {
                if (httpRetrofit == null) {
                    httpRetrofit = new HttpRetrofit(context);
                }
            }
        }
        return httpRetrofit;
    }

    APIServices apiService;

    private Object monitor = new Object();

    public APIServices getApiService() {
        if (apiService == null) {
            synchronized (monitor) {
                if (apiService == null) {
                    apiService = new Retrofit.Builder()
                        .baseUrl(baseUri)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(APIServices.class);
                }
            }
        }
        return apiService;
    }


}
