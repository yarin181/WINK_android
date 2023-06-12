package com.example.wink_android.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.repository.ChatRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {

    private ChatRepository mRepository;

    private LiveData<List<Chat>> chats;


    public ChatViewModel (){
        mRepository = new ChatRepository();
        chats = mRepository.getChats();
    }

    public LiveData<List<Chat>> get() { return chats; }

    public void add(Chat chat){mRepository.add(chat);}

    public void delete(Chat chat){mRepository.delete(chat);}

    public void reload(){mRepository.reload();}

}
