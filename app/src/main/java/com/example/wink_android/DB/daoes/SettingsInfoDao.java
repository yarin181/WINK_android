package com.example.wink_android.DB.daoes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.wink_android.DB.entities.SettingsInfo;

@Dao
public interface SettingsInfoDao {
    @Insert
    void insert(SettingsInfo settingsInfo);

    @Query("SELECT * FROM settings_info LIMIT 1")
    SettingsInfo getSettingsInfo();

    @Query("UPDATE settings_info SET ip_address = :ipAddress, theme_status = :themeStatus WHERE id = :id")
    void updateSettingsInfo(String ipAddress, boolean themeStatus, int id);

   //delete settings info
    @Query("DELETE FROM settings_info")
    void deleteSettingsInfo();

}
