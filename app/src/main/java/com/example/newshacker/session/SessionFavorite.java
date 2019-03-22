package com.example.newshacker.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionFavorite {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String sessionName = "favorite";
    private final String isFavorite = "favorite";

    public SessionFavorite(Context context) {
        sharedPreferences = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getIsFavorite() {
        return sharedPreferences.getString(isFavorite, null);
    }

    public void sertIsFavorite(String isFv){
        editor.putString(isFavorite, isFv);
        editor.commit();
    }

    public void clearIsFavorite(){
        editor.clear();
        editor.commit();
    }
}
