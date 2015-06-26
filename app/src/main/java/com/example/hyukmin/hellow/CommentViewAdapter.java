package com.example.hyukmin.hellow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

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
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "InterparkGothicLight.ttf");

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
        image.setImageResource(R.drawable.user);

        date.setText(calculateDate(context, paper.date));


        return view;
    }

    public static String calculateDate(Context context, String strDate) {

        StringBuilder sb = new StringBuilder();
        // 현재 날짜&시간 구하기
        Calendar today = Calendar.getInstance();
        // 시작 날짜&시간
        Calendar compareday = Calendar.getInstance();

        // 2014-11-12 11:12:13 (2014년 11월 12일 11시 12분 13초)
        // 1970-01-01T00:00:00.000+0000
        int getYear = Integer.parseInt(strDate.substring(0, 4));
        int getMonth = Integer.parseInt(strDate.substring(5, 7)) - 1; // 날짜는 인덱스이기때문에 11월이면 10이란값을 Calendar 클래스에 셋팅해줘야 11월로 인식
        int getDay = Integer.parseInt(strDate.substring(8, 10));
        int getHour = Integer.parseInt(strDate.substring(11, 13));
        int getMinute = Integer.parseInt(strDate.substring(14, 16));
        int getSecond = Integer.parseInt(strDate.substring(17, 19));
        // 위에 계산된 값을 셋팅
        compareday.set(getYear, getMonth, getDay, getHour, getMinute, getSecond);

        // 초단위로 계산
        long gapSec = (today.getTimeInMillis() - compareday.getTimeInMillis()) / 1000;
        // 지나간 Day 값 계산
        // long gapDay = gapSec / (60 * 60 * 24);
        // 지나간 Hour 값 계산
        // gapSec -= gapDay * (60 * 60 * 24);
        // long gapHour = gapSec / (60 * 60);
        // 지나간 Min 값 계산
        // gapSec -= gapHour * (60 * 60);
        // long gapMin = gapSec / 60;
        // 지나간 Sec 값 계산
        // gapSec -= gapMin * 60;

        String message = null;//String.format(msgPrompt, green_fee_min_value, green_fee_max_value);

        if(gapSec < 0) {
            Log.d("", "");
        }
        if(gapSec >= 0 && gapSec < 60) {
            String msgPrompt = context.getString(R.string.string_second_before_prompt);
            message = String.format(msgPrompt, gapSec);
        }
        else if(gapSec >= 60 && gapSec < 3600) {
            String msgPrompt = context.getString(R.string.string_minute_before_prompt);
            message = String.format(msgPrompt, gapSec/60);
        }
        else if(gapSec >= 3600 && gapSec < 86400) {
            String msgPrompt = context.getString(R.string.string_hour_before_prompt);
            message = String.format(msgPrompt, gapSec/3600);
        }
        else {
            String msgPrompt = context.getString(R.string.string_day_before_prompt);
            message = String.format(msgPrompt, getYear, getMonth, getDay);
        }
        sb.append(message);
        return sb.toString();
    }
}