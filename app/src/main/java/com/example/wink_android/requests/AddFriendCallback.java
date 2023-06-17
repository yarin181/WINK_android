package com.example.wink_android.requests;

public interface AddFriendCallback {
    void onSuccess(UserFriend friend);
    void onFailure(int statusCode, String errorMessage);
}
