package com.argbots.aashish.taskman;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileAct extends Activity {

    ImageView imageView;
    TextView textName,textEmail;
    FirebaseAuth mAuth;
    Button SignOutBut;
    FirebaseAuth.AuthStateListener mAuthLisner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();
        mAuthLisner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                    startActivity(new Intent(ProfileAct.this,MainActivity.class));
            }
        };

        imageView=findViewById(R.id.imgView);
        textName=findViewById(R.id.textViewName);
        textEmail=findViewById(R.id.textViewEmail);
        SignOutBut=(Button)findViewById(R.id.button2);

        FirebaseUser user=mAuth.getCurrentUser();

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(imageView);

        textName.setText(user.getDisplayName());
        textEmail.setText(user.getEmail());

        SignOutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });


    }

    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthLisner);
    }
}
