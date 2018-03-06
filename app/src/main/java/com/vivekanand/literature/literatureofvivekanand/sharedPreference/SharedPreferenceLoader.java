package com.vivekanand.literature.literatureofvivekanand.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.vivekanand.literature.literatureofvivekanand.Constants.Constants;

/**
 * Created by gaurav on 22/02/18.
 */

public class SharedPreferenceLoader {
    private static final String APP_NAME = "literature.of.vivekanand";

    SharedPreferences sharedPreferences;

    public SharedPreferenceLoader(Context context){
        sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

     public void saveNighMode(Boolean mode){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.NIGHT_MODE_KEY, mode);
        editor.commit();
    }

    public Boolean loadNightMode(){
        return sharedPreferences.getBoolean("nightMode", false);
    }

    public int loadBookMark(String bookName){
      return sharedPreferences.getInt(bookName,0);
    }

    public void saveBookMark(String bookName, int scrollY){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(bookName, scrollY);
        editor.commit();
    }
}
