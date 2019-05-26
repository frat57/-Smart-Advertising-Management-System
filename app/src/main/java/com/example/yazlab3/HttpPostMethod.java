package com.example.yazlab3;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpPostMethod {
    String urlString;
    String params;

    public HttpPostMethod(String url) {
        this.urlString = url;
        this.params = params;
    }

    public void postMethod() {
        try {
            byte[] postData = urlString.getBytes( StandardCharsets.UTF_8 );
            URL voteURL = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) voteURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", "" +Integer.toString(postData.length));
            conn.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlString);
            wr.flush ();
            wr.close ();

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

        }catch (Exception e) {
            Log.e("Exception in View POST", urlString);
        }
    }


}
