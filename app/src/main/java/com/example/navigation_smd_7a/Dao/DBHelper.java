package com.example.navigation_smd_7a.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public abstract class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade
        db.execSQL("DROP TABLE IF EXISTS " + getTableName());
        onCreate(db);
    }

    protected abstract String getTableName();

    @Override
    public abstract void onCreate(SQLiteDatabase db);
}
