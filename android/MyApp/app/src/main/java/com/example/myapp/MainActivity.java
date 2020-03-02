package com.example.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.*;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=(EditText)findViewById(R.id.etName);
        tv=(TextView)findViewById(R.id.tvInfo);
    }

    public void checkLen(View v) {
        int len = et.getText().toString().length();

        if(len>5&&len<9) {
            tv.setText("用户名合法");
        }
        else {
            tv.setText("用户名不合法");
        }
    }
}
