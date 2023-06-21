package com.example.wink_android.general;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.util.Base64;

import com.example.wink_android.R;
import com.example.wink_android.repository.ChatRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

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

    public static String removePrefixFromBase64Image(String base64Image) {
        String[] parts = base64Image.split(",", 2);
        if (parts.length > 1) {
            return parts[1];
        } else {
            return base64Image;
        }
    }

    public static String compressImage(String base64Image) {
        try{
            byte[] decodedBytes = Base64.decode(removePrefixFromBase64Image(base64Image), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            // Calculate the desired width and height for the compressed image
            int desiredWidth = 600;
            int desiredHeight = 600;

            // Resize the bitmap maintaining aspect ratio
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, false);

            // Compress the bitmap to a ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);

            // Convert the compressed bitmap to a byte array
            byte[] compressedBytes = outputStream.toByteArray();

            // Encode the byte array to base64 string
            String compressedBase64Image = Base64.encodeToString(compressedBytes, Base64.DEFAULT);

            return compressedBase64Image;
        }catch (Exception e){
            e.printStackTrace();
            base64Image = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC";
            return  base64Image; // return default image
        }

    }

    public static String compressImage2(String base64Image) {
        try{
            byte[] decodedBytes = Base64.decode(removePrefixFromBase64Image(base64Image), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            // Remove EXIF orientation
            bitmap = removeExifOrientation(bitmap, decodedBytes);

            // Create a ByteArrayOutputStream to hold the compressed image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Start with a quality of 100
            int quality = 100;
            boolean compressionSuccess = false;

            while (!compressionSuccess && quality >= 0) {
                // Compress the Bitmap with the current quality
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

                // Convert the compressed Bitmap to a byte array
                byte[] compressedBytes = outputStream.toByteArray();

                // Calculate the file size in KB
                int fileSizeKB = compressedBytes.length / 1024;

                if (fileSizeKB <= Constants.IMAGE_MAX_SIZE) {
                    compressionSuccess = true;
                } else {
                    // Reduce the quality for the next iteration
                    quality -= 10;

                    // Reset the ByteArrayOutputStream for the next compression
                    outputStream.reset();
                }
            }

            // Encode the byte array to a Base64 string
            String compressedBase64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

            // Clear the memory used by the original Bitmap
            bitmap.recycle();

            return compressedBase64;
        } catch (Exception e) {
            e.printStackTrace();
            base64Image = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC";
            return  base64Image; // return default image
        }



    }

    private static Bitmap removeExifOrientation(Bitmap bitmap, byte[] imageData) {
        try {
            ExifInterface exifInterface = new ExifInterface(String.valueOf(imageData));
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            int rotationDegrees = 0;

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationDegrees = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationDegrees = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationDegrees = 270;
                    break;
                default:
                    rotationDegrees = 0;
                    break;
            }

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationDegrees);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static  String convertToDateTime(String string,boolean isDate) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)");
        SimpleDateFormat outputFormat;
        if (isDate){
            outputFormat = new SimpleDateFormat("dd/MM HH:mm");
        }else {
            outputFormat = new SimpleDateFormat("HH:mm");
        }

        try {
            Date date = inputFormat.parse(string);
            String outputDate = outputFormat.format(date);
            return outputDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

}
