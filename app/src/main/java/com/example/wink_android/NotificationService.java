package com.example.wink_android;//package com.example.wink_android;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.activities.ChatActivity;
import com.example.wink_android.activities.Login;
import com.example.wink_android.activities.UsersActivity;
import com.example.wink_android.view.ChatViewModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Objects;


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class NotificationService extends FirebaseMessagingService {
    private ChatViewModel viewModel;

    public NotificationService() {

        viewModel=new ChatViewModel();
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        //
        if (remoteMessage.getNotification() != null && viewModel.getIsLogdIn()) {
          String name=remoteMessage.getNotification().getTitle();
          if(name==null){
              viewModel.updateChats(viewModel.getRepositoryFireBaseToken());
          }else {
              int userId=viewModel.viewModalGetRealChatByUsername(name).getId();
              Intent intent=new Intent(this, ChatActivity.class);
              intent.putExtra("id",userId);
//              Log.i("notifications","herreeeeeeeeee");
//              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
              PendingIntent pendingIntent=PendingIntent.getActivity(this, userId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
              viewModel.updateMessagesByChatId(userId);
              createNotificationChannel();
              NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                      .setSmallIcon(R.drawable.ic_send)
                      .setContentTitle(remoteMessage.getNotification().getTitle())
                      .setContentText(remoteMessage.getNotification().getBody())
                      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                      .setContentIntent(pendingIntent)
                      .setAutoCancel(true);



              if (ActivityCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS")
                      == PackageManager.PERMISSION_GRANTED) {
                  NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                  notificationManager.notify(1, builder.build());
//                viewModel.reloadMessages();
//                viewModel.getMessageById(userId);
              }
          }

        }




        Log.i("notification","fire base works");
//        super.onMessageReceived(message);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "MyChannel", importance);
            channel.setDescription("wink channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

//    private int nameToId(String name){
//        List<Chat> chats=viewModel.getChats().getValue();
//        for (Chat c: chats) {
//            if(c.getOtherUsername() == name)
//        }
//    }


    //    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.example.wink_android.R;
//import com.example.wink_android.activities.ChatActivity;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//public class NotificationService extends FirebaseMessagingService {
//
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        if (remoteMessage.getNotification() != null) {
//            String name = remoteMessage.getNotification().getTitle();
//            if (name != null) {
//                int userId = getUserIdByName(name); // Implement this method to get the user ID by name
//                if (userId != -1) {
//                    Intent intent = new Intent(this, ChatActivity.class);
//                    intent.putExtra("id", userId);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(this, userId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                    createNotificationChannel();
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
//                            .setSmallIcon(R.drawable.ic_send)
//                            .setContentTitle(remoteMessage.getNotification().getTitle())
//                            .setContentText(remoteMessage.getNotification().getBody())
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                            .setAutoCancel(true)
//                            .setContentIntent(pendingIntent);
//
//                    if (ActivityCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS")
//                            == PackageManager.PERMISSION_GRANTED) {
//                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//                        notificationManager.notify(1, builder.build());
//                    }
//                }
//            }
//        }
//
//        Log.i("notification", "Firebase works");
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("1", "MyChannel", importance);
//            channel.setDescription("Wink channel");
//
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//    }
//
//    private int getUserIdByName(String name) {
//        // Implement your logic here to retrieve the user ID by name
//        // You can use a database query or any other method to fetch the ID
//        // Return the user ID if found, otherwise return -1
//        return -1;
//    }
//}
