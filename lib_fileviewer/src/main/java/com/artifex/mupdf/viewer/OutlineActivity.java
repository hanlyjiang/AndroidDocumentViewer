package com.artifex.mupdf.viewer;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class OutlineActivity extends ListActivity {
    protected ArrayAdapter<Item> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        int currentPage = bundle.getInt("POSITION");
        ArrayList<Item> outline = (ArrayList<Item>) bundle.getSerializable("OUTLINE");
        int found = -1;
        for (int i = 0; i < outline.size(); ++i) {
            Item item = outline.get(i);
            if (found < 0 && item.page >= currentPage)
                found = i;
            adapter.add(item);
        }
        if (found >= 0)
            setSelection(found);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = adapter.getItem(position);
        setResult(RESULT_FIRST_USER + item.page);
        finish();
    }

    public static class Item implements Serializable {
        public String title;
        public int page;

        public Item(String title, int page) {
            this.title = title;
            this.page = page;
        }

        public String toString() {
            return title;
        }
    }
}
