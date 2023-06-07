package com.example.wink_android.api;

import com.example.wink_android.requests.LoginRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;


import retrofit2.http.Body;


public interface WebServiceAPI {
    @POST("/api/Tokens/") // Replace "token" with your actual endpoint path
    Call<ResponseBody> postToken(@Body LoginRequest loginRequest);

}
