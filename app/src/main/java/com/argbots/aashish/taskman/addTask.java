package com.argbots.aashish.taskman;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class addTask extends AppCompatActivity {

    FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();

    private static final String TAG = "Aashish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);



        //Set action for onlick of back arrow imageview
        ImageView backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //blink animation
                Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                animation.setDuration(100); // duration 100ms
                animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
                animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in




                // ACCESS CLOUD FIRESTORE START //


                // Generate cur. date time for note ID
                SimpleDateFormat formatter = new SimpleDateFormat("HHmmssddMMyy");
                Date date = new Date();
                String currentTime = formatter.format(date);

                //input from EditText field of addTask Layout
                TextView taskNote = (TextView) findViewById(R.id.taskNote);
                String task = taskNote.getText().toString();

                if(!task.isEmpty()){
                    DocumentReference mDocRef = FirebaseFirestore.getInstance().collection(u.getEmail()).document("t"+currentTime);

                    /*mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                String noteId = documentSnapshot.getString("noteId");
                            }
                        }
                    });*/



                    // Create a new note with fields uid,email,name,task,toc
                    Map<String, Object> user = new HashMap<>();
                    user.put("uid", u.getUid());
                    user.put("email", u.getEmail());
                    user.put("name", u.getDisplayName());
                    user.put("task",task);
                    user.put("toc",currentTime);

                    mDocRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Document has been saved!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Document was not saved!",e);
                        }
                    });
                    ;
                }


                // ACCESS CLOUD FIRESTORE END //


                //Transfer control to dashboard onclick of back arrow imageview
                Intent in = new Intent(addTask.this,dashboard.class);
                startActivity(in);


            }
        });




    }
}
