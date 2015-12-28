package com.growingio.android.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ExpandableListViewActivity extends BaseActivity {

    ExpandableListView mElv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        mElv = (ExpandableListView) findViewById(R.id.elv);

        mElv.setAdapter(new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return 10;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return 5;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return "Group Item " + groupPosition;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return String.format("Group Item %d, Child Item %d", groupPosition, childPosition);
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
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                TextView textView;
                if (convertView== null) {
                    textView = new TextView(ExpandableListViewActivity.this);
                    convertView = textView;
                } else {
                    textView = (TextView) convertView;
                }

                textView.setText(getGroup(groupPosition).toString());
                return textView;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textView;
                if (convertView== null) {
                    textView = new TextView(ExpandableListViewActivity.this);
                    convertView = textView;
                } else {
                    textView = (TextView) convertView;
                }

                textView.setText(getChild(groupPosition, childPosition).toString());
                return textView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        });

        mElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        mElv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return true;
            }
        });
    }
}
