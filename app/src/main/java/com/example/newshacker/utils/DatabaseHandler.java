package com.example.newshacker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newshacker.model.ModelNews;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "FavoriteNews";

    private static final String TABLE_FAVORITE = "favorite";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITE + "("
                + KEY_ID + " TEXT," + KEY_TITLE + " TEXT,"
                + KEY_AUTHOR + " TEXT," + KEY_DATE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);

        onCreate(sqLiteDatabase);
    }

    public void save(ModelNews news){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID, news.getId());
        values.put(KEY_TITLE, news.getTitle());
        values.put(KEY_AUTHOR, news.getBy());
        values.put(KEY_DATE, news.getTime());

        db.insert(TABLE_FAVORITE, null, values);
        db.close();
    }

    public List<ModelNews> findAll(){
        List<ModelNews> listNews = new ArrayList<ModelNews>();
        String query="SELECT * FROM "+TABLE_FAVORITE;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                ModelNews news = new ModelNews();
                news.setId(Integer.valueOf(cursor.getString(0)));
                news.setTitle(cursor.getString(1));
                news.setBy(cursor.getString(2));
                news.setTime(cursor.getString(3));
                listNews.add(news);
            }while(cursor.moveToNext());
        }

        return listNews;
    }
}
