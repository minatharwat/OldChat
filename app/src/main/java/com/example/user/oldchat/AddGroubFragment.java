package com.example.user.oldchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.oldchat.models.GroubModel;
import com.example.user.oldchat.utilities.LoginUtilities;
import com.example.user.oldchat.utilities.UserConstants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.user.oldchat.HomeChat.selector;

/**
 * Created by Mina on 26/11/2017.
 */

public class AddGroubFragment extends Fragment {

    private String user, pass;
    public ListView ContactsList;
    public static List<Integer> checkedPositions;

    List<GroubModel> s;
    String id;
    int totalUsers = 0;
    String groubName = "";
    DatabaseReference databaseReference;
    List<String> users;
    public static List<String> selectedItems = new ArrayList<String>();
    private ArrayAdapter<String> adaptero;

    public final int x = 0;

    static String sb;
    // ListView listView;
    public static List<String> itemso = new ArrayList<>();
    int counter;

    Button create;
    Button cancle;

    private DatabaseReference databaseReferenceq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_groub_fragment, container, false);

        users = new ArrayList<>();

        user = new LoginUtilities().getUserData(getActivity());
        ContactsList = (ListView) v.findViewById(R.id.listView_checked);
        ContactsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        create = (Button) v.findViewById(R.id.create_groub_btn);
        cancle = (Button) v.findViewById(R.id.fabov);

        checkedPositions = new ArrayList<Integer>();

        final String[] userData = user.split("--");
        user = userData[0];
        //pass = userData[1];
        //noinspection HardCodedStringLiteral
        databaseReferenceq = FirebaseDatabase.getInstance().getReference(getString(R.string.users));
        databaseReferenceq.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Map map = (Map)dataSnapshot.getValue();
                String m = dataSnapshot.getKey();

                if (!m.equals(UserConstants.username)) {
                    users.add(m);

                }
                ContactsList.setVisibility(View.VISIBLE);
                adaptero = new ArrayAdapter<String>(getContext(), R.layout.user_item, R.id.txt_choice, users);
                ContactsList.setAdapter(adaptero);
                if (savedInstanceState != null) {
                    checkedPositions = savedInstanceState.getIntegerArrayList(getString(R.string.fra));
                    for (int i = 0; i < ContactsList.getCount(); i++) {

                        if (checkedPositions.contains(i)) {
                            ContactsList.setItemChecked(i, true);
                        }
                    }
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


        ContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = ((TextView) view).getText().toString();

                if (selectedItems.contains(selected) && checkedPositions.contains(position)) {

                    selectedItems.remove(selected);
                    checkedPositions.remove(position);
                } else {
                    selectedItems.add(selected);
                    checkedPositions.add(position);


                }

            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestGroubName();


            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selector = 2;
                Intent intent = new Intent(getActivity(), HomeChat.class);
                startActivity(intent);
            }
        });
        return v;
    }


    public void RequestGroubName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText editText = new EditText(getActivity());
        builder.setView(editText);
        builder.setTitle(getString(R.string.enter_title));
        builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                groubName = editText.getText().toString();

                if (!TextUtils.isEmpty(groubName)) {
                    requestFirebase(groubName);


                } else {
                    editText.setError(getString(R.string.Blank_handle));
                    // RequestGroubName();


                }
                dialog.dismiss();
            }
        });

        //noinspection HardCodedStringLiteral
        builder.setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void requestFirebase(String groubNamee) {

        counter = 1;
        for (String item : selectedItems) {
            itemso.add(item);
            counter++;
        }
        Collections.sort(itemso, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {

                return s1.compareToIgnoreCase(s2);
            }
        });

        sb = "";
        for (String s : itemso) {
            sb += s + " ";

        }

        itemso.clear();
        if (counter == 1 || counter == 2) {

            //noinspection HardCodedStringLiteral
            Toast.makeText(getActivity(), getString(R.string.toast1), Toast.LENGTH_LONG).show();

        } else {
////Todo

            final String c = counter + "";
            //noinspection HardCodedStringLiteral
            databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.groubchat));
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //noinspection HardCodedStringLiteral
                    databaseReference.child(groubName).child(getString(R.string.memebers)).setValue("" + sb + UserConstants.username);
                    //noinspection HardCodedStringLiteral
                    databaseReference.child(groubName).child(getString(R.string.no_memebers)).setValue(c);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        selector = 2;
        Intent intent = new Intent(getActivity(), HomeChat.class);
        // intent.putExtra("flag_choice","sasa");
        startActivity(intent);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntegerArrayList(getString(R.string.fra), (ArrayList<Integer>) checkedPositions);

    }

}
