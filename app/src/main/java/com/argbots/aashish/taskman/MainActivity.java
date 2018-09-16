package com.argbots.aashish.taskman;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        TextView loginActivityText = (TextView) findViewById(R.id.loginTextView);

        //onclick login button function
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        //onclick login activity text function
        loginActivityText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.taskman.signUpActivity");
                startActivity(intent);
            }
        });
    }
}
