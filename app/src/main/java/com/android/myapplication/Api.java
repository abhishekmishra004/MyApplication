package com.android.myapplication;

import com.android.myapplication.Model.HomeModel;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Api {

    String Base_url = "https://dev-api.musewearables.com:9000";

    @GET("v1/test/route/animation")
    @Headers("x-api-key: NEEDKEYHERE")
    Call<List<HomeModel>> getData();

    @GET("v1/test/route/animation")
    @Headers("x-api-key: NEEDKEYHERE")
    Call<JsonObject> getjson();
}
