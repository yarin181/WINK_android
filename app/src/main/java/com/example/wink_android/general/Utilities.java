package com.example.wink_android.general;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utilities {
    public  static Bitmap stringToBitmap(String string){
        byte[] decodedImage = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
    }


}
