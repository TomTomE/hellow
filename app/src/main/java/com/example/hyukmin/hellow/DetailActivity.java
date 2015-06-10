package com.example.hyukmin.hellow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
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
    private ScrollView main_SV;
    private ScrollView sub_SV;
    private ListView comment_LV;

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

    public String[] comment_list = {
            "거기 날씨 어때요?",
            "개구림",
            "낙산 쪽은 괜찮아요",
            "ㅎㅎ",
            "저거 거짓말",
            "뭐가 진짜임?",
            "날씨 좋아용~",
            "입수는 가능한가요?",
            "네 다들 잘놀고 있어요~",
            "숙소는 어디가 좋아요?",
            "이 앞에 널린게 숙소임",
            "아하",
            "ㅋㅋㅋㅋㅋㅋ",
            "거기 날씨 어때요?",
            "개구림",
            "낙산 쪽은 괜찮아요",
            "ㅎㅎ",
            "저거 거짓말",
            "뭐가 진짜임?",
            "날씨 좋아용~",
            "입수는 가능한가요?",
            "네 다들 잘놀고 있어요~",
            "숙소는 어디가 좋아요?",
            "이 앞에 널린게 숙소임",
            "아하",
            "ㅋㅋㅋㅋㅋㅋ",
            "거기 날씨 어때요?",
            "개구림",
            "낙산 쪽은 괜찮아요",
            "ㅎㅎ",
            "저거 거짓말",
            "뭐가 진짜임?",
            "날씨 좋아용~",
            "입수는 가능한가요?",
            "네 다들 잘놀고 있어요~",
            "숙소는 어디가 좋아요?",
            "이 앞에 널린게 숙소임",
            "아하",
            "ㅋㅋㅋㅋㅋㅋ",
            "거기 날씨 어때요?",
            "개구림",
            "낙산 쪽은 괜찮아요",
            "ㅎㅎ",
            "저거 거짓말",
            "뭐가 진짜임?",
            "날씨 좋아용~",
            "입수는 가능한가요?",
            "네 다들 잘놀고 있어요~",
            "숙소는 어디가 좋아요?",
            "이 앞에 널린게 숙소임",
            "아하",
            "ㅋㅋㅋㅋㅋㅋ",
            "거기 날씨 어때요?",
            "개구림",
            "낙산 쪽은 괜찮아요",
            "ㅎㅎ",
            "저거 거짓말",
            "뭐가 진짜임?",
            "날씨 좋아용~",
            "입수는 가능한가요?",
            "네 다들 잘놀고 있어요~",
            "숙소는 어디가 좋아요?",
            "이 앞에 널린게 숙소임",
            "아하",
            "ㅋㅋㅋㅋㅋㅋ",
            "거기 날씨 어때요?",
            "개구림",
            "낙산 쪽은 괜찮아요",
            "ㅎㅎ",
            "저거 거짓말",
            "뭐가 진짜임?",
            "날씨 좋아용~",
            "입수는 가능한가요?",
            "네 다들 잘놀고 있어요~",
            "숙소는 어디가 좋아요?",
            "이 앞에 널린게 숙소임",
            "아하",
            "ㅋㅋㅋㅋㅋㅋ"
    };

    Integer[] comment_Img = {
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1
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

        swim_img.setImageResource(R.drawable.swim1);
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


        int num = 0;
        while (num<groupList.size()) {
            childList_txt.add(childConName);
            childList_img.add(childConImg);
            num++;
        }

        // 댓글 작업(임시)
        CommentViewAdapter adapter = new CommentViewAdapter(DetailActivity.this, comment_list, comment_Img);
        comment_LV = (ListView)findViewById(R.id.comment_LV);
        comment_LV.setAdapter(adapter);

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

        main_SV = (ScrollView)findViewById(R.id.main_Scroll);

        ExListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                main_SV.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        sub_SV = (ScrollView)findViewById(R.id.sub_SV);

        comment_LV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sub_SV.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        /*
        comment_LV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sv.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        */
        setListViewHeightBasedOnChildren(comment_LV);
        setExpandableListViewHeight(ExListView, -1);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void setExpandableListViewHeight(ExpandableListView expandableListView, int group) {
        ExpandableListAdapter listAdapter = expandableListView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(expandableListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            view = listAdapter.getGroupView(i, false, view, expandableListView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
            if(((expandableListView.isGroupExpanded(i)) && (i != group)) || ((!expandableListView.isGroupExpanded(i)) && (i == group))) {
                View listItem = null;
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    listItem = listAdapter.getChildView(i, j, false, listItem, expandableListView);
                    listItem.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, View.MeasureSpec.UNSPECIFIED));
                    listItem.measure(
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }
        ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
        params.height = totalHeight + (expandableListView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        expandableListView.setLayoutParams(params);
        expandableListView.requestLayout();
    }

    private void setLayout(){
        ExListView = (ExpandableListView)findViewById(R.id.elv_list);
    }

}
