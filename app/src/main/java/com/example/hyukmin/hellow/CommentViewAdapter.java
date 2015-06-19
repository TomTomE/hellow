package com.example.hyukmin.hellow;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hyukmmin on 2015-06-05.
 */
public class CommentViewAdapter extends ArrayAdapter<Paper> {
    private final Activity context;
    private ArrayList<Paper> papers;
    private final int rowResourceID;
    public static final String DEBUG_TAG_VIEW = "DEBUG_TAG_VIEW";

    public CommentViewAdapter(Activity context, ArrayList<Paper> papers, int rowResourceID) {

        super(context, rowResourceID, papers);
        this.context = context;
        this.papers = papers;
        this.rowResourceID = rowResourceID;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Paper paper = papers.get(position);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "InterparkGothicBold.ttf");

        if (view == null) {
            Log.d(DEBUG_TAG_VIEW, "create View");
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(this.rowResourceID, parent, false);
        }

        TextView content = ViewHolderHelper.get(view, R.id.comment_list_text);
        TextView date = ViewHolderHelper.get(view, R.id.comment_text_date);
        ImageView image = ViewHolderHelper.get(view, R.id.comment_list_img);

        content.setTypeface(tf);
        date.setTypeface(tf);
        Log.d(DEBUG_TAG_VIEW, "getView");
        content.setText(paper.contents);
        date.setText(paper.date);

        return view;

        /*
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.comment_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.comment_list_text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.comment_list_img);
        TextView txtDate = (TextView) rowView.findViewById(R.id.comment_text_date);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "InterparkGothicBold.ttf");
        */



        /*
        Random ran = new Random();
        // 댓글 수만큼 이미지 리스트 랜덤 구성
        for (int i = 0; i<=comment_Img.length;i++){

            while (0<=comment_list.length) {
                Integer index = (Integer) ran(comment_Img.hashCode());
                Integer ran_img = (Integer) comment_Img[index];
            }
        }
        */

        /*
        txtTitle.setTypeface(typeface);
        txtDate.setTypeface(typeface);

        txtTitle.setText(comment_list[position]);
        Log.d("comment_Img[position]", ""+comment_Img[position]);
        imageView.setImageResource(comment_Img[position]);
        txtDate.setText("2015/06/07");

        */



    }


}