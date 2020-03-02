package com.example.test3;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login_do extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_do);

        TextView username = (TextView)findViewById(R.id.name);
        TextView password = (TextView)findViewById(R.id.password);
        final TextView voice = (TextView)findViewById(R.id.voice);
        Button btnShow = (Button)findViewById(R.id.btnShow);
        Button btnHeigh = (Button)findViewById(R.id.btnHeigh);
        Button btnLower = (Button)findViewById(R.id.btnLower);

        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String pwd = intent.getStringExtra("pwd");
        username.setText("用户名：" + name);
        password.setText("密  码：" + pwd);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
                int b = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
                int c = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int d = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                voice.setText("通话音量：" + a + "\n系统音量：" + b + "\n音乐音量：" + c +"\n提示声音音量：" + d);
            }
        });

        btnHeigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
            }
        });

        btnLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
            }
        });
    }
}
