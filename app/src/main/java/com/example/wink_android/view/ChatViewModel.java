package com.example.wink_android.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.User;
import com.example.wink_android.repository.ChatRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {

    private ChatRepository mRepository;

    private LiveData<List<Chat>> chats;

    private User ConnectUser;


    public ChatViewModel (){
        mRepository = ChatRepository.getInstance();
        chats = mRepository.getChats();

    }
    public void switchThemMode(){
        mRepository.switchTheme();
    }

    public User getConnectUser() {
        return ConnectUser;
    }

    public void setConnectUser(String connectUser) {
        ConnectUser = mRepository.getUserDetails(connectUser);
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

    public boolean addContactByUsername(String username){
        if (!mRepository.addChat(username)){
            return false;
        }
        reload();
        return true;
    }
}
