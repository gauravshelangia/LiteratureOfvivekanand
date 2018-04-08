package com.vivekanand.literature.literatureofvivekanand.indexer;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Ankit on 3/7/2018.
 */

public class IndexerCore {

    private HashMap<String, HashSet<String>> wordToFileMap;
    private FileReader fileReader;
    private Context context;

    String[] bengali_sources = {
            "01_Bengali.htm",
            "02_Bengali.htm",
            "03_Bengali.htm",
            "04_Bengali.htm",
            "05_Bengali.htm",
            "06_Bengali.htm",
            "07_Bengali.htm",
            "08_Bengali.htm",
            "09_Bengali.htm",
            "10_Bengali.htm",
            "11_Bengali.htm",
            "12_Bengali.htm",
            "13_Bengali.htm",
            "14_Bengali.htm",
            "15_Bengali.htm",
            "16_Bengali.htm",
            "17_Bengali.htm",
            "18_Bengali.htm",
            "19_Bengali.htm",
            "20_Bengali.htm",
            "21_Bengali.htm",
            "22_Bengali.htm",
            "23_Bengali.htm",
            "24_Bengali.htm",
            "25_Bengali.htm",
            "26_Bengali.htm",
            "27_Bengali.htm",
            "28_Bengali.htm",
            "29_Bengali.htm",
            "30_Bengali.htm",
            "31_Bengali.htm",
            "32_Bengali.htm",
            "33_Bengali.htm",
            "34_Bengali.htm",
            "35_Bengali.htm"
    };

    String[] english_sources = {
            "01_English_Partly.htm",
            "02_English_Partly.htm"
    };


    public IndexerCore(Context context) {

        this.context = context;
        wordToFileMap = new HashMap<>();
        fileReader = new FileReader(context);

    }

    public void startIndexing() {

        createIndexes(english_sources);
        cacheIndexMap(true);

        createIndexes(bengali_sources);
        cacheIndexMap(false);

    }


    private void createIndexes(String[] fileNames) {

        for (int j = 0; j < fileNames.length; j++) {

            String content = fileReader.getContentAsString(fileNames[j]);

            HashSet<String> files;
            String[] splitArray = content.split("\\s+");
            String key = "";

            for (int i = 0; i < splitArray.length; i++) {

                files = new HashSet<>();
                key = splitArray[i];
                files = (wordToFileMap.get(key) != null) ? wordToFileMap.get(key) : files;
                files.add(fileNames[j]);
                wordToFileMap.put(key, files);

            }
        }

    }

    private void cacheIndexMap(boolean isEnglish) {

        SharedPreferenceLoader sharedPreferenceLoader = new SharedPreferenceLoader(context);
        String json = new Gson().toJson(wordToFileMap);
        if (isEnglish) {
            sharedPreferenceLoader.cacheIndexedEnglish(json);
            sharedPreferenceLoader.setEnglishIndexedCached(true);
        } else {
            sharedPreferenceLoader.cacheIndexedBengali(json);
            sharedPreferenceLoader.setBengaliIndexedCached(true);
        }

    }

    private HashMap<String, HashSet<String>> getWordToFileMap() {
        return wordToFileMap;
    }


    private void printIndex() {

        for (String name : wordToFileMap.keySet()) {

            String key = name;
            HashSet<String> fileSet = wordToFileMap.get(name);
            System.out.println(key + " " + fileSet.toString());
            Log.d("INDEX", key + " " + fileSet.toString());

        }

    }

}
