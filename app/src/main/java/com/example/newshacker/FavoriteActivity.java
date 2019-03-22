package com.example.newshacker;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.newshacker.adapter.FavoriteAdapter;
import com.example.newshacker.model.ModelNews;
import com.example.newshacker.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;

    private DatabaseHandler databaseHandler;
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initToolbar();

        progressDoalog = new ProgressDialog(FavoriteActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        getAllData();
    }

    private void getAllData() {
        databaseHandler = new DatabaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_favorite);
        List<ModelNews> newsList = databaseHandler.findAll();
        adapter = new FavoriteAdapter(newsList, this);
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        progressDoalog.dismiss();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
