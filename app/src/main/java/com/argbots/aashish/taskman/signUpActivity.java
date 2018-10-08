package com.argbots.aashish.taskman;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpActivity extends Activity {

    private FirebaseAuth mAuth;
    private EditText name;
    private EditText email;
    private EditText pass;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

         TextView appName = (TextView) findViewById(R.id.appName);
         name = (EditText) findViewById(R.id.name);
         email = (EditText) findViewById(R.id.email);
         pass = (EditText) findViewById(R.id.password);
         signUpBtn = (Button) findViewById(R.id.signUpBtn);
        TextView signUpTextView = (TextView) findViewById(R.id.signUpTextView);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SignUp();
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


    private void SignUp()
    {
           String memail = email.getText().toString();
           String mpass = pass.getText().toString();

           if(TextUtils.isEmpty(memail) || TextUtils.isEmpty(mpass))
           {
               Toast.makeText(signUpActivity.this,"Fields are empty",Toast.LENGTH_LONG);
           }
           else
           {
               mAuth.createUserWithEmailAndPassword(memail, mpass)
                       .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {
                                   startActivity(new Intent(signUpActivity.this,dashboard.class));
                               } else {
                                   Toast.makeText(signUpActivity.this,"Sign Up Problem",Toast.LENGTH_LONG);
                               }


                           }
                       });
           }
    }
}
