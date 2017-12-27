package com.kavsoftware.kaveer.shoppinglistcompare.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.kavsoftware.kaveer.shoppinglistcompare.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kaveer on 12/24/2017.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> parentDataSource;
    private HashMap<String, List<String>> childDataSource;

    public ExpandableListViewAdapter(Context context, List<String> childParent, HashMap<String, List<String>> child) {
        this.context = context;
        this.parentDataSource = childParent;
        this.childDataSource = child;
    }

    @Override
    public int getGroupCount() {
        return this.parentDataSource.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childDataSource.get(this.parentDataSource.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentDataSource.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.parent_layout, parent, false);
        }

        String parentHeader = (String)getGroup(groupPosition);
        TextView parentItem = (TextView)view.findViewById(R.id.parent_layout);
        parentItem.setText(parentHeader);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child_layout, parent, false);
        }

        String childName = (String)getChild(groupPosition, childPosition);
        TextView childItem = (TextView)view.findViewById(R.id.child_layout);
        childItem.setText(childName);

        return view;
    }

    @Override

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
