package com.growingio.android.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends BaseActivity {

    private static final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ListView listview = (ListView) findViewById(R.id.test_listview);
        List<String> data = new ArrayList<>(41);
        for (int i = 0; i < 40; i++) {
            data.add("ListView item " + (i + 1));
        }
        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_item, R.id.text_item, data));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Click on ListView item " + position + " hasFocusable " + view.hasFocusable());

            }
        });
    }

    public void onTextClick(View view) {
        Log.d(TAG, "Click on view "+view);
    }

}
