package com.example.wink_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Messages_RecycleView_Adapter extends RecyclerView.Adapter<Messages_RecycleView_Adapter.MyVieHolder>  {
    private Context context;
    private int connectedId;
    private ArrayList<Message> messages;

    public Messages_RecycleView_Adapter(Context context, ArrayList<Message> messages,int connectedId){
        this.context = context;
        this.messages = messages;
        this.connectedId = connectedId;
    }
    @NonNull
    @Override
    //inflate the layout (giving look to our msg)
    public Messages_RecycleView_Adapter.MyVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Get the id's sender of the new message item
        int senderId = messages.get(viewType).getSender();
        View view;
        if(senderId ==connectedId){
            view = inflater.inflate(R.layout.sent_message,parent,false);
        }
        else{
            view = inflater.inflate(R.layout.received_message,parent,false);
        }

        return new Messages_RecycleView_Adapter.MyVieHolder(view);

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
