package com.example.linkwave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linkwave.ExtraClass.User;
import com.firebase.client.Firebase;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class FirstPageActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;
    private NavigationView nv;
    // cRestaurant;
    CardView MapButton;
    Button VideoCalling;
    CardView MapButtonHospital;
    CardView MapButtonPolice;
    Button ContactButtomList;
    Button GroupChat;
    TextView mTitle;
    FirebaseDatabase myFirebaseDatabase_UserInfo;
    DatabaseReference myDatabaseReference_UserInfo_reference;
    final int MY_PERMISSIONS_REQUEST_LOCATION = 102;
    final int MY_PERMISSIONS_REQUEST_CONTACTLIST = 103;
    public static int LOCATION_TYPE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.Main_toobar);
        mTitle = (TextView) myToolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Welcome to LinkWave!");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle myActionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, myToolbar, R.string.Open_Nav_Drawer,R.string.Close_Nav_Drawer);
        myDrawerLayout.addDrawerListener(myActionBarDrawerToggle);
        myActionBarDrawerToggle.syncState();
        MapButton = findViewById(R.id.MapButton);
        VideoCalling = findViewById(R.id.VideoCalling);
        MapButtonHospital =  findViewById(R.id.MapButtonHospital);
        MapButtonPolice =  findViewById(R.id.MapButtonPolice);
        ContactButtomList = findViewById(R.id.ContactButtom);
        GroupChat = findViewById(R.id.GroupChatEnter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_var);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_logout){
                    FirebaseAuth.getInstance().signOut();

                    Intent it = new Intent(FirstPageActivity.this,LoginActivity.class);
                    it.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it);

                }
             /*   else if (menuItem.getItemId() == R.id.nav_Profile){
                    Toast.makeText(FirstPageActivity.this,"Not Work Profile",Toast.LENGTH_SHORT).show();
                }*/
                else if (menuItem.getItemId() == R.id.nav_Support){
                    Toast.makeText(FirstPageActivity.this,"This app was created by Emdadul Haque and Faraz Kabir",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });






        myFirebaseDatabase_UserInfo = FirebaseDatabase.getInstance();
        myDatabaseReference_UserInfo_reference = myFirebaseDatabase_UserInfo.getReference().child("USERS").child(FirebaseAuth.getInstance().getUid());
        myDatabaseReference_UserInfo_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User getUser = dataSnapshot.getValue(User.class);

                NavigationView navigationView = (NavigationView) findViewById(R.id.nv_var);
                View headerView = navigationView.getHeaderView(0);
                TextView navUsername = (TextView) headerView.findViewById(R.id.UserName);
                ImageView navImage = (ImageView) headerView.findViewById(R.id.profile_image);

                String link = getUser.getProfilePicLink();
                Picasso.get().load(link).into(navImage);
                navUsername.setText(getUser.getFirstname() + " "+getUser.getLastname());
                mTitle.setText("Hello " +getUser.getFirstname() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstPageActivity.LOCATION_TYPE = 1;
                LocationPermissionCheck(FirstPageActivity.LOCATION_TYPE);
            }
        });
        MapButtonHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstPageActivity.LOCATION_TYPE = 2;
                LocationPermissionCheck(FirstPageActivity.LOCATION_TYPE);
            }
        });
        MapButtonPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstPageActivity.LOCATION_TYPE = 3;
                LocationPermissionCheck(FirstPageActivity.LOCATION_TYPE);
            }
        });
        ContactButtomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactButtomListPrermission();

            }
        });
        VideoCalling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(FirstPageActivity.this,CallerActivity.class);
                startActivity(it);
            }
        });

        GroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FirstPageActivity.this,GroupChatRoomCreatActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(myDrawerLayout.isDrawerOpen(GravityCompat.START)){
            myDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            finish();
            super.onBackPressed();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When PERMISSION is GRANTED
                LocationPermissionCheck(FirstPageActivity.LOCATION_TYPE);
                //Intent it = new Intent(FirstPageActivity.this,NearbyPlaceActivity.class);
               // it.putExtra("NearestPlace_Type", "RESTAURENT");
               // startActivity(it);
               // finish();


            } else {

                Intent mySuperIntent = new Intent(this, ErrorActivity.class);
                mySuperIntent.putExtra("Error_Title", "Location");
                mySuperIntent.putExtra("Error_Des", "Location Permission Not given ");
                startActivity(mySuperIntent);


            }


        }
       else if (requestCode == MY_PERMISSIONS_REQUEST_CONTACTLIST) {


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent it = new Intent(FirstPageActivity.this,ContactListActivity.class);
                startActivity(it);
                finish();
            }
            else{
                Intent mySuperIntent = new Intent(this, ErrorActivity.class);
                mySuperIntent.putExtra("Error_Title", "Contact");
                mySuperIntent.putExtra("Error_Des", "Please give contact permission. ");
                startActivity(mySuperIntent);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void LocationPermissionCheck(int Type){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Do the main task

            //getLocation();

           // Intent myIntent = getIntent();
           // String place = myIntent.getExtras().getString("NearBy");

           // textView = findViewById(R.id.textView);
            if(Type == 1){
                Intent it = new Intent(FirstPageActivity.this,NearbyPlaceActivity.class);
                it.putExtra("NearestPlace_Type", "RESTAURANTS");
                startActivity(it);
                finish();
            }
            else if(Type == 2){
                Intent it = new Intent(FirstPageActivity.this,NearbyPlaceActivity.class);
                it.putExtra("NearestPlace_Type", "HOSPITALS");
                startActivity(it);
                finish();
            }
            else if(Type == 3){
                Intent it = new Intent(FirstPageActivity.this,NearbyPlaceActivity.class);
                it.putExtra("NearestPlace_Type", "POLICES");
                startActivity(it);
                finish();
            }


        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                Location_RequestPermission();

            } else {
                Location_RequestPermission();
            }

        }

    }

    public void Location_RequestPermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

    }
    void ContactButtomListPrermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {


            Intent it = new Intent(FirstPageActivity.this,ContactListActivity.class);
            startActivity(it);
            finish();



        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_CONTACTLIST);
                }

            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_CONTACTLIST);
                }
            }

        }
    }
}