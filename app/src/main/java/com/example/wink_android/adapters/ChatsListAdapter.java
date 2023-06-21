package com.example.wink_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.Message;
import com.example.wink_android.DB.User;
import com.example.wink_android.R;
import com.example.wink_android.general.OvalImageDrawable;
import com.example.wink_android.general.Utilities;
import com.example.wink_android.view.ChatViewModel;

import java.util.List;
public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatViewHolder> {
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername;
        private final TextView tvLastMessage;
        //private final TextView tvTime;
        private final TextView tvDate;
        private final ImageView ivProfilePic;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.usernameTextView);
            tvLastMessage = itemView.findViewById(R.id.lastMessageTextView);
            //tvTime = itemView.findViewById(R.id.timeTextView);
            tvDate = itemView.findViewById(R.id.dateTextView);
            ivProfilePic = itemView.findViewById(R.id.userPhotoImageView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    public ChatsListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.user_bar_layout,parent,false);

        return new ChatsListAdapter.ChatViewHolder(itemView);
    }

    public void setChats(List<Chat> lst){
        chats = lst;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        if (chats !=null){
            final Chat current = chats.get(position);
            holder.ivProfilePic.setImageDrawable(new OvalImageDrawable(Utilities.stringToBitmap(current.getOtherProfilePic())));
            holder.tvUsername.setText(current.getOtherUsername());
            //Message lastMessage = current.getLsatMessage();
            holder.tvLastMessage.setText(current.getLastMessageContent());
            holder.tvDate.setText(Utilities.convertToDateTime(current.getLastMessageCreated()));

        }
    }

    @Override
    public int getItemCount() {
        if (chats != null){
            return chats.size();
        }
        return 0;
    }
}
