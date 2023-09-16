package com.example.ndvi_test;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;


public class WelcomeActivity extends AppCompatActivity {
    ImageView imageView;
    Calendar cal;
    String year;
    String month;
    int month_int;
    String day;
    String hour;
    String minute;
    String second;
    String my_time_1;
    String my_time_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        imageView=(ImageView) findViewById(R.id.welcome);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limited();//时间限制获取到当前系统时间。判断在哪个月能用！
            }
        });

    }
    void limited() {
//        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
//        t.setToNow(); // 取得系统时间。
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        year = String.valueOf(cal.get(Calendar.YEAR));
        month = String.valueOf(cal.get(Calendar.MONTH));
        month_int=Integer.parseInt(month)+1;
        day = String.valueOf(cal.get(Calendar.DATE));
        int firstD = cal.getActualMinimum(cal.DAY_OF_MONTH);//这个月的第一天
        int lastD = cal.getActualMaximum(cal.DAY_OF_MONTH);//这个月的最后一天
        if (Integer.parseInt(String.valueOf(cal.get(Calendar.AM_PM)))==0) {
            hour = String.valueOf(cal.get(Calendar.HOUR));
        }
        else
        {hour = String.valueOf(cal.get(Calendar.HOUR)+12);}
        minute = String.valueOf(cal.get(Calendar.MINUTE));
        second = String.valueOf(cal.get(Calendar.SECOND));

        String time = year + "年 " + month_int  + "月 " + day + "日 "
                + hour + "时 " + minute + "分 " + second + "秒";
        Log.e("msg", time);
        int deadline=lastD-Integer.parseInt(day);//计算这个月剩余天数
        //if (t.year == 2014 && t.month + 1 == 9 && t.monthDay == 23) 测试
        if(Integer.parseInt(year) ==2023 && (month_int)==4&&Integer.parseInt(day)<=31)//只有这个月能用
        {
            Toast.makeText(WelcomeActivity.this," 欢迎试用，当前日期为: "+ time+"距离试用期结束还有："+deadline+"天" , Toast.LENGTH_LONG).show();
            Intent intent=new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else
        // if(t.year!=2014 || (t.month+1)!=9||t.monthDay!=23) //今天
        {
            Toast.makeText(WelcomeActivity.this,"试用版时间到自动退出，继续使用请联系软件作者QQ:2247274465" ,
                    Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable(){   //延迟执行
                @Override
                public void run(){
                    android.os.Process
                            .killProcess(android.os.Process
                                    .myPid()); // 终止线程
                }
            }, 3000);
        }
    }

}
