package com.example.test3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText username = (EditText)findViewById(R.id.name);
        final EditText password = (EditText)findViewById(R.id.pwd);
        Button login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", username.getText().toString());
                intent.putExtra("pwd", password.getText().toString());
                intent.setClass(MainActivity.this, login_do.class);
                startActivity(intent);
            }
        });
    }
}
