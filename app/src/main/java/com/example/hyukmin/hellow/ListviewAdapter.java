package com.example.hyukmin.hellow;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//TODO Holder Pattern 적용할것.
public class ListviewAdapter extends ArrayAdapter<Beach> {

    private final Context context;
    private final ArrayList<Beach> beachlist;
    private final int rowResourceID;
    private static final String DEBUG = "ListView - TEST";

    public ListviewAdapter(Context context, ArrayList<Beach> beachlist, int rowResourceID) {
        super(context, rowResourceID, beachlist);

        this.context = context;
        this.beachlist = beachlist;
        this.rowResourceID = rowResourceID;
        Log.d(DEBUG, "Construct");
    }

    //
//    public ListviewAdapter(Activity context, String[] beachlist, Integer[] imageId) {
//        super(context, R.layout.beach_list, beachlist);
//        this.context = context;
//        this.beachlist = beachlist;
//        this.imageId = imageId;
//    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.d(DEBUG, "getView");
        //View v = view;
        if (view == null) {
            //LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v = vi.inflate(this.rowResourceID, null);

            view = LayoutInflater.from(getContext()).inflate(this.rowResourceID, parent, false);
        }

        Beach item = beachlist.get(position);

        if (item != null) {
            TextView txtTitle = (TextView) view.findViewById(R.id.beach_list_text);
            //ImageView imageView = (ImageView) view.findViewById(R.id.beach_list_img);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "InterparkGothicMedium.ttf"); //폰트 적용
            if(txtTitle != null) {
                txtTitle.setTypeface(typeface); //폰트 적용
                txtTitle.setText(item._name);
            }
        }
        return view;

        //LayoutInflater inflater = context.getLayoutInflater();
        /*
        if (view ==null) {
            view = inflater.inflate(R.layout.beach_list, null, true);
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.beach_list_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.beach_list_img);
        txtTitle = ViewHolderHelper.get(view, R.id.beach_list_text);

        return view;
        */

//        View rowView= inflater.inflate(R.layout.beach_list, null, true);
//
//        TextView txtTitle = (TextView) rowView.findViewById(R.id.beach_list_text);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.beach_list_img);
//
//        txtTitle.setText(beachlist[position]);
//        imageView.setImageResource(imageId[1]);
//        return rowView;

    }
/*
    public static class ViewHolderHelper {
        public static <T extends View> T get(View convertView, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
            if (viewHolder ==null) {
                viewHolder = new SparseArray<View>();
                convertView.setTag(viewHolder);
            }

            View childView = viewHolder.get(id);

            if (childView == null) {
                childView = convertView.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }

    }
*/
    public void updateList(){
        Log.d(DEBUG, "updateList");
        notifyDataSetChanged();
    }


}
