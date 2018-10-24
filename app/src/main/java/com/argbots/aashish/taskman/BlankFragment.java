package com.argbots.aashish.taskman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class BlankFragment extends Fragment {


    private List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button addTaskBtn;
    private NotesAdapter mAdapter;
    private ConstraintLayout constraintLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_blank, container, false);

        recyclerView =  v.findViewById(R.id.recycler_view);
        constraintLayout=v.findViewById(R.id.constraint_Layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //open popup_menu on long press



        //Set Action of Add Task Btn Onclick
        addTaskBtn = v.findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(),addTask.class);
                startActivity(in);
            }
        });



        preparenoteData();
        enableSwipeToDeleteAndUndo();
        return v;
    }


    private void preparenoteData() {
        Log.e("called","yes");
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //DocumentReference myDocRef = db.collection(u.getEmail()).document("t024715121018");

        /*myDocRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){
                    String task = documentSnapshot.getString("task");
                    Note note = new Note(task,"2018");
                    Log.e("Notes", task);
                    noteList.add(note);

                    recyclerView.setAdapter(mAdapter);
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });*/

        db.collection(u.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String noteContent = document.getString("task");
                                String timeOfCreation = document.getString("toc");
                                String taskId = document.getString("tid");

                                Note note = new Note(noteContent,timeOfCreation,taskId);
                                Log.e("Notes", noteContent);
                                noteList.add(note);

                                recyclerView.setAdapter(mAdapter);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


//        Note note = new Note("Mad Max: Fury Road", "2015");
//        noteList.add(note);
//
//        note = new Note("Inside Out", "2015");
//        noteList.add(note);
//
//        note = new Note("Star Wars: Episode VII - The Force Awakens", "2015");
//        noteList.add(note);
//
//        note = new Note("Shaun the Sheep", "2015");
//        noteList.add(note);
//
//        note = new Note("The Martian", "2015");
//        noteList.add(note);
//
//        note = new Note("Mission: Impossible Rogue Nation", "2015");
//        noteList.add(note);
//
//        note = new Note("Up", "2009");
//        noteList.add(note);
//
//        note = new Note("Star Trek", "2009");
//        noteList.add(note);
//
//        note = new Note("Mission Impossible Fallout", "2018");
//        noteList.add(note);
//
//        note = new Note("The NUN", "2018");
//        noteList.add(note);
//
//        note = new Note("Avengers:Infinity War", "2018");
//        noteList.add(note);
//
//        note = new Note("Venom", "2018");
//        noteList.add(note);

        mAdapter = new NotesAdapter(noteList,getContext());

        recyclerView.setAdapter(mAdapter);

    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Note item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);

                //delete the task from Firestore
                FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection(u.getEmail()).document(item.getTid())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });




                Snackbar snackbar = Snackbar
                        .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

}
