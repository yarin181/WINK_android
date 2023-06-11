package com.example.wink_android.api;

import com.example.wink_android.requests.BasicUserData;
import com.example.wink_android.requests.LoginRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


import retrofit2.http.Body;
import retrofit2.http.Path;


public interface WebServiceAPI {
    @POST("/api/Tokens/") // Replace "token" with your actual endpoint path
    Call<ResponseBody> postToken(@Body LoginRequest loginRequest);
    @GET("/api/Users/{username}/")
    Call<BasicUserData> getUsersUsername(@Path("username") String username, @Header("Authorization") String token);

}
