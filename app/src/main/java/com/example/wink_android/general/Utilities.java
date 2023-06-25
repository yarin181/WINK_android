package com.example.wink_android.general;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.util.Base64;
import android.icu.util.TimeZone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.Date;
import java.util.Locale;

public class Utilities {
    public  static Bitmap stringToBitmap(String string){
        try {
            byte[] decodedBytes = Base64.decode(string, 0);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null; // Return null or handle the error appropriately
        }
    }

    public static String removePrefixFromBase64Image(String base64Image) {
        String[] parts = base64Image.split(",", 2);
        if (parts.length > 1) {
            return parts[1];
        } else {
            return base64Image;
        }
    }

    public static String compressImage(String base64Image) {
        try {
            byte[] decodedBytes = Base64.decode(removePrefixFromBase64Image(base64Image), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            // Obtain the image's
            ExifInterface exif = new ExifInterface(new ByteArrayInputStream(decodedBytes));
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int desiredWidth = 600;
            int desiredHeight = 600;

            // Rotate the bitmap if necessary
            Matrix matrix = new Matrix();
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }
            // Resize the bitmap maintaining aspect ratio and applying rotation
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, false);
            Bitmap rotatedBitmap = Bitmap.createBitmap(resizedBitmap, 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight(), matrix, true);

            // Compress the bitmap to a ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);

            // Convert the compressed bitmap to a byte array
            byte[] compressedBytes = outputStream.toByteArray();


            // Encode the byte array to base64 string
            String compressedBase64Image = Base64.encodeToString(compressedBytes, Base64.DEFAULT);

            return compressedBase64Image;
        } catch (Exception e) {
            e.printStackTrace();
            base64Image = Constants.DEFAULT_BASE64_STRING;
            return base64Image; // return default image
        }


    }

    public static String convertToDateTime(String string, boolean isDate) {
        String outputFormat = isDate ? "dd/MM\nHH:mm" : "HH:mm";
        String inputFormat = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzzz)";
        String inputFormat2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
            inputDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            Date date = inputDateFormat.parse(string);
            return outputDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat2, Locale.ENGLISH);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
            inputDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            Date date = inputDateFormat.parse(string);
            return outputDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "--:--";
    }


}
