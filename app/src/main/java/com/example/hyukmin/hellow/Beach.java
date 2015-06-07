package com.example.hyukmin.hellow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by baesangjoon on 15. 6. 7..
 */
public class Beach {
    public String _id;
    public String _name;
    public String _hits;

    public Beach(JSONObject object) {
        try {
            this._id = object.getString("_id");
            this._name = object.getString("_name");
            this._hits = object.getString("_hits");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Beach(String str) {
        this._name = str;
    }

    public static ArrayList<Beach> fromJSON(JSONArray jsonObjects) {
        ArrayList<Beach> beaches = new ArrayList<Beach>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                beaches.add(new Beach(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return beaches;
    }
}
