package com.example.wink_android.api;

import com.example.wink_android.requests.BasicUserData;
import com.example.wink_android.requests.LoginRequest;
import com.example.wink_android.requests.MessageAnswer;
import com.example.wink_android.requests.MessageRequest;
import com.example.wink_android.requests.RegisterRequest;
import com.example.wink_android.requests.UserFriend;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


import retrofit2.http.Body;
import retrofit2.http.Path;


public interface WebServiceAPI {
    @POST("/api/Tokens/") // Replace "token" with your actual endpoint path
    Call<ResponseBody> postToken(@Body LoginRequest loginRequest);
    @GET("/api/Users/{username}/")
    Call<BasicUserData> getUsersUsername(@Path("username") String username, @Header("Authorization") String token);
    @POST("/api/Users/")
    Call<ResponseBody> postUsers(@Body RegisterRequest registerRequest );
    @GET("api/Chats/")
    Call <List<UserFriend>>getChats(
            @Header("Authorization") String token
            ,@Header("FireBaseToken") String FireBaseToken);
    @POST("api/Chats/")
    Call <UserFriend> postChats(@Body LoginRequest loginRequest ,@Header("Authorization") String token);
    @POST("api/Chats/{id}/Messages")
    Call <MessageAnswer> postChatsIdMessages(@Path("id") int id, @Header("Authorization") String token, @Body MessageRequest msg);
    @GET("api/Chats/{id}/Messages")
    Call <List<MessageAnswer>> getChatsIdMessages(@Path("id") int id, @Header("Authorization") String token);

}
