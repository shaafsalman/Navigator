package com.example.navigation_smd_7a.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.navigation_smd_7a.Model.Product;

import java.util.ArrayList;

public class ProductDao extends DBHelper {

    private static final String DATABASE_NAME = "products_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_PRICE = "price";

    private SQLiteDatabase db;

    public ProductDao(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TITLE + " TEXT NOT NULL, " +
                KEY_DATE + " TEXT NOT NULL, " +
                KEY_PRICE + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    // Open the database connection
    public void open() {
        db = this.getWritableDatabase();
    }

    // Close the database connection
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void insert(String title, String date, int price) {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_DATE, date);
        values.put(KEY_PRICE, price);
        db.insert(TABLE_NAME, null, values);
    }

    public void remove(int id) {
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Product> fetchAll() {
        ArrayList<Product> products = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            try {
                // Get column indices using getColumnIndexOrThrow
                int idIndex = cursor.getColumnIndexOrThrow(KEY_ID);
                int titleIndex = cursor.getColumnIndexOrThrow(KEY_TITLE);
                int dateIndex = cursor.getColumnIndexOrThrow(KEY_DATE);
                int priceIndex = cursor.getColumnIndexOrThrow(KEY_PRICE);

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idIndex);
                    String title = cursor.getString(titleIndex);
                    String date = cursor.getString(dateIndex);
                    int price = cursor.getInt(priceIndex);
                    products.add(new Product(id, title, date, price, ""));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return products;
    }
}

