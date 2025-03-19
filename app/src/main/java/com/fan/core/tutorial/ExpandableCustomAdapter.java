package com.fan.core.tutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fan.core.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableCustomAdapter extends BaseExpandableListAdapter{

    //Initializing variables
    private List<String> headerData;
    private HashMap<String, ArrayList<ChildDataModel>> childData;
    private Context mContext;
    private LayoutInflater layoutInflater;

    // constructor
    public ExpandableCustomAdapter(Context mContext, List<String> headerData,
                                   HashMap<String, ArrayList<ChildDataModel>> childData) {
        this.mContext = mContext;
        this.headerData = headerData;
        this.childData = childData;
        this.layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return this.headerData.size();
    }

    @Override
    public int getChildrenCount(int headPosition) {
        return this.childData.get(this.headerData.get(headPosition)).size();
    }

    @Override
    public Object getGroup(int headPosition) {
        return this.headerData.get(headPosition);
    }

    @Override
    public Object getChild(int headPosition, int childPosition) {
        return this.childData.get(this.headerData.get(headPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int headPosition) {
        return headPosition;
    }

    @Override
    public long getChildId(int headPosition, int childPosition) {
        return this.childData.get(this.headerData.get(headPosition))
                .get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int headPosition, boolean is_expanded, View view, ViewGroup headGroup) {
        // Heading of each group
        String heading = (String) getGroup(headPosition);
        if (view==null){
            view = layoutInflater.inflate(R.layout.list_header,null);
        }
        TextView headerTv = view.findViewById(R.id.headerTv);
        headerTv.setText(heading+"");
        return view;
    }

    @Override
    public View getChildView(int headPosition, int childPosition, boolean islastChild, View view, ViewGroup viewGroup) {

        ChildDataModel child = (ChildDataModel) getChild(headPosition, childPosition);

        if (view == null) {
            view = layoutInflater.inflate(R.layout.child_item, null);
        }

        TextView childTv = (TextView) view.findViewById(R.id.childTv);
        ImageView childImg = (ImageView) view.findViewById(R.id.childImg);

        childTv.setText(child.getTitle());
        childImg.setImageResource(child.getImage());
        return view;
    }

    @Override
    public boolean isChildSelectable(int headPosition, int childPosition) {
        return true;
    }
}

