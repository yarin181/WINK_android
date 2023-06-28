package com.example.wink_android.api;

import android.util.Log;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.User;
import com.example.wink_android.general.Constants;
import com.example.wink_android.general.Utilities;
import com.example.wink_android.DB.Message;
import com.example.wink_android.repository.ChatRepository;
import com.example.wink_android.requests.BasicUserData;
import com.example.wink_android.requests.LastMessage;
import com.example.wink_android.requests.LoginRequest;
import com.example.wink_android.requests.MessageAnswer;
import com.example.wink_android.requests.MessageRequest;
import com.example.wink_android.requests.RegisterRequest;
import com.example.wink_android.requests.UserFriend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequests {
    Retrofit retrofit;
 WebServiceAPI webServiceAPI;
 private int friendId;
 private final ChatRepository repository;

public ApiRequests( ChatRepository repository){
this.repository=repository;
    retrofit = new Retrofit.Builder()
         .baseUrl(Constants.Default_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .build();
         webServiceAPI = retrofit.create(WebServiceAPI.class);
}

    public void changeBaseUrl(String ip) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ip + ":" + Constants.Default_PORT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }


    public void getToken(String username, String password) {


    LoginRequest loginRequest=new LoginRequest(username,password);
        Call<ResponseBody> token = webServiceAPI.postToken(loginRequest);
//        Log.i("ApiRequests", token.toString());
        // You can enqueue the call to execute it asynchronously
        token.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String token = response.body().string();
                        repository.setToken("bearer "+token);
                        repository.setStatus(Constants.EXIST);
                        Log.i("ApiRequests", "Token: " + token);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    repository.setStatus(Constants.NOT_EXIST);
                    Log.e("ApiRequests", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //repository.setStatus(Constants.NOT_EXIST);
                repository.setStatus(Constants.FAILED_CONNECT_TO_SERVER);
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
                        repository.getUserDao().insertUser(new User(userData.getUsername(),
                                userData.getDisplayName(), Utilities.compressImage(userData.getProfilePic()),token));
                        repository.setStatus(Constants.SUCCESSFUL_GET_USER_DATA);
                        Log.i("ApiRequests getMyUserData", "Username: " + userData.getUsername());
                    }
                } else {
                    repository.setStatus(Constants.FAILED_GET_USER_DATA);
                    // Handle unsuccessful response
                    Log.e("ApiRequests getMyUserData", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<BasicUserData> call, Throwable t) {
                // Handle failure
               // repository.setStatus(Constants.FAILED_GET_USER_DATA);
                repository.setStatus(Constants.FAILED_CONNECT_TO_SERVER);
                Log.e("ApiRequests getMyUserData", "Request failed: " + t.getMessage());
            }
        });

    }
    public void registerUser(RegisterRequest registerRequest) {
        Call<ResponseBody> answer = webServiceAPI.postUsers(registerRequest);

        answer.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                        repository.setStatus(Constants.SUCCESSFUL_REGISTER);
                        Log.i("ApiRequests", "new user added");

                } else {
                    // Handle error response
                    repository.setStatus(Constants.FAILED_REGISTER);
                    Log.e("ApiRequests", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                //repository.setStatus(Constants.FAILED_REGISTER);
                repository.setStatus(Constants.FAILED_CONNECT_TO_SERVER);
                Log.e("ApiRequests", "Failure: " + t.getMessage());
            }
        });
    }

    public List<Chat> friendsToChats(List<UserFriend> friends){
        List<Chat> chats=new ArrayList<>();
        if(friends != null){
            for (UserFriend friend: friends) {
                BasicUserData user=friend.getUser();
                LastMessage lastMessage=friend.getLastMessage();
                if(lastMessage!=null){
                        chats.add(new Chat(friend.getId(),lastMessage.getContent(),lastMessage.getCreated() ,user.getUsername(), user.getDisplayName(), Utilities.compressImage(user.getProfilePic())));
                    }else{
                        chats.add(new Chat(friend.getId(),"" ,"",user.getUsername(), user.getDisplayName(), Utilities.compressImage(user.getProfilePic())));
                    }
            }
        }
        return chats;
    }
    public void getFriends(String token,String fireBaseToken) {
    Call <List<UserFriend>> chats = webServiceAPI.getChats(token,fireBaseToken);
        //Call<BasicUserData> userData = webServiceAPI.getUsersUsername(username,token);

        chats.enqueue(new Callback<List<UserFriend>>() {
            @Override
            public void onResponse(Call<List<UserFriend>> call, Response<List<UserFriend>> response) {
                if (response.isSuccessful()) {
                    List<UserFriend> friends = response.body();
                    List<Chat> chats=friendsToChats(friends);
                    if(chats.size()>0){
                        for (Chat chat: chats) {
                            repository.add(chat);
                        }
                        if (chats.size()>0)    {
                            Log.i("ApiRequests", "id: " + chats.get(0).getId());
                        }
                        repository.setStatus(Constants.SUCCESSFUL_GET_CHATS);
                    }

                } else {
                    // Handle unsuccessful response
                    repository.setStatus(Constants.FAILED_GET_CHATS);

                    Log.e("ApiRequests", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UserFriend>> call, Throwable t) {
                // Handle failure
                //repository.setStatus(Constants.FAILED_GET_CHATS);
                repository.setStatus(Constants.FAILED_CONNECT_TO_SERVER);
                Log.e("ApiRequests", "Request failed: " + t.getMessage());
            }
        });


    }
    public void addFriend(String name, String token) {
    LoginRequest request = new LoginRequest(name);
    Call<UserFriend> friendCall = webServiceAPI.postChats(request, token);

    friendCall.enqueue(new Callback<UserFriend>() {
        @Override
        public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {
            if (response.isSuccessful()) {
                UserFriend friend = response.body();
                if (friend != null) {
                    BasicUserData user = friend.getUser();
                    Chat chat = new Chat(friend.getId(),"","", user.getUsername()
                            ,user.getDisplayName(), Utilities.compressImage(user.getProfilePic()));
                    repository.add(chat);
                    repository.setStatus(Constants.SUCCESSFUL_ADD_CHAT);
//                    repository.setStatus(response.code());
                    Log.i("ApiRequests", "friend id: " + friend.getId());
                }
            } else {
                // Handle unsuccessful response
//                repository.setStatus(response.code());
                if(response.code()==400){
                    repository.setStatus(Constants.FAILED_ADD_CHAT_INCORRECT_USER);
                }else{
                    repository.setStatus(Constants.FAILED_ADD_CHAT);
                }


                Log.e("ApiRequests", "Request failed with code: " + response.code());
            }
        }

        @Override
        public void onFailure(Call<UserFriend> call, Throwable t) {
            // Handle failure
            //repository.setStatus(Constants.FAILED_ADD_CHAT);

            repository.setStatus(Constants.FAILED_CONNECT_TO_SERVER);
            Log.e("ApiRequests", "Request failed: " + t.getMessage());
        }
    });
}

    public void sendMessage(int id,String message,String token) {
        MessageRequest messageRequest=new MessageRequest(message);
        friendId=id;
//        Call<UserFriend> friendCall = webServiceAPI.postChats(request, token);
        Call<MessageAnswer> messageAnswerCall= webServiceAPI.postChatsIdMessages(id,token,messageRequest);
        messageAnswerCall.enqueue(new Callback<MessageAnswer>() {
            @Override
            public void onResponse(Call<MessageAnswer> call, Response<MessageAnswer> response) {
                if (response.isSuccessful()) {
                   MessageAnswer answer = response.body();
                   if (answer != null) {
                       Message message=new Message(answer.getId(),id,answer.getCreated(),answer.getSender().getUsername(),answer.getContent());
                       repository.addMessage(message,friendId);
                       repository.setStatus(Constants.SUCCESSFUL_SEND_MESSAGE);
                       repository.reloadChatLastMessage(id);
                   Log.i("ApiRequests", "friend id: " + answer.getId());
                   }
                } else {
                    // Handle unsuccessful response
                    Log.e("ApiRequests", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageAnswer> call, Throwable t) {
                // Handle failure
                repository.setStatus(Constants.FAILED_CONNECT_TO_SERVER);
                Log.e("ApiRequests", "Request failed: " + t.getMessage());
            }
        });
    }
    public List<Message> answersToMessages(List<MessageAnswer> answers,int id){
        List<Message> messages=new ArrayList<>();
        if(answers !=null){
            for (MessageAnswer answer: answers) {
//                messages.add(new Message())
                messages.add(new Message(answer.getId(),id,answer.getCreated(),answer.getSender().getUsername(),answer.getContent()));
            }
        }
        return messages;
    }
    public void getMessagesFromApi(int id,String token) {
    friendId=id;
    Call <List<MessageAnswer>> messageAnswerCall= webServiceAPI.getChatsIdMessages(id,token);
        messageAnswerCall.enqueue(new Callback<List<MessageAnswer>>() {
            @Override
            public void onResponse(Call<List<MessageAnswer>> call, Response<List<MessageAnswer>> response) {
                if (response.isSuccessful()) {
                    List<MessageAnswer> answers = response.body();
                    List<Message> messages= answersToMessages(answers,friendId);
                    for (Message message:messages) {
                        repository.addMessage(message,friendId);
                    }
                    if (messages.size()>0) {
                        Log.i("ApiRequests", " id: " + answers.get(0).getId());
                        String lastMessageContent = messages.get(0).getContent();
                        String lastMessageCreated = messages.get(0).getCreated();
                        Chat updatedChat= repository.getChatById(friendId);
                        updatedChat.setLastMessageContent(lastMessageContent);
                        updatedChat.setLastMessageCreated(lastMessageCreated);
                        repository.updateChat(updatedChat);
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("ApiRequests", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MessageAnswer>>call, Throwable t) {
                // Handle failure
                repository.setStatus(Constants.FAILED_CONNECT_TO_SERVER);
                Log.e("ApiRequests", "Request failed: " + t.getMessage());
            }
        });
    }
}


