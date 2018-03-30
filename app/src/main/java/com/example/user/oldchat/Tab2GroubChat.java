package com.example.user.oldchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.oldchat.chatRooms.GroubChatRoom;
import com.example.user.oldchat.utilities.LoginUtilities;
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

public class Tab2GroubChat extends Fragment {

    public ListView groubusersList;

    private int position = -1;
    FloatingActionButton floatingActionButton;
    List<String> al;
    String user, pass;
    ArrayAdapter<String> adapter;

    DatabaseReference databaseReferenceq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView1 = inflater.inflate(R.layout.chat_tabgroub, container, false);
        floatingActionButton = (FloatingActionButton) rootView1.findViewById(R.id.fab_gr);
        al = new ArrayList<>();

        user = new LoginUtilities().getUserData(getActivity());
        groubusersList = (ListView) rootView1.findViewById(R.id.listView_gr);

        final String[] userData = user.split("--");
        user = userData[0];
        pass = "000000";
        //noinspection HardCodedStringLiteral
        databaseReferenceq = FirebaseDatabase.getInstance().getReference(getString(R.string.groubchat));
        databaseReferenceq.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Map map = (Map)dataSnapshot.getValue();
                String m = dataSnapshot.getKey();

                al.add(m);
                groubusersList.setVisibility(View.VISIBLE);
                if (getActivity() != null) {
                    adapter = new ArrayAdapter<String>(getActivity(), simple_list_item_1, al);
                    groubusersList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                if (savedInstanceState != null) {

                    position = savedInstanceState.getInt(getString(R.string.Sgroub));
                    groubusersList.smoothScrollToPosition(position);
                    groubusersList.setSelection(position);
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


        groubusersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), GroubChatRoom.class);
                //noinspection HardCodedStringLiteral
                intent.putExtra(getString(R.string.roomname), ((TextView) view).getText().toString());
                //noinspection HardCodedStringLiteral
                intent.putExtra(getString(R.string.username), user);
                startActivity(intent);

            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddGroubActivity.class);
                startActivity(intent);
            }
        });


        return rootView1;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int scrollPosition = groubusersList.getFirstVisiblePosition();
        outState.putInt(getString(R.string.Sgroub), scrollPosition);

    }
}
