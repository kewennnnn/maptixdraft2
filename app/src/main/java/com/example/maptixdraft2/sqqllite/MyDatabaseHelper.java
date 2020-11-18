package com.example.maptixdraft2.sqqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private  static final String DATABASE_NAME = "GroceryList.db"; //database name
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "my_list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY = "grocery_category";
    public static final String COLUMN_ITEM = "grocery_item";
    public static final String COLUMN_QTY = "grocery_qty";

    //TABLE_NAME, COLUMN_ID,COLUMN_CATEGORY,COLUMN_ITEM,COLUMN_QTY
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
        this.context = context;
    }

//    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }
//
//
//    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
//        super(context, name, version, openParams);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("TAG" , "create database");
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CATEGORY + " TEXT, " +
                        COLUMN_ITEM + " TEXT, " +
                        COLUMN_QTY + " INTEGER);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //function to add to database
    void add_grocery(String category, String item , int quantity) {
        SQLiteDatabase db = this.getWritableDatabase(); //get data repository in write mode

        //create new map of values, where column keys are the name
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_ITEM, item);
        cv.put(COLUMN_QTY, quantity);

        long result = db.insert(TABLE_NAME, null , cv); //insert row data to sql data

        if(result == 0) {  //application failed to insert the data
            Toast.makeText(context, "Failed to store" , Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Success" , Toast.LENGTH_LONG).show();
        }
    }
}
