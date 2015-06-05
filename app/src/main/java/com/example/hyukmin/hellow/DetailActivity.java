package com.example.hyukmin.hellow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hyukmmin on 2015-06-01.
 */
public class DetailActivity extends Activity {

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childList_txt = null;
    private ArrayList<ArrayList<Integer>> childList_img = null;
    private ArrayList<String> childConName = null;
    private ArrayList<Integer> childConImg = null;
    private ExpandableListView ExListView;

    public String[] weatherName = {
            "맑음",
            "태풍",
            "태풍",
            "태풍",
            "태풍",
            "여우비",
            "여우비",
            "맑음",
            "맑음",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20"

    };

    Integer[] weatherImage = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7

    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        // 서버 통신

        // Header에 이름(임시)
        Intent in = getIntent();
        int id = in.getExtras().getInt("id");
        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText(""+id);

        // 오늘 날씨와 입수가능()
        ImageView swim_img = (ImageView) findViewById(R.id.swim_img);
        TextView swim_text = (TextView) findViewById(R.id.swim_text);
        ImageView today_img = (ImageView) findViewById(R.id.today_img);
        TextView today_text = (TextView) findViewById(R.id.today_text);

        swim_img.setImageResource(R.drawable.swim);
        swim_text.setText(R.string.swim);

        today_img.setImageResource(R.drawable.image3);
        today_text.setText(R.string.today_weather);

        setLayout();

        groupList = new ArrayList<String>();
        childList_txt = new ArrayList<ArrayList<String>>();
        childList_img = new ArrayList<ArrayList<Integer>>();
        childConName = new ArrayList<String>();
        childConImg = new ArrayList<Integer>();

        groupList.add("시간별날씨");
        groupList.add("주간별날씨");

        for (int i = 0;i < 9;i++){
            childConName.add("" + weatherName[i]);
            childConImg.add(weatherImage[i]);
        }
        childList_txt.add(childConName);
        childList_img.add(childConImg);

        /*
        childListContent.add("1");
        childListContent.add("2");
        childListContent.add("3");

        childList.add(childListContent);
        childList.add(childListContent);
        childList.add(childListContent);
        */
        ExListView.setAdapter(new ExpandableAdapter(this, groupList, childList_txt, childList_img));

        ExListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(getApplicationContext(), "g click = " + groupPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        ExListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), "c click = " + childPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ExListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });

        ExListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setLayout(){
        ExListView = (ExpandableListView)findViewById(R.id.elv_list);
    }

}
