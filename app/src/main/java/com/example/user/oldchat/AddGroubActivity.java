package com.example.user.oldchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class AddGroubActivity extends AppCompatActivity {

    public static List<String> mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_groub);
        AddGroubFragment addGroubFragment = new AddGroubFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.addgroubfragment, addGroubFragment).commit();
    }


//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putStringArrayList("qq", (ArrayList<String>) selectedItems);
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        selectedItems=savedInstanceState.getStringArrayList("qq");
//    }
}
