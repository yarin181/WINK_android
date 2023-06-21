package com.example.wink_android.DB.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings_info")
public class SettingsInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "ip_address")
    private String ipAddress;

    @ColumnInfo(name = "theme_status")
    private boolean themeStatus;

    public SettingsInfo(String ipAddress, boolean themeStatus) {
        this.ipAddress = ipAddress;
        this.themeStatus = themeStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean getThemeStatus() {
        return themeStatus;
    }

    public void setThemeStatus(boolean themeStatus) {
        this.themeStatus = themeStatus;
    }
}
