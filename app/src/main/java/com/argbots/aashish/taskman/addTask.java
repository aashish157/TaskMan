package com.argbots.aashish.taskman;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;


public class addTask extends AppCompatActivity {

    FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();

    private static final String TAG = "Aashish";

    TextView textFile;
    private static final int PICKFILE_RESULT_CODE = 1;

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
                SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yy");
                Date date = new Date();
                String currentTime = formatter.format(date);
                String toc = formatter2.format(date);


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
                    user.put("tid", "t" + currentTime);
                    user.put("uid", u.getUid());
                    user.put("email", u.getEmail());
                    user.put("name", u.getDisplayName());
                    user.put("task",task);
                    user.put("toc",toc);

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



        // set action for onclick of attach file imageview
        ImageView attachFile = (ImageView) findViewById(R.id.attachFile);
        textFile = (TextView) findViewById(R.id.textFile);

        attachFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();
                    textFile.setText(FilePath);
                }
                break;

        }
    }


}
