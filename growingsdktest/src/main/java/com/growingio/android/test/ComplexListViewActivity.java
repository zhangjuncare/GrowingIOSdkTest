package com.growingio.android.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishaojie on 15/12/30.
 */
public class ComplexListViewActivity extends BaseActivity {
    private static final String TAG = "ComplexListView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        List<String> data = new ArrayList<>(41);
        for (int i = 0; i < 40; i++) {
            data.add("ComplexListView item " + (i + 1));
        }
        ListView listView = (ListView) findViewById(R.id.test_listview);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.complex_list_item, R.id.complex_list_item_text, data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked on item "+position);
            }
        });
    }
}
