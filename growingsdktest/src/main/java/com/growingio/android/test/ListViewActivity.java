package com.growingio.android.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.growingio.android.test.util.WindowHelper;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ListView listview = (ListView) findViewById(R.id.test_listview);
        List<String> data = new ArrayList<>(41);
        data.add("Start RecyclerListActivity");
        for (int i = 0; i < 40; i++) {
            data.add("ListView item " + (i + 2));
        }
        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_item, R.id.text_item, data));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(ListViewActivity.this, RecyclerListActivity.class));
                    finish();
                } else {
                    Log.i(TAG, "Click on ListView item " + position);
                }
            }
        });
    }

}
