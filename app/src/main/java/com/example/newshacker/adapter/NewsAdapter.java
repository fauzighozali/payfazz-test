package com.example.newshacker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newshacker.R;
import com.example.newshacker.callback.OnNewsItemClickListener;
import com.example.newshacker.model.ModelNews;
import com.example.newshacker.session.SessionFavorite;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
    private ArrayList<ModelNews> list;
    private Context context;
    private OnNewsItemClickListener itemClickListener;

    public NewsAdapter(ArrayList<ModelNews> list, Context context, OnNewsItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView id;
        TextView by;
        TextView time;
        LikeButton favorite;
        public NewsViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title_news);
            id = (TextView) itemView.findViewById(R.id.id);
            by = (TextView) itemView.findViewById(R.id.by);
            time = (TextView) itemView.findViewById(R.id.time);
            favorite = (LikeButton) itemView.findViewById(R.id.favorite);
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_items, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        final String newsTitle = list.get(position).getTitle();
        holder.title.setText(newsTitle);

        final String by = list.get(position).getBy();
        holder.by.setText(by);

        final String id = String.valueOf(list.get(position).getId());
        holder.id.setText(id);

        String s = list.get(position).getTime();
        long timestamp = Long.parseLong(s) * 1000L;

        final String time = getDate(timestamp);
        holder.time.setText(time);

        final SessionFavorite sessionFavorite = new SessionFavorite(context);

        holder.favorite.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                itemClickListener.onItemClick(list.get(position).getId(), newsTitle, by, time);
                sessionFavorite.sertIsFavorite("true");
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                sessionFavorite.clearIsFavorite();
            }
        });
    }

    private String getDate(long timestamp) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            Date netDate = (new Date(timestamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
