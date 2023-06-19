package com.example.wink_android.repository;
import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.DB.ChatDao;
import com.example.wink_android.DB.Message;
import com.example.wink_android.DB.MessageDao;
import com.example.wink_android.DB.MessageDao;
import com.example.wink_android.DB.User;
import com.example.wink_android.DB.UserDao;
import com.example.wink_android.api.ApiRequests;
import com.example.wink_android.requests.AddFriendCallback;
import com.example.wink_android.requests.RegisterRequest;
import com.example.wink_android.requests.ServerAnswer;
import com.example.wink_android.requests.UserFriend;

import java.util.List;

public class ChatRepository {
    private ChatDB chatDB;
    private final UserDao userDao;
    private MessageDao messageDao;
    private final ChatDao chatDao;
    private final String connectPhotoString= "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";
    private String ip;
    private final MutableLiveData<String> status;
    ApiRequests API;
    private String token;
    private boolean darkMode;

    private static ChatRepository instance;
    private ChatRepository(){
        chatDB = ChatDB.getInstance(null);
        userDao = chatDB.userDao();
        chatDao = chatDB.chatDao();
        messageDao= chatDB.messageDao();
        ip = "10.0.2.2";
        status=new MutableLiveData<>();
        status.setValue(" ");
//        deleteUserDetailsFromRepo();
        API = new ApiRequests(this);
    }
    //making the api update the chats
    public void repositoryUpdateChats(){
    API.getFriends(token);
        darkMode = false;
    }
    public void switchTheme(){
        darkMode = !darkMode;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public boolean getTheme(){
        return darkMode;
    }


    public User getConnectedUser(){
        User u=userDao.getUser();
        return u;
    }
    public void deleteUserDetailsFromRepo(){
        userDao.deleteAllUsers();
        chatDao.deleteAllChats();
    }
    public void repositoryLogIn(String username,String password){
        API.getToken(username,password);
    }
    public void repositoryRegister(RegisterRequest registerRequest){
        API.registerUser(registerRequest);
    }

    public void setToken(String token) {
        this.token = token;
    }

    //get messages by chat id
    public LiveData<List<Message>> getMessagesByChatId(int chatId){
        return messageDao.getMessagesByChatId(chatId);
    }

    public UserDao getUserDao() {
        return userDao;
    }
    public ChatDao getChatDao(){return chatDao;}

    public MutableLiveData<String> getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status.setValue(status);
    }
    public static synchronized ChatRepository getInstance() {
        if (instance == null) {
            instance = new ChatRepository();
        }
        return instance;
    }

    public LiveData<List<Chat>> getChats(){
        return chatDao.getAllChats();
    }

    public void add(Chat chat){
        chatDao.getAllChats().observeForever(chats -> {
            if (chats != null) {
                boolean chatExists = false;
                for (Chat existingChat : chats) {
                    if (existingChat.getId() == chat.getId()) {
                        chatExists = true;
                        break;
                    }
                }
                if (!chatExists) {
                    chatDao.insertChat(chat);
                }
            } else {
                chatDao.insertChat(chat);
            }
        });
    }

    public void delete(Chat chat){
        chatDao.deleteChat(chat);
    }
    public void reload(){
        ///reload
    }

    //get a chat by Cha id
    public Chat getChatById(int id){
        return chatDao.getChatById(id);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        API.changeBaseUrl(ip);
    }

    public void addMessage(Message message,int id){
        messageDao.getMessagesByChatId(id).observeForever(messages -> {
            if (messages != null) {
                boolean messageExists = false;
                for (Message existingMessage : messages) {
                    if (existingMessage.getId() == message.getId()) {
                        messageExists = true;
                        break;
                    }
                }
                if (!messageExists) {
                    messageDao.insertMessage(message);
                }
            } else {
                messageDao.insertMessage(message);
            }
        });
        //messageDao.insertMessage(message);
    }

    //send a message to the api
    public void sendMessage(int id ,String messageContent){
        API.sendMessage(id,messageContent,token);
    }
    public void updateMessagesByChatId(int chatId){
        API.getMessagesFromApi(chatId,token);
        //return messageDao.getMessagesByChatId(chatId);
    }

    public LiveData<Chat> getChatByUsername(String username){
        return chatDao.getChatByUsername(username);
    }
    public void getUserDetails(String username){
        //userDao.getUser("yoav")
        User user = userDao.getUser();
        if (user != null){
            return;
        }else{
            new Thread(()->{
               API.getMyUserData(username,token);
            }).start();
        }
    }

    public void addChat(String username){
        API.addFriend(username,token);

        // get the username display name and profile pic
        // if the user isn't exists return here false
//        Random random = new Random();
//        new Thread(()->{
//            chatDao.insertChat(new Chat(random.nextInt(),username,username,connectPhotoString));
//        }).start();

        return;
    }

    public LiveData<List<Message>> getMessages() {
        return messageDao.getAllMessages();
    }
}
