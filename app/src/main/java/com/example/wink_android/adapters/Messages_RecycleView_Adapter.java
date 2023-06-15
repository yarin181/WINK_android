package com.example.wink_android.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wink_android.Message;
import com.example.wink_android.R;

import java.util.ArrayList;

public class Messages_RecycleView_Adapter extends RecyclerView.Adapter<Messages_RecycleView_Adapter.MyVieHolder>  {
    private Context context;
    private int connectedId;
    private ArrayList<Message> messages;
    private static final int CONNECTED_VIEW_TYPE = 0;
    private static final int CONTACT_VIEW_TYPE = 1;

    public Messages_RecycleView_Adapter(Context context, ArrayList<Message> messages, int connectedId){
        this.context = context;
        this.messages = messages;
        this.connectedId = connectedId;
        Log.i("the contact", String.valueOf(messages.get(1).getSender()));
        Log.i("the sender", String.valueOf(messages.get(0).getSender()));
    }
    @NonNull
    @Override
    //inflate the layout (giving look to our msg)
    public MyVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Get the id's sender of the new message item
        int senderId = messages.get(viewType).getSender();
        View view;
        //this is the connected
        if(viewType == CONNECTED_VIEW_TYPE){
           view = inflater.inflate(R.layout.sent_message,parent,false);
        }
        else{
            view = inflater.inflate(R.layout.received_message,parent,false);
        }
       // Log.i("the id", String.valueOf(senderId));


        return new MyVieHolder(view);

    }
    //assigning value to the views we created in the recycle view
    @Override
    public void onBindViewHolder(@NonNull MyVieHolder holder, int position) {
        holder.content.setText(messages.get(position).getContent());
        holder.time.setText(messages.get(position).getTime());

    }
    //the number of items we displayed
    @Override
    public int getItemCount() {
        return messages.size();
    }
    @Override
    public int getItemViewType(int position) {
        // the sender of the msg is the connected user
        if (messages.get(position).getSender() == connectedId) {
            // Return the view type for the header item
            return CONNECTED_VIEW_TYPE;
        } else {
            return CONTACT_VIEW_TYPE;
        }
    }

    public static class MyVieHolder extends RecyclerView.ViewHolder{
        // grabbing the views from the recycle_view_layout like the text view

        TextView content;
        TextView time;
        public MyVieHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.message_content);
            time = itemView.findViewById(R.id.time);
        }
    }

}
