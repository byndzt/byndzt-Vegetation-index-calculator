package com.example.ndvi_test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    public MyDatabaseHelper dbHelper;
    private Button zc;
    private Button back;
    private EditText edit1,edit2,edit3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edit1= (EditText) findViewById(R.id.zc1);
        edit2= (EditText) findViewById(R.id.zc2);
        edit3= (EditText) findViewById(R.id.zc3);

        zc = (Button) findViewById(R.id.buttonzc);
        dbHelper=new MyDatabaseHelper(this,"aaa.db",null,1);
        zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit1.equals("")){
                    SQLiteDatabase db1=dbHelper.getWritableDatabase();
                    Cursor cursor = db1.query("aaa", null, null,null,null,null,null);
                    int a=0;
                    if(cursor.moveToFirst()){
                        do{
                            String idd=cursor.getString(cursor.getColumnIndex("id"));
                            if(edit1.getText().toString().equals(idd))
                            {
                                a=1;
                            }
                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                    if(edit2.getText().toString().equals(edit3.getText().toString())&&a==0)
                    {
                        ContentValues values = new ContentValues();
                        values.put("id",edit1.getText().toString());
                        values.put("pass",edit2.getText().toString());
                        db1.insert("aaa",null,values);
                        finish();
                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    }
                    else if (a==1)
                        Toast.makeText(getApplicationContext(),"账号已存在,请重新输入账号",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"输入账号为空！",Toast.LENGTH_SHORT).show();
            }
        });

        back = (Button) findViewById(R.id.buttonzc2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}