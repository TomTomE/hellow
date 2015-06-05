package com.example.hyukmin.hellow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hyukmmin on 2015-06-03.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childListName = null;
    private ArrayList<ArrayList<Integer>> childListImg = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public ExpandableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<String>> childListName,
                             ArrayList<ArrayList<Integer>> childListImg){

        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childListName = childListName;
        this.childListImg = childListImg;
    }

    @Override
    public String getGroup(int groupPosition){
        return groupList.get(groupPosition);
    }

    @Override
    public int getGroupCount(){
        Log.d("---getGroupCount()---",""+groupList.size());
        return groupList.size();
    }

    @Override
    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.group_list, parent, false);
            viewHolder.group_img = (ImageView)v.findViewById(R.id.group_img);
            viewHolder.group_name = (TextView)v.findViewById(R.id.group_name);

            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        if(isExpanded){
            viewHolder.group_img.setImageResource(R.drawable.minus);
        }else{
            viewHolder.group_img.setImageResource(R.drawable.plus_bold);
        }

        viewHolder.group_name.setText(getGroup(groupPosition));

        return v;
    }

    @Override
    public String getChild(int groupPosition, int childPosition){
        return childListName.get(groupPosition).get(childPosition);
    }

    // child img 보내는 함수
    public Integer getChildImg(int groupPosition, int childPosition){
        return Integer.valueOf(childListImg.get(groupPosition).get(childPosition));
    }

    @Override
    public int getChildrenCount(int groupPosition){
        Log.d("----Log-----", ""+childListName.get(groupPosition).size());
        return childListName.get(groupPosition).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.child_list, null);
            viewHolder.child_img = (ImageView) v.findViewById(R.id.child_img);
            viewHolder.child_text = (TextView) v.findViewById(R.id.child_text);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.child_text.setText(getChild(groupPosition, childPosition));
        viewHolder.child_img.setImageResource(getChildImg(groupPosition,childPosition));

        return v;
    }

    @Override
    public boolean hasStableIds(){return true;}

    @Override
    public boolean isChildSelectable(int groupPostion, int childPosition){return true;}



    class ViewHolder{
        public ImageView group_img;
        public TextView group_name;
        public ImageView child_img;
        public TextView child_text;
    }
}
