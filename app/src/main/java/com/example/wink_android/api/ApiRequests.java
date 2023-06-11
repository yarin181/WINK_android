package com.example.wink_android.api;

import android.util.Log;

import com.example.wink_android.requests.BasicUserData;
import com.example.wink_android.requests.LoginRequest;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequests {
    Retrofit retrofit;
 WebServiceAPI webServiceAPI;


//String.valueOf(R.string.BaseUrl)
public ApiRequests(){
         retrofit = new Retrofit.Builder()
         .baseUrl("http://10.0.2.2:5000")
//                 .callbackExecutor(Executor.newSingleThread)
         .addConverterFactory(GsonConverterFactory.create())
         .build();
         webServiceAPI = retrofit.create(WebServiceAPI.class);
         }
    public void getToken(String username, String password) {
        LoginRequest loginRequest=new LoginRequest(username,password);
        Call<ResponseBody> token = webServiceAPI.postToken(loginRequest);
        Log.i("ApiRequests", token.toString());
        // You can enqueue the call to execute it asynchronously
        token.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String token = response.body().string();
                        // Handle the token
                        Log.i("ApiRequests", "Token: " + token);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle error response
                    Log.e("ApiRequests", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Log.e("ApiRequests", "Failure: " + t.getMessage());
            }
        });
    }
    public void getMyUserData(String username,String token) {
        Call<BasicUserData> userData = webServiceAPI.getUsersUsername(username,token);

        userData.enqueue(new Callback<BasicUserData>() {
            @Override
            public void onResponse(Call<BasicUserData> call, Response<BasicUserData> response) {
                if (response.isSuccessful()) {
                    BasicUserData userData = response.body();
                    if (userData != null) {
                        Log.i("ApiRequests", "Username: " + userData.getUsername());
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("ApiRequests", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<BasicUserData> call, Throwable t) {
                // Handle failure
                Log.e("ApiRequests", "Request failed: " + t.getMessage());
            }
        });
    }

}


