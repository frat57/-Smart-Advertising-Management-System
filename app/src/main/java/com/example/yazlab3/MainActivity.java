package com.example.yazlab3;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    private Button register,giris,pw_change;
    private TextView user_id,password,fake;
    private String message;
    private String URL;
    HttpHandler httpHandler;
    ArrayList<Firmam> firmamArrayList;
    String urlPrefix ="https://firmareklam-565d1.firebaseapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        giris = (Button)findViewById(R.id.giris);
        register = (Button)findViewById(R.id.register);
        pw_change = (Button)findViewById(R.id.pw_change);
        user_id = (TextView) findViewById(R.id.user_id);
        password = (TextView) findViewById(R.id.password);
        //LoadJson("api/list");
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        if(user_id.getText().toString().equals("") || password.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Bütün alanları doldurun", Toast.LENGTH_LONG).show();
                            return;
                        }
                        URL =urlPrefix+"getInfo/"+ user_id.getText().toString() + "/" + password.getText().toString();
                        httpHandler = new HttpHandler();
                        String jsonString = httpHandler.makeServiceCall(URL);
                        JSONArray array = new JSONArray(jsonString);
                        JSONObject object = array.getJSONObject(0);
                        Iterator<String> keys = object.keys();
                        String key = keys.next();
                        String message = object.getString(key);
                        if(message.equals("error:user not exists")){
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                        else if(message.equals("error:wrong username-password")){
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                        else {
                            openMainMenu();
                        }
                        }catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterMenu();
            }
        });
        pw_change.setOnClickListener(new View.OnClickListener() {
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
    public void openMainMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
    public void openRegisterMenu(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    public void LoadJson(String tur){
        ArrayList<Firmam> firmamArrayList = new ArrayList<>();
        try {
            URL =urlPrefix+tur ;
            httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(URL);
            Log.d("JSON_RESPONSE", jsonString);
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Log.e("object", object.toString());
                Iterator<String> keys = object.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
                    if (object.get(key) instanceof JSONObject) {
                        String lokasyon = ((JSONObject) object.get(key)).getString("firmaLokasyon");
                        String name = ((JSONObject) object.get(key)).getString("firmaAdi");
                        String sure = ((JSONObject) object.get(key)).getString("kampanyaSure");
                        String type = ((JSONObject) object.get(key)).getString("kampanyaIcerik");
                        int id = ((JSONObject) object.get(key)).getInt("firmaID");
                        Firmam firmalarim = new Firmam(id,lokasyon,name,sure,type);
                        firmamArrayList.add(firmalarim);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
