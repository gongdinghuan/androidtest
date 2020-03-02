package com.example.myapplicationw;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private WebView webView;

    private void openurl(String id){
        webView.loadUrl("https://tianqi.moji.com/weather/china/"+id+"/");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bj=(Button)findViewById(R.id.bj);
        bj.setOnClickListener(this);
        Button sh=(Button)findViewById(R.id.sh);
        sh.setOnClickListener(this);
        Button tj=(Button)findViewById(R.id.tj);
        tj.setOnClickListener(this);

        webView=(WebView)findViewById(R.id.WebView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());


        webView.loadUrl("https://tianqi.moji.com/weather/china/sichuan/mianyang");
        webView.setInitialScale(57*3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bj:
                openurl("beijing/beijing");
                break;
            case R.id.sh:
                openurl("shanghai/shanghai");
                break;
            case R.id.tj:
                openurl("tianjin/tianjin");
                break;


        }

    }
}
