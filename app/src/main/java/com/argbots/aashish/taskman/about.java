package com.argbots.aashish.taskman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView backBtn = (ImageView) findViewById(R.id.backBtn);
        TextView aboutText = (TextView) findViewById(R.id.about_text);
        String txt = "TaskMan is a Task Manager mobile application." +
                " People tend to forget their day to day tasks frequently " +
                "owing to different personal reasons. This application would " +
                "simply remind them of each of their future tasks and alert " +
                "them so that they don’t forget.\n ";

        aboutText.setText(txt);

        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(about.this,dashboard.class);
                startActivity(intent);
            }
        });
    }
}
