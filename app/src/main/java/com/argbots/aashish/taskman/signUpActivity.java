package com.argbots.aashish.taskman;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class signUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView appName = (TextView) findViewById(R.id.appName);
        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.password);
        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        TextView signUpTextView = (TextView) findViewById(R.id.signUpTextView);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
