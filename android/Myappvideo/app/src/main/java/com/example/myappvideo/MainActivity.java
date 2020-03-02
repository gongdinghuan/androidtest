package com.example.myappvideo;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaplayer;
    private SurfaceView sview;
    private SurfaceHolder holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Button play = (Button)findViewById(R.id.play);
        final Button pause = (Button)findViewById(R.id.pause);
        Button stop = (Button)findViewById(R.id.stop);
        sview = (SurfaceView)findViewById(R.id.surfaceView2);
        mediaplayer = MediaPlayer.create(this,R.raw.mp0);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaplayer.setDataSource("/sdcard/mp0.mp4");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaplayer.setDisplay(sview.getHolder());
                holder =sview.getHolder();
                holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                mediaplayer.setDisplay(holder);
                mediaplayer.start();
                pause.setText("暂停");
                pause.setEnabled(true);
            }

        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaplayer.isPlaying()){
                    mediaplayer.pause();
                    ((Button)v).setText("继续");
                }else {
                    mediaplayer.start();
                    ((Button)v).setText("暂停");
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaplayer.isPlaying()){
                    mediaplayer.stop();
                    sview.setBackgroundResource(R.drawable.bg_finish);
                    pause.setEnabled(false);
                }
            }
        });

        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                sview.setBackgroundResource(R.drawable.bg_finish);
                Toast.makeText(MainActivity.this,"视频播放完毕",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy(){
        if(mediaplayer.isPlaying()){
            mediaplayer.stop();
        }
        mediaplayer.release();
        super.onDestroy();
    }
}
