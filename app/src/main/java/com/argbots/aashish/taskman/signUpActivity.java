package com.argbots.aashish.taskman;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
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
    private EditText name,email,pass,confirmPass;
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
         confirmPass = (EditText) findViewById(R.id.confirmPassword);
         signUpBtn = (Button) findViewById(R.id.signUpBtn);
         TextView signUpTextView = (TextView) findViewById(R.id.signUpTextView);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //blink animation
                Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                animation.setDuration(100); // duration - half a second
                animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
                animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
                signUpBtn.startAnimation(animation);

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
           String cpass = confirmPass.getText().toString();
           String uname = name.getText().toString();

           if(TextUtils.isEmpty(memail) || TextUtils.isEmpty(mpass) ||  TextUtils.isEmpty(cpass) || TextUtils.isEmpty(uname))
           {
               Toast.makeText(signUpActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
           }
           else
           {

               if(cpass.equals(mpass) && cpass.length()>=8 && mpass.length()>=8)
               {
                   mAuth.createUserWithEmailAndPassword(memail, mpass)
                           .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       startActivity(new Intent(signUpActivity.this,dashboard.class));
                                   } else {
                                       Toast.makeText(signUpActivity.this,"Sign Up Problem",Toast.LENGTH_LONG).show();
                                   }


                               }
                           });
               }
               else
               {
                   if(!cpass.equals(mpass))
                  Toast.makeText(signUpActivity.this,"Passwords not matching",Toast.LENGTH_LONG).show();
                   else
                   {
                       Toast.makeText(signUpActivity.this,"Passwords should be at least 8 characters long ",Toast.LENGTH_LONG).show();
                   }

               }

           }
    }
}
