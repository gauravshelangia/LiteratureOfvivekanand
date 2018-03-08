package com.vivekanand.literature.literatureofvivekanand.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vivekanand.literature.literatureofvivekanand.Constants.Constants;
import com.vivekanand.literature.literatureofvivekanand.models.IndexCacheModel;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;

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


    public void cacheIndexedEnglish(String json){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("engJson",json);
        editor.apply();
    }

    public IndexCacheModel getCachedEnglishIndex(){
        String data = sharedPreferences.getString("engJson","");
        Type type = new TypeToken<HashMap<String, HashSet<String>>>() {}.getType();
        HashMap<String, HashSet<String>> setHashMap = new Gson().fromJson(data, type);
        return new IndexCacheModel(setHashMap);
//        return new Gson().fromJson(sharedPreferences.getString("engJson",""),IndexCacheModel.class);
    }

    public boolean isEnglishIndexCached(){
        return sharedPreferences.getBoolean("c_eng",false);
    }

    public void setEnglishIndexedCached(boolean englishIndexedCached){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("c_eng",englishIndexedCached);
        editor.apply();
    }

    public void cacheIndexedBengali(String json){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("benJson",json);
        editor.apply();
    }

    public IndexCacheModel getCachedBengaliIndex(){
        String data = sharedPreferences.getString("benJson","");
        Type type = new TypeToken<HashMap<String, HashSet<String>>>() {}.getType();
        HashMap<String, HashSet<String>> setHashMap = new Gson().fromJson(data, type);
        return new IndexCacheModel(setHashMap);
    }

    public boolean isBengaliIndexCached(){
        return sharedPreferences.getBoolean("c_ben",false);
    }

    public void setBengaliIndexedCached(boolean bengaliIndexedCached){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("c_ben",bengaliIndexedCached);
        editor.apply();

    }


}
