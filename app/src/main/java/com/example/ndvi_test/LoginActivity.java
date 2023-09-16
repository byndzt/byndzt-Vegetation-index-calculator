package com.example.ndvi_test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private Button login,adddata;
    private EditText edit1,edit2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dbHelper = new MyDatabaseHelper(this,"aaa.db",null,1);
        edit1 = (EditText) findViewById(R.id.idd);
        edit2 = (EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db1=dbHelper.getWritableDatabase();
                Cursor cursor = db1.query("aaa", null, null,null,null,null,null);
                int a=0;
                if(cursor.moveToFirst()){
                    do{
                        String idd=cursor.getString(cursor.getColumnIndex("id"));
                        String pass=cursor.getString(cursor.getColumnIndex("pass"));
                        if(edit1.getText().toString().equals(idd) && edit2.getText().toString().equals(pass))
                            a=1;
                    }while (cursor.moveToNext());
                }
                cursor.close();
                if(a==1)
                {
                    edit2.setText("");
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), zhuye_Activity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"用户名或密码错误！",Toast.LENGTH_SHORT).show();
            }
        });

        adddata= (Button) findViewById(R.id.add_data);
        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
                Intent intent1 = new Intent();
                intent1.setClass(getApplicationContext(),RegisterActivity.class);
                startActivity(intent1);

            }
        });
    }
}