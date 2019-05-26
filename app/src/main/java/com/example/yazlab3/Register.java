package com.example.yazlab3;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Register extends AppCompatActivity {
    private TextView user_id,password;
    private Button giris;
    private String username,pw;
    private String URL;
    private String urlPrefix ="https://firmareklam-565d1.firebaseapp.com/";
    HttpHandler httpHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        giris = (Button)findViewById(R.id.giris);
        user_id = (TextView) findViewById(R.id.user_id);
        password = (TextView) findViewById(R.id.password);

            giris.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (user_id.getText().toString().equals("") || password.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Bütün alanları doldurun", Toast.LENGTH_LONG).show();
                            return;
                        }
                        URL = urlPrefix + "postuser/" + user_id.getText().toString() + "/" + password.getText().toString();
                        new HttpPostMethod(URL).postMethod();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });


    }
}
