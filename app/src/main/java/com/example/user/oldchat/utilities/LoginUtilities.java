package com.example.user.oldchat.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ${Mina} on 25/11/2017.
 */

public class LoginUtilities {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public void setUserData(Context context, String userName, String password) {
        //noinspection HardCodedStringLiteral
        sharedPreferences = context.getSharedPreferences("oldchat", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //noinspection HardCodedStringLiteral
        editor.putString("user", userName);
        //noinspection HardCodedStringLiteral
        editor.putString("pass", password);
        editor.commit();
    }

    public String getUserData(Context context) {
        //noinspection HardCodedStringLiteral
        sharedPreferences = context.getSharedPreferences("oldchat", MODE_PRIVATE);
        //noinspection HardCodedStringLiteral,HardCodedStringLiteral
        return sharedPreferences.getString("user", " ") + "--" + sharedPreferences.getString("pass", " ");
    }

}
