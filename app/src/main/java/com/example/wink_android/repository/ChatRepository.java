package com.example.wink_android.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.DB.ChatDao;
import com.example.wink_android.DB.Message;
import com.example.wink_android.DB.MessageDao;
import com.example.wink_android.DB.User;
import com.example.wink_android.DB.UserDao;
import com.example.wink_android.DB.daoes.SettingsInfoDao;
import com.example.wink_android.DB.entities.SettingsInfo;
import com.example.wink_android.api.ApiRequests;
import com.example.wink_android.requests.RegisterRequest;

import java.util.List;

public class ChatRepository {
    private ChatDB chatDB;
    private String fireBaseToken;
    private Boolean isLogdIn;
    private final UserDao userDao;
    private MessageDao messageDao;
    private final ChatDao chatDao;
    private final SettingsInfoDao settingsInfoDao;
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
        settingsInfoDao = chatDB.settingsInfoDao();
        API = new ApiRequests(this);
        status=new MutableLiveData<>();
        loadSettings();
        setInitialStatus();
    }

    public Boolean getIsLogdIn() {
        return isLogdIn;
    }

    public void setIsLogdIn(Boolean logdIn) {
        isLogdIn = logdIn;
    }

    //get the last message for a chat
    public Message getLastMessageForChat(int id) {
        return messageDao.getLastMessageForChat(id);
    }

    public Message getMessageById(int id){
        return messageDao.getMessageById(id);
    }

    //making the api update the chats
    public void repositoryUpdateChats(String fireBaseToken){
        Thread thread = new Thread(() -> {
            API.getFriends(token,fireBaseToken);
        });
        thread.start();
    }
    public void switchTheme(){
        darkMode = !darkMode;
        storeSettings();
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
            messageDao.deleteAllMessages();

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

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }

    public LiveData<List<Chat>> getChats(){
        return chatDao.getAllChats();
    }

    public synchronized void add(Chat chat){
        if (chatDao.getChatById(chat.getId()) == null) {
            chatDao.insertChat(chat);
        }
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
        this.ip = ip;
        API.changeBaseUrl(ip);
        storeSettings();
    }

    public void addMessage(Message message,int id){
        if (messageDao.getMessageById(message.getId()) == null) {
            messageDao.insertMessage(message);
        }
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

    public Chat RepositoryGetRealChatByUsername(String username){
        return chatDao.getRealChatByUsername(username);
    }

    public void getUserDetails(String username){
        User user = userDao.getUser();
        if (user != null){
            return;
        }else{
            new Thread(()->{
               API.getMyUserData(username,token);
            }).start();
        }
    }

    public void addChat(String username) {
        API.addFriend(username, token);
    }

    public LiveData<List<Message>> getMessages() {
        return messageDao.getAllMessages();
    }

    public void updateChat(Chat updatedChat) {
        chatDao.updateChat(updatedChat);
    }

    public void loadSettings() {
        SettingsInfo settingsInfo = settingsInfoDao.getSettingsInfo();
        if (settingsInfo != null) {
            this.darkMode = settingsInfo.getThemeStatus();
            this.ip = settingsInfo.getIpAddress();
        }else {
            this.ip = "10.0.2.2";
            this.darkMode = false;
        }
        setIp(this.ip);
    }

    public void storeSettings() {
        Thread thread = new Thread(() -> {
            settingsInfoDao.deleteSettingsInfo();
            settingsInfoDao.insert(new SettingsInfo(ip,darkMode));
        });
        thread.start();
    }

    public void setInitialStatus() {
        status.setValue(" ");
    }

    public void reloadChatLastMessage(int friendId) {
        Chat chat = chatDao.getChatById(friendId);
        if (chat != null) {
            Message lastMessage = messageDao.getLastMessageForChat(friendId);
            if (lastMessage == null) {
                return;
            }
            chat.setLastMessageContent(lastMessage.getContent());
            chat.setLastMessageCreated(lastMessage.getCreated());
        }
        chatDao.updateChat(chat);
    }
}
