package com.example.user.oldchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mina on 25/11/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, E_mail;
    String user, pass, mail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.username_reg);
        password = findViewById(R.id.pass_reg);
        E_mail = findViewById(R.id.email_reg);

        Firebase.setAndroidContext(this);

    }


    public void reg(View view) {
        user = username.getText().toString();
        pass = password.getText().toString();
        mail = E_mail.getText().toString();

        if (user.equals("")) {

            username.setError(getString(R.string.Blank_handle));
        } else if (pass.equals("")) {

            password.setError(getString(R.string.Blank_handle));
        } else if (mail.isEmpty()) {

            E_mail.setError(getString(R.string.Blank_handle));

        } else if (!isEmailValid(mail)) {


            E_mail.setError(getString(R.string.validemail)); //NON-NLS
        } else
            //noinspection HardCodedStringLiteral
            if (!user.matches("[A-Za-z0-9]+")) { //NON-NLS


                username.setError(getString(R.string.alphabet));
            } else if (user.length() < 5) {


                username.setError(getString(R.string.chars));
            } else if (pass.length() < 5) {


                password.setError(getString(R.string.chars));
            } else {
                final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);


                dialog.setMessage(getString(R.string.Loading___));
                dialog.show();

                String url = getString(R.string.base_url_json);

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        @SuppressWarnings("HardCodedStringLiteral") Firebase reference = new Firebase(getString(R.string.base_url) + getString(R.string.users));

                        if (s.equals("null")) {
                            reference.child(user).child(getString(R.string.password)).setValue(pass);
                            reference.child(user).child(getString(R.string.E_mail)).setValue(mail);
                            Toast.makeText(RegisterActivity.this, getString(R.string.regsucecess), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            try {
                                JSONObject obj = new JSONObject(s);

                                if (!obj.has(user)) {
                                    reference.child(user).child(getString(R.string.password)).setValue(pass);
                                    reference.child(user).child(getString(R.string.E_mail)).setValue(mail);

                                    Toast.makeText(RegisterActivity.this, getString(R.string.regsucecess), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                                } else {
                                    Toast.makeText(RegisterActivity.this, getString(R.string.usernamealreadyexists), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        dialog.dismiss();

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("" + volleyError);
                        //noinspection HardCodedStringLiteral
                        Toast.makeText(RegisterActivity.this, getString(R.string.toastinternetconnection), Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
                rQueue.add(request);
            }
    }


    //valideting email address
    public boolean isEmailValid(String email) {
        String expression = this.getString(R.string.expertion);
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}

