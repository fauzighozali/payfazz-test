package com.example.newshacker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newshacker.adapter.NewsAdapter;
import com.example.newshacker.callback.OnNewsItemClickListener;
import com.example.newshacker.model.ModelNews;
import com.example.newshacker.rest.ApiClient;
import com.example.newshacker.rest.ApiInterface;
import com.example.newshacker.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnNewsItemClickListener {

    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    ArrayList<ModelNews> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        final ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Integer>> call = apiInterface.getTopStories();
        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                List<Integer> topStories = response.body();
                progressDoalog.dismiss();
                for (int i = 0; i < 10; i++) {
                    Call<ModelNews> listCall = apiInterface.getArticle(topStories.get(i));
                    listCall.enqueue(new Callback<ModelNews>() {
                        @Override
                        public void onResponse(Call<ModelNews> call, Response<ModelNews> response) {
                            String title= response.body().getTitle().toString();
                            int id = response.body().getId();
                            String by = response.body().getBy().toString();
                            String time = response.body().getTime();
                            list.add(new ModelNews(by, id, title, time));
                            generateDataList(list);
                        }

                        @Override
                        public void onFailure(Call<ModelNews> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void generateDataList(ArrayList<ModelNews> body) {
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new NewsAdapter(body, getApplicationContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int id, String title, String by, String time) {
        DatabaseHandler databaseHandler=new DatabaseHandler(this);
        Log.d("insert", "inserting data");
        databaseHandler.save(new ModelNews(by, id, title, time));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite){
            Intent i = new Intent(MainActivity.this,FavoriteActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
