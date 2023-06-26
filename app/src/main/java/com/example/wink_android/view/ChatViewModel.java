package com.example.wink_android.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.Message;
import com.example.wink_android.DB.User;
import com.example.wink_android.DB.UserDao;
import com.example.wink_android.repository.ChatRepository;
import com.example.wink_android.requests.RegisterRequest;

import java.util.List;

public class ChatViewModel extends ViewModel {

    private ChatRepository mRepository;


    private LiveData<List<Chat>> chats;
    private LiveData<List<Message>> messages;
//    private String token;
    private MutableLiveData<String> status;
    private User ConnectUser;

    public ChatViewModel (){
        mRepository = ChatRepository.getInstance();
        chats = mRepository.getChats();
        status=  mRepository.getStatus();
        messages = mRepository.getMessages();
    }

    public LiveData<Message> getLastMessageForChat(int id) {
        return mRepository.getLastMessageForChat(id);
    }
    public Chat viewModalGetRealChatByUsername(String username){
        return mRepository.RepositoryGetRealChatByUsername(username);
    }

    public void setIsLogdIn(Boolean logdIn) {
       mRepository.setIsLogdIn(logdIn);
    }
    public Boolean getIsLogdIn(){
       return mRepository.getIsLogdIn();
    }
    public void setRepositoryFireBaseToken(String token){
        mRepository.setFireBaseToken(token);
    }
    public String getRepositoryFireBaseToken(){
        return mRepository.getFireBaseToken();
    }
    //get messages by id
    public Message getMessageById(int id){
        return mRepository.getMessageById(id);
    }

    public void deleteUserDetails(){
        mRepository.deleteUserDetailsFromRepo();
        //        mRepository.getUserDao().deleteAllUsers();
//        mRepository
    }

    public LiveData<List<Message>> getMessages() {
        //reloadMessages();
        return messages;
    }

    //get messages by chat id


    public void setMessagesByChatId(int chatId){
        messages = mRepository.getMessagesByChatId(chatId);
    }

    public MutableLiveData<String> getStatus() {
        return status;
    }

    public void switchThemMode(){
        mRepository.switchTheme();
    }
    public boolean getTheme(){
        return mRepository.getTheme();
    }

    public User getConnectUser() {
//        mRepository.
        return mRepository.getConnectedUser();
    }

    public void setConnectUser(String connectUser) {
       mRepository.getUserDetails(connectUser);
    }
    public void tryToLogin(String username,String password){
        mRepository.repositoryLogIn(username,password);
    }
    public void tryToRegister(RegisterRequest registerRequest){
        mRepository.repositoryRegister(registerRequest);
    }
    //making the api update the chats
    public void updateChats(String fireBaseToken){
        mRepository.repositoryUpdateChats(fireBaseToken);
    }



    public void updateMessagesByChatId(int chatId){
        mRepository.updateMessagesByChatId(chatId);
    }


    //get chat by id
    public Chat getChatById(int chatId){
        return mRepository.getChatById(chatId);
    }


    //add message to the database
    public void addMessage(Message message,int id){
        mRepository.addMessage(message, id);
    }



    //send message to the server
    public void sendMessage(int chatId, String message){
        mRepository.sendMessage(chatId,message);
    }

    public LiveData<List<Chat>> getChats() { return chats; }

    public LiveData<Chat> getChatByUsername(String username){
        return mRepository.getChatByUsername(username);
    }


    //set Token to the repository
    public void setToken(String token){
        mRepository.setToken(token);
    }
    public void add(Chat chat){mRepository.add(chat);}

    public void delete(Chat chat){mRepository.delete(chat);}

    public void reload(){
        this.chats = mRepository.getChats();
    }

    public void reloadMessages(){
        this.messages = mRepository.getMessages();
    }

    public void editSettings(){

    }
    public String getIp(){
        return mRepository.getIp();
    }

    public void setIP(String ip){
        mRepository.setIp(ip);
    }

    public void addContactByUsername(String username){
        mRepository.addChat(username);
    }

    public void loadSettings() {
        mRepository.loadSettings();
    }

    public void storeSettings() {
        mRepository.storeSettings();
    }

    public void setInitialStatus() {
        mRepository.setInitialStatus();
    }
}
