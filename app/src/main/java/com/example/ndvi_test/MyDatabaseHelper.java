package com.example.ndvi_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper  extends SQLiteOpenHelper {
    public static final String CREATE_aaa="create table aaa("
            +"id text primary key,"
            +"pass text)";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, 1);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db1) {
        db1.execSQL(CREATE_aaa);
        Toast.makeText(mContext, "Creat succeeded",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

}