package com.example.wink_android.DB;

import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
public class Converters {
    @TypeConverter
    public static String fromIntegerList(List<Integer> integers) {
        if (integers == null) {
            return null;
        }

        JSONArray jsonArray = new JSONArray();
        for (Integer integer : integers) {
            jsonArray.put(integer);
        }
        return jsonArray.toString();
    }

    @TypeConverter
    public static List<Integer> toIntegerList(String value) {
        if (value == null) {
            return null;
        }

        List<Integer> integers = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(value);
            for (int i = 0; i < jsonArray.length(); i++) {
                integers.add(jsonArray.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return integers;
    }
}
