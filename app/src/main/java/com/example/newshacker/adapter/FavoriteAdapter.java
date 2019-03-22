package com.example.newshacker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newshacker.R;
import com.example.newshacker.model.ModelNews;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<ModelNews> list;
    private Context context;

    public FavoriteAdapter(List<ModelNews> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_favorites, viewGroup, false);
        return new FavoriteAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int i) {
        holder.tvTitle.setText(list.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;

        public FavoriteViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.title_news_favorite);
        }
    }
}
