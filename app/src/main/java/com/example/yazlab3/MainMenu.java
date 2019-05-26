package com.example.yazlab3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainMenu extends AppCompatActivity implements FirmaAdapter.OnItemClickListener {
    private Spinner mySpinner;
    private Button b,m,search;
    private TextView lat1,long1,company,uzaklik;
    private LocationManager locationManager;
    private LocationListener listener;
    private double lat,lon;
    FirmaAdapter adapter;
    HttpHandler httpHandler;
    RecyclerView recyclerView;
    String urlPrefix ="https://firmareklam-565d1.firebaseapp.com/";
    private String URL;
    ArrayList<String> location = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ArrayList<String> names = new ArrayList<>();
        names.add(0, "Yemek");
        names.add("Giyim");
        names.add("Spor");

        lat1 = (TextView) findViewById(R.id.lat);
        long1 = (TextView) findViewById(R.id.long1);
        b = (Button) findViewById(R.id.auto);
        m = (Button) findViewById(R.id.manuel);
        company = (TextView)findViewById(R.id.company);
        search = (Button) findViewById(R.id.search);
        uzaklik = (TextView)findViewById(R.id.uzaklik);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LoadJson("api/list");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mySpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainMenu.this, android.R.layout.simple_list_item_1, names);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Yemek")){
                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if((company.getText().toString().equals("") && uzaklik.getText().toString().equals("")) && (lat1.getText().toString().equals("") || long1.getText().toString().equals(""))){
                                LoadJson("api/list/yemek");
                            }

                             else if(company.getText().toString().equals("") && uzaklik.getText().toString().equals("")){
                                 LoadJson("api/list/yemek");
                             }
                             else if(company.getText().toString().equals("") && lat1.getText().toString().equals("") && long1.getText().toString().equals("")){
                                 Toast.makeText(getApplicationContext(), "Gps i açın ya da manuel giriş yapın", Toast.LENGTH_LONG).show();
                                // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                             }
                             else if(company.getText().toString().equals("")){
                                 String latim = lat1.getText().toString();
                                 String lotum = long1.getText().toString();
                                 double lat = Double.parseDouble(String.valueOf(latim));
                                 double lot = Double.parseDouble(String.valueOf(lotum));
                                 String rangem =uzaklik.getText().toString();
                                 int range = Integer.parseInt(String.valueOf(rangem));
                                 LoadJson2("api/list/yemek",lat,lot,range);
                             }
                             else if(uzaklik.getText().toString().equals("")){
                                 LoadJson3("api/list/yemek",company.getText().toString());
                             }
                            else{
                                 String latim = lat1.getText().toString();
                                 String lotum = long1.getText().toString();
                                 double lat = Double.parseDouble(String.valueOf(latim));
                                 double lot = Double.parseDouble(String.valueOf(lotum));
                                 String rangem =uzaklik.getText().toString();
                                 int range = Integer.parseInt(String.valueOf(rangem));
                                 LoadJson1("api/list/yemek",company.getText().toString(),lat,lot,range);
                                /* for (int i = 0; i < location.size(); i++) {
                                     String[] location1 = location.get(i).split(",");
                                     double location2 =Double.parseDouble(String.valueOf(location1[i]));
                                     double location3 =Double.parseDouble(String.valueOf(location1[i+1]));
                                     double a = UzaklıkHesapla(lat,lot,location2,location3);
                                     if(range >= a){

                                     }
                                 }*/
                            }
                        }
                    });
                }
                else if(parent.getItemAtPosition(position).equals("Giyim")){
                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if((company.getText().toString().equals("") && uzaklik.getText().toString().equals("")) && (lat1.getText().toString().equals("") || long1.getText().toString().equals(""))){
                                LoadJson("api/list/giyim");
                            }

                            else if(company.getText().toString().equals("") && uzaklik.getText().toString().equals("")){
                                LoadJson("api/list/giyim");
                            }
                            else if(company.getText().toString().equals("") && lat1.getText().toString().equals("") && long1.getText().toString().equals("")){
                                Toast.makeText(getApplicationContext(), "Gps in açılmasını bekleyin ya da manuel giriş yapın", Toast.LENGTH_LONG).show();
                                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                            }
                            else if(company.getText().toString().equals("")){
                                String latim = lat1.getText().toString();
                                String lotum = long1.getText().toString();
                                double lat = Double.parseDouble(String.valueOf(latim));
                                double lot = Double.parseDouble(String.valueOf(lotum));
                                String rangem =uzaklik.getText().toString();
                                int range = Integer.parseInt(String.valueOf(rangem));
                                LoadJson2("api/list/giyim",lat,lot,range);
                            }
                            else if(uzaklik.getText().toString().equals("")){
                                LoadJson3("api/list/giyim",company.getText().toString());
                            }
                            else{
                                String latim = lat1.getText().toString();
                                String lotum = long1.getText().toString();
                                double lat = Double.parseDouble(String.valueOf(latim));
                                double lot = Double.parseDouble(String.valueOf(lotum));
                                String rangem =uzaklik.getText().toString();
                                int range = Integer.parseInt(String.valueOf(rangem));
                                LoadJson1("api/list/giyim",company.getText().toString(),lat,lot,range);
                                /* for (int i = 0; i < location.size(); i++) {
                                     String[] location1 = location.get(i).split(",");
                                     double location2 =Double.parseDouble(String.valueOf(location1[i]));
                                     double location3 =Double.parseDouble(String.valueOf(location1[i+1]));
                                     double a = UzaklıkHesapla(lat,lot,location2,location3);
                                     if(range >= a){

                                     }
                                 }*/
                            }

                        }
                    });
                }
                else if (parent.getItemAtPosition(position).equals("Spor")){
                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if((company.getText().toString().equals("") && uzaklik.getText().toString().equals("")) && (lat1.getText().toString().equals("") || long1.getText().toString().equals(""))){
                                LoadJson("api/list/spor");
                            }

                            else if(company.getText().toString().equals("") && uzaklik.getText().toString().equals("")){
                                LoadJson("api/list/spor");
                            }
                            else if(company.getText().toString().equals("") && lat1.getText().toString().equals("") && long1.getText().toString().equals("")){
                                Toast.makeText(getApplicationContext(), "Gps in açılmasını bekleyin ya da manuel giriş yapın", Toast.LENGTH_LONG).show();
                                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                            }
                            else if(company.getText().toString().equals("")){
                                String latim = lat1.getText().toString();
                                String lotum = long1.getText().toString();
                                double lat = Double.parseDouble(String.valueOf(latim));
                                double lot = Double.parseDouble(String.valueOf(lotum));
                                String rangem =uzaklik.getText().toString();
                                int range = Integer.parseInt(String.valueOf(rangem));
                                LoadJson2("api/list/spor",lat,lot,range);
                            }
                            else if(uzaklik.getText().toString().equals("")){
                                LoadJson3("api/list/spor",company.getText().toString());
                            }
                            else{
                                String latim = lat1.getText().toString();
                                String lotum = long1.getText().toString();
                                double lat = Double.parseDouble(String.valueOf(latim));
                                double lot = Double.parseDouble(String.valueOf(lotum));
                                String rangem =uzaklik.getText().toString();
                                int range = Integer.parseInt(String.valueOf(rangem));
                                LoadJson1("api/list/spor",company.getText().toString(),lat,lot,range);
                                /* for (int i = 0; i < location.size(); i++) {
                                     String[] location1 = location.get(i).split(",");
                                     double location2 =Double.parseDouble(String.valueOf(location1[i]));
                                     double location3 =Double.parseDouble(String.valueOf(location1[i+1]));
                                     double a = UzaklıkHesapla(lat,lot,location2,location3);
                                     if(range >= a){

                                     }
                                 }*/
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                 lat = location.getLongitude();
                 lon = location.getLatitude();
                lat1.setText("\n " + lat);
                long1.setText("\n " + lon);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions

        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission

                if (ActivityCompat.checkSelfPermission(MainMenu.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainMenu.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                                , 10);
                    }
                    return;
                }
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainMenu.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainMenu.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                                , 10);
                    }
                    return;
                }
                Location location = new Location("");
                location.setLatitude(lat);
                location.setLongitude(lon);
            }
        });
    }
    public void LoadJson3(String tur,String filter){
        ArrayList<Firmam> firmamArrayList = new ArrayList<>();
        try {
            URL =urlPrefix+tur;
            httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(URL);
            Log.d("JSON_RESPONSE", jsonString);
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //Log.e("object", object.toString());
                Iterator<String> keys = object.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
                    if (object.get(key) instanceof JSONObject) {
                        if(filter.equals(((JSONObject) object.get(key)).getString("firmaAdi"))){
                            Log.e("object", object.toString());
                            String lokasyon = ((JSONObject) object.get(key)).getString("firmaLokasyon");
                            String name = ((JSONObject) object.get(key)).getString("firmaAdi");
                            String sure = ((JSONObject) object.get(key)).getString("kampanyaSure");
                            String type = ((JSONObject) object.get(key)).getString("kampanyaIcerik");
                            int id = ((JSONObject) object.get(key)).getInt("firmaID");
                            Firmam firmalarim = new Firmam(id,lokasyon,name,sure,type);
                            location.add(lokasyon);
                            firmamArrayList.add(firmalarim);

                            //Recycler View i fitreden sonraki search te çaliştir.
                            adapter = new FirmaAdapter(this, firmamArrayList, this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void LoadJson2(String tur,double location2,double location3,int range){
        ArrayList<Firmam> firmamArrayList = new ArrayList<>();
        try {
            URL =urlPrefix+tur;
            httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(URL);
            Log.d("JSON_RESPONSE", jsonString);
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //Log.e("object", object.toString());
                Iterator<String> keys = object.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
                    if (object.get(key) instanceof JSONObject) {
                            Log.e("object", object.toString());
                            String lokasyon = ((JSONObject) object.get(key)).getString("firmaLokasyon");
                            String name = ((JSONObject) object.get(key)).getString("firmaAdi");
                            String sure = ((JSONObject) object.get(key)).getString("kampanyaSure");
                            String type = ((JSONObject) object.get(key)).getString("kampanyaIcerik");
                            int id = ((JSONObject) object.get(key)).getInt("firmaID");
                            Firmam firmalarim = new Firmam(id,lokasyon,name,sure,type);
                            String[] location1 = lokasyon.split(",");
                            double lat2 =Double.parseDouble(String.valueOf(location1[i]));
                            double lot2 =Double.parseDouble(String.valueOf(location1[i+1]));
                            double a = UzaklıkHesapla(location2,location3,lat2,lot2);
                            location.add(lokasyon);
                            if(range >= a){
                                firmamArrayList.add(firmalarim);

                                //Recycler View i fitreden sonraki search te çaliştir.
                                adapter = new FirmaAdapter(this, firmamArrayList, this);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void LoadJson1(String tur,String filter,double location2,double location3,int range){
        ArrayList<Firmam> firmamArrayList = new ArrayList<>();
        try {
            URL =urlPrefix+tur;
            httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(URL);
            Log.d("JSON_RESPONSE", jsonString);
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //Log.e("object", object.toString());
                Iterator<String> keys = object.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
                    if (object.get(key) instanceof JSONObject) {
                        if(filter.equals(((JSONObject) object.get(key)).getString("firmaAdi"))){
                            Log.e("object", object.toString());
                        String lokasyon = ((JSONObject) object.get(key)).getString("firmaLokasyon");
                        String name = ((JSONObject) object.get(key)).getString("firmaAdi");
                        String sure = ((JSONObject) object.get(key)).getString("kampanyaSure");
                        String type = ((JSONObject) object.get(key)).getString("kampanyaIcerik");
                        int id = ((JSONObject) object.get(key)).getInt("firmaID");
                        Firmam firmalarim = new Firmam(id,lokasyon,name,sure,type);
                            String[] location1 = lokasyon.split(",");
                            double lat2 =Double.parseDouble(String.valueOf(location1[i]));
                            double lot2 =Double.parseDouble(String.valueOf(location1[i+1]));
                            double a = UzaklıkHesapla(location2,location3,lat2,lot2);
                            location.add(lokasyon);
                            if(range >= a){
                                firmamArrayList.add(firmalarim);

                        //Recycler View i fitreden sonraki search te çaliştir.
                        adapter = new FirmaAdapter(this, firmamArrayList, this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
    } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void LoadJson(String tur){
        ArrayList<Firmam> firmamArrayList = new ArrayList<>();
        try {
            URL =urlPrefix+tur;
            httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(URL);
            Log.d("JSON_RESPONSE", jsonString);
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //Log.e("object", object.toString());
                Iterator<String> keys = object.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
                    if (object.get(key) instanceof JSONObject) {
                            Log.e("object", object.toString());
                            String lokasyon = ((JSONObject) object.get(key)).getString("firmaLokasyon");
                            String name = ((JSONObject) object.get(key)).getString("firmaAdi");
                            String sure = ((JSONObject) object.get(key)).getString("kampanyaSure");
                            String type = ((JSONObject) object.get(key)).getString("kampanyaIcerik");
                            int id = ((JSONObject) object.get(key)).getInt("firmaID");
                            Firmam firmalarim = new Firmam(id,lokasyon,name,sure,type);
                            firmamArrayList.add(firmalarim);
                            //location.add(lokasyon);
                            //Recycler View i fitreden sonraki search te çaliştir.
                            adapter = new FirmaAdapter(this, firmamArrayList, this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
        @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private static double DereceToRadyan(double deg){
        return (deg * Math.PI / 180.0);
    }
    private static double RadyanToDerece(double rad){
        return (rad / Math.PI * 180.0);
    }
    public double UzaklıkHesapla(double lat1,double lon1,double lat2,double lon2){
        double R = 6371 * 1000;
        double deltaLat = DereceToRadyan(lat2-lat1);
        double deltaLon = DereceToRadyan(lon2-lon1);
        double a = Math.sin(deltaLat / 2 ) * Math.sin(deltaLat / 2 )
                + Math.cos (DereceToRadyan(lat1)) * Math.cos(DereceToRadyan(lat2))
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 *Math.asin(Math.min(1, Math.sqrt(a)));
        double d = R * c;
        return d;
    }
}
