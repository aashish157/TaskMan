package com.argbots.aashish.taskman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    //firebase auth class instances
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthLisner;
    //recyclerview
    ImageView open;
    FrameLayout content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


      //  content=findViewById(R.id.content);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);



        //set user pic, name and email id in the nav drawer header
        View headerView = navigationView.getHeaderView(0);
        TextView name = (TextView) headerView.findViewById((R.id.name));
        TextView email = (TextView) headerView.findViewById(R.id.email);
        ImageView img = (ImageView) headerView.findViewById(R.id.imageView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String Name = user.getDisplayName();
        String Email = user.getEmail();

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(img);
        name.setText(Name);
        email.setText(Email);



        navigationView.setNavigationItemSelectedListener(this);
        open=findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(navigationView))
                    drawer.closeDrawer(navigationView);
                else
                    drawer.openDrawer(navigationView);
            }
        });

        if(drawer.isDrawerOpen(navigationView))
            drawer.closeDrawer(navigationView);

        Fragment frag=null;

        try {
            frag=(Fragment) BlankFragment.class.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.content,frag);
        trans.commit();




        // check if user is logged in ,; sign him out if not logged in
        mAuth=FirebaseAuth.getInstance();
        mAuthLisner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                    startActivity(new Intent(dashboard.this,MainActivity.class));
            }
        };


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tasks) {
            // Handle the task action
        } else if (id == R.id.nav_reminders) {

        } else if (id == R.id.nav_about) {
            Intent in = new Intent(dashboard.this,about.class);
            startActivity(in);

        } else if (id == R.id.nav_logout) {
            mAuth=FirebaseAuth.getInstance();
            mAuth.signOut();
            //below lines are for transferring control to Main Activity when logged out
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    //recycler view







}
