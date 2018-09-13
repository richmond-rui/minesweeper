package com.lanlengran.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etWidth;
    private EditText etHeight;
    private EditText etSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        etWidth=findViewById(R.id.et_setting_width);
        etHeight=findViewById(R.id.et_setting_height);
        etSize=findViewById(R.id.et_setting_landmine_size);

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_exit){
            finish();
        }else if (v.getId()==R.id.btn_start){
            if (!TextUtils.isEmpty(etWidth.getText().toString().trim())){
                int width=Integer.parseInt(etWidth.getText().toString());
                if (width>10){
                    Toast.makeText(this,"由于手机屏幕原因，宽度不能大于10",Toast.LENGTH_SHORT).show();
                    return;
                }
                MainActivity.width= width;
            }else {
                MainActivity.width= 10;
            }
            if (!TextUtils.isEmpty(etHeight.getText().toString().trim())){
                MainActivity.height= Integer.parseInt(etHeight.getText().toString());
            }else {
                MainActivity.height=10;
            }
            if (!TextUtils.isEmpty(etSize.getText().toString().trim())){
                int size=Integer.parseInt(etSize.getText().toString());
                if (size<2){
                    Toast.makeText(this,"地雷数不能小于2",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (size>=MainActivity.width*MainActivity.height){
                    Toast.makeText(this,"地雷数太大",Toast.LENGTH_SHORT).show();
                    return;
                }
                MainActivity.landMineSize= size;
            }else {
                MainActivity.landMineSize=10;
            }
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
