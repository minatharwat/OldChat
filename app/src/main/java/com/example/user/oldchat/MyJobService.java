package com.example.user.oldchat;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.widget.Toast;

import com.example.user.oldchat.utilities.UserConstants;
import com.example.user.oldchat.widget.ChatWidgetProvider;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.user.oldchat.Tab1OneChat.listUsers;

/**
 * Created by ${Mina} on 27/11/2017.
 */


public class MyJobService extends JobService {
    DatabaseReference databaseReferenceq;

    @Override
    public boolean onStartJob(JobParameters job) {
        // Do some work here


        //pass = userData[1];
        //noinspection HardCodedStringLiteral
        databaseReferenceq = FirebaseDatabase.getInstance().getReference(getString(R.string.users));
        databaseReferenceq.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Map map = (Map)dataSnapshot.getValue();
                String m = dataSnapshot.getKey();
                try {

                    if (listUsers.isEmpty()) {
                        if (!m.equals(UserConstants.username)) {
                            listUsers.add(m);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                        new ComponentName(getApplicationContext(), ChatWidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetContactsList);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(getApplicationContext(), ChatWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetContactsList);


        //noinspection HardCodedStringLiteral
        Toast.makeText(this, getString(R.string.toasthello), Toast.LENGTH_SHORT).show();

        return true; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        return false; // Answers the question: "Should this job be retried?"
    }
}
