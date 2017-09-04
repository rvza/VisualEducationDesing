package com.tatlicilar.visualeducation;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Asus on 25.08.2017.
 */
//Connect email and password
//Package Name: "com.tatlicilar.visualeducation"
//SHA1: "e585a8159a1d65ff4b446cee38458918d7114e81"
public class InsertFirebaseData extends AppCompatActivity {
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference myRef=db.getReference();
    DatabaseReference write=FirebaseDatabase.getInstance().getReference().child("Users").child("1");

}
