package com.example.user.oldchat.chatRooms;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.oldchat.adabters.RecycleView;
import com.example.user.oldchat.models.Model;
import com.example.user.oldchat.R;
import com.example.user.oldchat.utilities.UserConstants;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${Mina} on 25/11/2017.
 */

public class GroubChatRoom extends AppCompatActivity {
    //Fields for out Views
    Button sendBtn;
    TextView receivedMsg;
    EditText sendMsg;

    String sb;
    public static ImageView sendButton;
    EditText messageArea;
    String users = "";

    public RecyclerView recyclerView;

    List<Model> q;
    RecycleView adabter;
    Firebase refreence;
    String roomName;
    String userName;
    String members;

    Firebase ref;
    List<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groubchat_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_chat_groub);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);

        Firebase.setAndroidContext(this);

        //noinspection HardCodedStringLiteral
        roomName = getIntent().getExtras().get(getString(R.string.roomname)).toString();
        //noinspection HardCodedStringLiteral
        userName = getIntent().getExtras().get(getString(R.string.username)).toString();

        TextView textView = (TextView) findViewById(R.id.groub_room);
        textView.setText(roomName);
        q = new ArrayList<Model>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_groub);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = new Firebase(getString(R.string.base_url) + getString(R.string.groubchat) + "/" + roomName);
        refreence = new Firebase(getString(R.string.base_url) + getString(R.string.groubchat) + "/" + roomName + getString(R.string.Mas));
        //On click listener on btn
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = messageArea.getText().toString();
                if (!(msg.equals(""))) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    //noinspection HardCodedStringLiteral
                    map.put(getString(R.string.name), userName);
                    //noinspection HardCodedStringLiteral
                    map.put(getString(R.string.message), msg);
                    refreence.push().setValue(map);

                }
            }
        });

        refreence.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                Map map = dataSnapshot.getValue(Map.class);
                // for (int i=0;i<dataSnapshot)
                String message_ = map.get(getString(R.string.message)).toString();
                String username_ = map.get(getString(R.string.name)).toString();


                if (username_.equals(UserConstants.username)) {

                    RecycleView.ty = 1;
                    q.add(new Model(username_, message_, 1));
                    adabter = new RecycleView(q);
                } else {
                    RecycleView.ty = 2;
                    q.add(new Model(username_, message_, 2));
                    adabter = new RecycleView(q);

                }
                recyclerView.setAdapter(adabter);
                recyclerView.scrollToPosition(q.size() - 1);


            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Map map = dataSnapshot.getValue(Map.class);
                // for (int i=0;i<dataSnapshot)
                //noinspection HardCodedStringLiteral
                members = map.get(getString(R.string.memebers)).toString();
                myList = new ArrayList<String>(Arrays.asList(members.split(" ")));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.members, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_mem:
                // Toast.makeText(this, "hello from chat", Toast.LENGTH_SHORT).show();
                pop_up_members();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void pop_up_members() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final TextView editText = new TextView(this);
        editText.setText(members);


        builder.setView(editText);
        //noinspection HardCodedStringLiteral
        builder.setTitle(getString(R.string.memberstitle));
        builder.show();
    }
}

