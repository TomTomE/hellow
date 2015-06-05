package com.example.hyukmin.hellow;

import android.app.Activity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListviewAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] beachlist;
    private final Integer[] imageId;
    public ListviewAdapter(Activity context, String[] beachlist, Integer[] imageId) {
        super(context, R.layout.beach_list, beachlist);
        this.context = context;
        this.beachlist = beachlist;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.beach_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.beach_list_text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.beach_list_img);

        txtTitle.setText(beachlist[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
