package com.example.user.oldchat.chatRooms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.user.oldchat.adabters.RecycleView;
import com.example.user.oldchat.models.Model;
import com.example.user.oldchat.R;
import com.example.user.oldchat.utilities.UserConstants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${Mina} on 25/11/2017.
 */

public class PersonalChatRoom extends AppCompatActivity {

    public RecyclerView recyclerView;
    RecycleView adabter;

    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    List<Model> listOfMessages;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_chat_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_chat_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textView = (TextView) findViewById(R.id.chat_user);
        textView.setText(UserConstants.chatWith);


        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        listOfMessages = new ArrayList<Model>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Firebase.setAndroidContext(this);
        //noinspection HardCodedStringLiteral
        reference1 = new Firebase(getString(R.string.base_url) + getString(R.string.message_url) + UserConstants.username + "_" + UserConstants.chatWith);
        //noinspection HardCodedStringLiteral
        reference2 = new Firebase(getString(R.string.base_url) + getString(R.string.message_url) + UserConstants.chatWith + "_" + UserConstants.username);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!messageArea.getText().toString().trim().isEmpty()) {
                    String messageText = messageArea.getText().toString();

                    if (!messageText.equals("")) {
                        Map<String, String> map = new HashMap<String, String>();
                        //noinspection HardCodedStringLiteral
                        map.put(getString(R.string.message), messageText);
                        //noinspection HardCodedStringLiteral
                        map.put(getString(R.string.user), UserConstants.username);
                        //  map.put("date",dateFormat.format(date));
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);

                    }
                }
            }
        });


        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Map map = dataSnapshot.getValue(Map.class);

                String message_ = map.get(getString(R.string.message)).toString();
                String username_ = map.get(getString(R.string.user)).toString();


                if (username_.equals(UserConstants.username)) {

                    RecycleView.ty = 1;
                    listOfMessages.add(new Model(username_, message_, 1));
                    adabter = new RecycleView(listOfMessages);
                } else {
                    RecycleView.ty = 2;
                    listOfMessages.add(new Model(username_, message_, 2));
                    adabter = new RecycleView(listOfMessages);

                }
                recyclerView.setAdapter(adabter);
                recyclerView.scrollToPosition(listOfMessages.size() - 1);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Model newPost = dataSnapshot.getValue(Model.class);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // throw databaseError.toException();

            }

        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
