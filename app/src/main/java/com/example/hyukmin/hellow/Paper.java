package com.example.hyukmin.hellow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by baesangjoon on 15. 6. 15..
 */
public class Paper {
    public String contents;
    public String date;

    public Paper(String contents, String date) {
        this.contents = contents;
        this.date = date;
    }

    public Paper(JSONObject obj) {
        try {
            this.contents = obj.getString("contents");
            this.date = obj.getString("date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Paper> fromJSON(JSONObject obj) {
        ArrayList<Paper> papers = new ArrayList<>();
        try {
            JSONArray temp = obj.getJSONArray("papers");

            for (int i = 0; i < temp.length(); i++) {
                papers.add(new Paper(temp.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return papers;
    }
}
