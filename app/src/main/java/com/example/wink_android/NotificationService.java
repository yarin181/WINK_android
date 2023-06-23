package com.example.wink_android;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {
    public NotificationService() {


    }
    @Override
    public void onNewToken(@NonNull String token){

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        if (remoteMessage.getNotification() != null) {
//            createNotificationChannel();
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
////                    .setSmallIcon(R.drawable.icon)
//                    .setContentTitle(remoteMessage.getNotification().getTitle())
//                    .setContentText(remoteMessage.getNotification().getBody())
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//            if (ActivityCompat.checkSelfPermission(this, "android.permission.USE_NOTIFICATION")
//                    == PackageManager.PERMISSION_GRANTED) {
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//                notificationManager.notify(1, builder.build());
//            }
//        }
//        String temp=remoteMessage.getData().get("text");

        int a=1;
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


    //    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}