package com.example.luneblazetask.RetrofitApi;


import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public  interface ApiService {

    @FormUrlEncoded
    @POST("react.json")
    Call<JsonObject> getResponse(@Field("user_id") String user_id,@Field("type") String type,@Field("content_id") String content_id,@Field("reaction") String reaction,@Field("user_type") String user_type);

}