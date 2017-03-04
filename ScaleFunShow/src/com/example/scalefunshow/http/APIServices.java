package com.example.scalefunshow.http;

import com.example.scalefunshow.bean.FiveAdjustBean;
import com.example.scalefunshow.bean.GetParameter;
import com.example.scalefunshow.bean.LoginBean;
import com.example.scalefunshow.bean.ResponseBean;
import com.example.scalefunshow.bean.TempTaskBean;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by cuihuawei on 3/4/2017.
 */

public interface APIServices {

    @POST("/login")
    Call<ResponseBean> login(@Body LoginBean bean);

    @POST("/getFama")
    Call<ResponseBean> getFama(@Body GetParameter bean);

    @POST("/fiveAdjust")
    Call<ResponseBean> fiveAdjust(@Body FiveAdjustBean bean);

    @POST("/tempTask")
    Call<ResponseBean> tempTask(@Body TempTaskBean bean);

    @POST("/taskDetail")
    Call<ResponseBean> taskDetail(@Body GetParameter bean);


}
