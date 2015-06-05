package com.example.hyukmin.hellow;

import android.app.Activity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] beachlist;
    private final Integer[] imageId;

    public CustomAdapter(Activity context, String[] beachlist, Integer[] imageId) {
        super(context, R.layout.beach_list, beachlist);
        this.context = context;
        this.beachlist = beachlist;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        /*
        if (view ==null) {
            view = inflater.inflate(R.layout.beach_list, null, true);
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.beach_list_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.beach_list_img);
        txtTitle = ViewHolderHelper.get(view, R.id.beach_list_text);

        return view;
        */

        View rowView= inflater.inflate(R.layout.beach_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.beach_list_text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.beach_list_img);

        txtTitle.setText(beachlist[position]);
        imageView.setImageResource(imageId[1]);
        return rowView;
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
}
