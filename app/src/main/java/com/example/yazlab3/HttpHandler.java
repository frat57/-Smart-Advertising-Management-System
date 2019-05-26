package com.example.yazlab3;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {
    public HttpHandler(){}
    public String makeServiceCall(String requestURL){
        String response =null;
        try {
            URL url = null;
            url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setRequestMethod("GET");
            InputStream in= new BufferedInputStream(connection.getInputStream());
            response = convertStreamToString(in);
        } catch (IOException e) {
            Log.e("HTTP GET:", e.toString());
        }
        return response;
    }
    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String satir = "";
        try{
            while((satir = reader.readLine())!=null){
                return satir;
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                is.close();
            }catch (IOException e)  {

            }
        }return satir;
    }

}
