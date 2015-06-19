package com.example.hyukmin.hellow;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by baesangjoon on 15. 6. 4..
 */
public class BaseActivity extends Activity{
    public static final String DEBUG_TAG_HTTP = "HTTP - Beach List";
    public static final String DEBUG_TAG_SEARCH = "Search - Beach List";
    public static final String DEBUG_TAG_VIEW = "DEBUG_TAG_VIEW";

    public static final String SERVER_IP = "14.50.109.225";
    public static final String SERVER_PORT = "9000";

    private static Typeface mTypeface;

    public String GetHttpResponseString(String location, Boolean is_post, JSONObject form_values) {
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

    public InputStream GetHttpResponseStream(String location, Boolean is_post, JSONObject form_values) {
        InputStream stream = null;

        try {
            URL url = new URL(location);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(is_post ? "POST" : "GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setUseCaches(false);
            if (form_values != null) {
                Log.d("BaseActivity", form_values.toString());
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(form_values.toString().getBytes("utf-8"));
                os.flush();
                os.close();
            }
            stream = connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }

    // 글꼴 적용 관련
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (BaseActivity.mTypeface == null)
            BaseActivity.mTypeface = Typeface.createFromAsset(getAssets(), "InterparkGothicLight.ttf");

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        setGlobalFont(root);
    }

    // 글꼴 적용 관련
    void setGlobalFont(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof TextView)
                ((TextView)child).setTypeface(mTypeface);
            else if (child instanceof ViewGroup)
                setGlobalFont((ViewGroup)child);
        }
    }


}
