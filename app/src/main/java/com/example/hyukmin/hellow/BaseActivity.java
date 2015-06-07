package com.example.hyukmin.hellow;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by baesangjoon on 15. 6. 4..
 */
public class BaseActivity extends Activity{
    public String GetHttpResponseString(String location, Boolean is_post, String form_values) {
        String result = "";
        String input_line = null;

        try
        {
            InputStream stream = GetHttpResponseStream(location, is_post, form_values);
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader buffer = new BufferedReader(reader);

            while ((input_line = buffer.readLine()) != null) {
                result += input_line + "/n";
            }
            buffer.close();
            reader.close();
            stream.close();

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public InputStream GetHttpResponseStream(String location, Boolean is_post, String form_values) {
        InputStream stream = null;

        try {
            URL url = new URL(location);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(is_post ? "POST" : "GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setUseCaches(false);
            if (form_values != null) {
                Log.d("BaseActivity", form_values);
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(form_values.getBytes("utf-8"));
                os.flush();
                os.close();
            }
            stream = connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }
}
