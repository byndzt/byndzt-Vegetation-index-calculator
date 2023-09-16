package com.example.ndvi_test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.math.BigDecimal;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Administrator on 2023/2/27.
 */

public class zhuye_Activity extends Activity {
    private static final int PICK_IMAGE = 1;
    private ImageView imageView;
    private String value;
    private int Flag;
    private double grayValue;
    private double red_TO_double;
    private double green_TO_double;
    private double nir_TO_double;
    Button Calculate,nir_open,red_open,green_open;
    EditText nir,red,green;
    TextView output;
    private double output_value;
    private String tishi_value;
    private String shiqi[] = new String[]{"NDVI","GNDVI","NDGI","RVI","DVI","GCI","RDVI","NLI"};
    private Spinner spinner1;
    private int shiqi_value;
    private int shiqi_index;
    ArrayAdapter<String> adapter1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuye);
        spinner1 = (Spinner) findViewById(R.id.spn);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,shiqi);
        spinner1.setAdapter(adapter1);
        Calculate=(Button) findViewById(R.id.Calculate);
        nir_open=(Button) findViewById(R.id.nir_open);
        red_open=(Button) findViewById(R.id.red_open);
        green_open=(Button) findViewById(R.id.green_open);
        nir=(EditText)findViewById(R.id.nir);
        red=(EditText)findViewById(R.id.red);
        green=(EditText)findViewById(R.id.green);
        output=(TextView) findViewById(R.id.output);

        ////////////////////////////////////////////////////////////////////
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                shiqi_index=position;
                shiqi_value=shiqi_index;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        nir_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag=1;

                Toast.makeText(zhuye_Activity.this,"正在打开相册，请选择您的图片..." , Toast.LENGTH_LONG).show();
                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });
        red_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag=2;
                Toast.makeText(zhuye_Activity.this,"正在打开相册，请选择您的图片..." , Toast.LENGTH_LONG).show();
                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);

            }
        });
        green_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag=3;
                Toast.makeText(zhuye_Activity.this,"正在打开相册，请选择您的图片..." , Toast.LENGTH_LONG).show();
                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);

            }
        });

        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(zhuye_Activity.this,"已点击,正在计算！",Toast.LENGTH_SHORT).show();

                red_TO_double=Double.valueOf(red.getText().toString());
                green_TO_double=Double.valueOf(green.getText().toString());
                nir_TO_double=Double.valueOf(nir.getText().toString());
                /**
                 * 接下来进行计算
                 */
                if (shiqi_value == 0) {
                    output_value=(nir_TO_double-red_TO_double)/(nir_TO_double+red_TO_double);

                    output.setText("NDVI值为： "+output_value);
                }
                if (shiqi_value == 1) {
                    output_value=(nir_TO_double-green_TO_double)/(nir_TO_double+green_TO_double);

                    output.setText("GNDVI值为： "+output_value);
                }
                if (shiqi_value == 2) {
                    output_value=(green_TO_double-red_TO_double)/(green_TO_double+red_TO_double);

                    output.setText("NDGI值为： "+output_value);
                }
                if (shiqi_value == 3) {
                    output_value=(nir_TO_double)/(red_TO_double);

                    output.setText("RVI值为： "+output_value);
                }
                if (shiqi_value == 4) {
                    output_value=(nir_TO_double-red_TO_double);

                    output.setText("DVI值为： "+output_value);
                }
                if (shiqi_value == 5) {
                    output_value=(nir_TO_double)/(green_TO_double);

                    output.setText("GCI值为： "+output_value);
                }
                if (shiqi_value == 6) {
                    output_value=Math.pow((nir_TO_double-red_TO_double)/(nir_TO_double+red_TO_double)*(nir_TO_double-red_TO_double),2);

                    output.setText("RDVI值为： "+output_value);
                }
                if (shiqi_value == 7) {
                    output_value=(nir_TO_double*nir_TO_double-red_TO_double)/(nir_TO_double*nir_TO_double+red_TO_double);

                    output.setText("NDVI值为： "+output_value);
                }
            }
        });
    }
    /**
     * 计算图像灰度值
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // 获取选中的图片的URI
            Uri imageUri = data.getData();
            try {
                // 将URI转换为Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(imageUri));
                // 在ImageView中显示选中的图片
                //imageView.setImageBitmap(bitmap);
                // 计算灰度值
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                double graySum = 0;
                for (int i = 0; i < pixels.length; i++) {
                    int color = pixels[i];
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = color & 0xff;
                    double gray = 0.299 * red + 0.587 * green + 0.114 * blue;
                    graySum += gray;
                }
                grayValue = graySum / (width * height);
                Toast.makeText(this, "灰度值为：" + grayValue, Toast.LENGTH_SHORT).show();
                value= String.valueOf(grayValue);
                if(Flag==1)
                {
                    nir.setText(value);
                }
                if(Flag==2)
                {
                    red.setText(value);
                }
                if(Flag==3)
                {
                    green.setText(value);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "出现错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

}