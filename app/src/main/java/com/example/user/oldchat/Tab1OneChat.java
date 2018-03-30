package com.example.user.oldchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.oldchat.chatRooms.PersonalChatRoom;
import com.example.user.oldchat.utilities.LoginUtilities;
import com.example.user.oldchat.utilities.UserConstants;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Mina on 25/11/2017.
 */

public class Tab1OneChat extends Fragment {
    String user, pass;
    public ListView ContactsList;

    private int position = -1;

    public static List<String> listUsers;
    ArrayAdapter<String> adaptero;

    DatabaseReference databaseReferenceq;
    FirebaseJobDispatcher dispatcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {
        View rootView1 = inflater.inflate(R.layout.chat_tabone, container, false);

        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getContext()));
        listUsers = new ArrayList<>();

        user = new LoginUtilities().getUserData(getActivity());
        ContactsList = (ListView) rootView1.findViewById(R.id.listViewG);

        final String[] userData = user.split("--");
        user = userData[0];
        //pass = userData[1];

        databaseReferenceq = FirebaseDatabase.getInstance().getReference(getString(R.string.users));
        databaseReferenceq.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Map map = (Map)dataSnapshot.getValue();
                String m = dataSnapshot.getKey();

                if (!m.equals(UserConstants.username)) {
                    listUsers.add(m);

                }
                ContactsList.setVisibility(View.VISIBLE);
                adaptero = new ArrayAdapter<String>(getContext(), simple_list_item_1, listUsers);
                ContactsList.setAdapter(adaptero);
                if (savedInstanceState != null) {

                    position = savedInstanceState.getInt(getString(R.string.Sone));
                    ContactsList.smoothScrollToPosition(position);
                    ContactsList.setSelection(position);
                }

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


        @SuppressWarnings("HardCodedStringLiteral") Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class) // the JobService that will be called
                .setTag("tag")
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(60, 60 * 2))
                .setReplaceCurrent(false)// uniquely identifies the job
                .build();

        dispatcher.mustSchedule(myJob);

        ContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  dispatcher.cancelAll();

                UserConstants.chatWith = listUsers.get(position);
                Intent intent = new Intent(getActivity(), PersonalChatRoom.class);

                startActivity(intent);

            }
        });


        //update widget
        /*
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(getActivity(), ChatWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetContactsList);
*/

        return rootView1;


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int scrollPosition = ContactsList.getFirstVisiblePosition();
        outState.putInt(getString(R.string.Sone), scrollPosition);

    }
}
