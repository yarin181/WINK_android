package com.example.wink_android.general;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utilities {
    public  static Bitmap stringToBitmap(String string){
        try {
            String[] parts = string.split(",", 2);
            byte[] decodedBytes;
            if (parts.length > 1) {
                decodedBytes = Base64.decode(parts[1], 0);

            } else {
                decodedBytes = Base64.decode(string, 0);
            }
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null; // Return null or handle the error appropriately
        }
    }
    public static Bitmap convertBlobToBitmap(byte[] blobData) {
        return BitmapFactory.decodeByteArray(blobData, 0, blobData.length);
    }

    public static byte[] convertImageToBlob(String base64Image) {
        String connectPhotoString= "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";
        String[] parts = base64Image.split(",", 2);
        try {
            if (parts.length > 1) {
                return Base64.decode(parts[1],0);
            } else {
                return Base64.decode(base64Image,0);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return  Base64.decode(connectPhotoString,0); // return default image
        }

    }

    public static String convertBlobToImage(byte[] blobData) {
        return Base64.encodeToString(blobData, 0);
    }


}
