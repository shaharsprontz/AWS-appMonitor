package com.example.somoto.aws_appmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MainActivity extends Activity {

    TextView mConditionTextView;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("apps").child("condition");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConditionTextView = (TextView) findViewById(R.id.downApps);
        mConditionTextView.setMovementMethod(new ScrollingMovementMethod());


        startService(new Intent(this, FirebaseBackgroundService.class));

    }

    @Override
    protected void onStart() {
        super.onStart();

        ChildEventListener ValueEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String newComment = dataSnapshot.getValue(String.class);
                mConditionTextView.setText(mConditionTextView.getText()+"\n"+newComment);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String newComment = dataSnapshot.getValue(String.class);
                mConditionTextView.setText(mConditionTextView.getText()+"\n"+newComment);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String newComment = dataSnapshot.getValue(String.class);
                mConditionTextView.setText(mConditionTextView.getText()+"\n"+newComment);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mConditionRef.addChildEventListener(ValueEventListener);
    }



}