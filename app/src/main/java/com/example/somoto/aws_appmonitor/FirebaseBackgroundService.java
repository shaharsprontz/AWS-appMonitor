package com.example.somoto.aws_appmonitor;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import static android.content.ContentValues.TAG;


public class FirebaseBackgroundService extends Service {



    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("apps").child("condition");



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        ChildEventListener ValueEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String newComment = dataSnapshot.getValue(String.class);
                String commentKey = dataSnapshot.getKey();
                //Toast.makeText(FirebaseBackgroundService.this, "Child Added", Toast.LENGTH_LONG).show();
                createNotification();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                String newComment = dataSnapshot.getValue(String.class);
                String commentKey = dataSnapshot.getKey();
                //Toast.makeText(FirebaseBackgroundService.this, "Child Changed", Toast.LENGTH_LONG).show();
                createNotification();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String newComment = dataSnapshot.getValue(String.class);
                String commentKey = dataSnapshot.getKey();
                //Toast.makeText(FirebaseBackgroundService.this, "Child Removed", Toast.LENGTH_LONG).show();
                createNotification();
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

    private void createNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setContentTitle("AWS-Monitor")
                        .setContentText("Changes in database");
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Vibrate for 300 milliseconds
        v.vibrate(600);
        //star wars theme
        //v.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1);

    // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, MainActivity.class);

    // The stack builder object will contain an artificial back stack for the
    // started Activity.
    // This ensures that navigating backward from the Activity leads out of
    // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
    // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    // mId allows you to update the notification later on.
            mNotificationManager.notify(1111, mBuilder.build());
    }

}
