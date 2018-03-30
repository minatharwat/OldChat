package com.example.user.oldchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.oldchat.utilities.LoginUtilities;
import com.example.user.oldchat.utilities.UserConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


    }


    public void login(View view) {
        user = username.getText().toString();
        pass = password.getText().toString();

        if (user.equals("")) {
            username.setError(getString(R.string.Blank_handle));

        } else if (pass.equals("")) {
            password.setError(getString(R.string.Blank_handle));
        } else {
            String url = getString(R.string.base_url_json);
            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            //noinspection HardCodedStringLiteral
            pd.setMessage(getString(R.string.Loading___));
            pd.show();

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if (s.equals("null")) {

                        Toast.makeText(LoginActivity.this, getString(R.string.toast2unf), Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            JSONObject obj = new JSONObject(s);

                            if (!obj.has(user)) {

                                Toast.makeText(LoginActivity.this, getString(R.string.toast3unfpr), Toast.LENGTH_LONG).show();
                            } else if (obj.getJSONObject(user).getString(getString(R.string.password)).equals(pass)) {
                                UserConstants.username = user;
                                UserConstants.password = pass;

                                new LoginUtilities().setUserData(LoginActivity.this, user, pass);

                                startActivity(new Intent(LoginActivity.this, HomeChat.class));
                            } else {

                                Toast.makeText(LoginActivity.this, getString(R.string.toastincorrectpassword), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    pd.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                    // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

                    Toast.makeText(LoginActivity.this, getString(R.string.toastinternetconnection), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
            rQueue.add(request);
        }


    }


    public void register(View view) {

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

    }
}
