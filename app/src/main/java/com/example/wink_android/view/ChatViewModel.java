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
//    private String token;
    private MutableLiveData<String> status;
    private User ConnectUser;

    public ChatViewModel (){
        mRepository = ChatRepository.getInstance();
        chats = mRepository.getChats();
        status=  mRepository.getStatus();
    }
    public void deleteUserDetails(){
        mRepository.deleteUserDetailsFromRepo();
        //        mRepository.getUserDao().deleteAllUsers();
//        mRepository
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
    public void updateChats(){
        mRepository.repositoryUpdateChats();
    }

    public LiveData<List<Message>> getMessagesByChatId(int chatId){
        return mRepository.getMessagesByChatId(chatId);
    }

    //get chat by id
    public Chat getChatById(int chatId){
        return mRepository.getChatById(chatId);
    }


    //add message to the database
    public void addMessage(Message message){
        mRepository.addMessage(message);
    }

    //send message to the server
    public void sendMessage(int chatId, String message){
        mRepository.sendMessage(chatId,message);
    }

    public LiveData<List<Chat>> getChats() { return chats; }

    public LiveData<Chat> getChatByUsername(String username){
        return mRepository.getChatByUsername(username);
    }

    public void add(Chat chat){mRepository.add(chat);}

    public void delete(Chat chat){mRepository.delete(chat);}

    public void reload(){
        this.chats = mRepository.getChats();
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
//        if (!mRepository.addChat(username)){
//            return false;
//        }
//        reload();
//        return true;
        return;
    }
}
