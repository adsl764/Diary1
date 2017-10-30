package com.park1993.diary1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class PhotoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PhotoRecyclerAdapter adapter;
    DBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helper = new DBHelper(this, "test", null, 1);
        adapter=new PhotoRecyclerAdapter(this,helper);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);




    }
}
