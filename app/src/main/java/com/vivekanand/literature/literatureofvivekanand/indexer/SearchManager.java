package com.vivekanand.literature.literatureofvivekanand.indexer;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Handler;

/**
 * Created by Ankit on 3/7/2018.
 */

public class SearchManager {

    private ArrayList<SearchItemModel> searchItemModels;
    private HashMap<String, HashSet<String>> wordsMap;
    private FileReader fileReader;
    private android.os.Handler mHandler;

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
            "15_Bengali.html",
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

    String[] bookPaths = {
            "file:///android_asset/01_Bengali.htm",
            "file:///android_asset/02_Bengali.htm",
            "file:///android_asset/03_Bengali.htm",
            "file:///android_asset/04_Bengali.htm",
            "file:///android_asset/05_Bengali.htm",
            "file:///android_asset/06_Bengali.htm",
            "file:///android_asset/07_Bengali.htm",
            "file:///android_asset/08_Bengali.htm",
            "file:///android_asset/09_Bengali.htm",
            "file:///android_asset/10_Bengali.htm",
            "file:///android_asset/11_Bengali.htm",
            "file:///android_asset/12_Bengali.htm",
            "file:///android_asset/13_Bengali.htm",
            "file:///android_asset/14_Bengali.htm",
            "file:///android_asset/15_Bengali.html",
            "file:///android_asset/16_Bengali.htm",
            "file:///android_asset/17_Bengali.htm",
            "file:///android_asset/18_Bengali.htm",
            "file:///android_asset/19_Bengali.htm",
            "file:///android_asset/20_Bengali.htm",
            "file:///android_asset/21_Bengali.htm",
            "file:///android_asset/22_Bengali.htm",
            "file:///android_asset/23_Bengali.htm",
            "file:///android_asset/24_Bengali.htm",
            "file:///android_asset/25_Bengali.htm",
            "file:///android_asset/26_Bengali.htm",
            "file:///android_asset/27_Bengali.htm",
            "file:///android_asset/28_Bengali.htm",
            "file:///android_asset/29_Bengali.htm",
            "file:///android_asset/30_Bengali.htm",
            "file:///android_asset/31_Bengali.htm",
            "file:///android_asset/32_Bengali.htm",
            "file:///android_asset/33_Bengali.htm",
            "file:///android_asset/34_Bengali.htm",
            "file:///android_asset/35_Bengali.htm"
    };


    String[] english_sources = {
            "01_English_Partly.htm",
            "02_English_Partly.htm"
    };

    public SearchManager(Context context) {
        searchItemModels = new ArrayList<>();
        fileReader = new FileReader(context);
        mHandler = new android.os.Handler();
    }

    public void setIndexes(HashMap<String, HashSet<String>> wordsToFileMap) {
        this.wordsMap = wordsToFileMap;
    }


    public void invokeBengaliSearchResults(final String word) {

        new Thread() {
            @Override
            public void run() {

                final ArrayList<SearchItemModel> searchItemModels = new ArrayList<>();
                // read in a loop
                for (int i = 0; i < bengali_sources.length; i++) {

                    String content = fileReader.getContentAsString(bengali_sources[i]);
                    // search
                    Log.d("SearchManager"," searching: "+bengali_sources[i]);
                    if (content.contains(word)) {
                        searchItemModels.add(new SearchItemModel(word, bookPaths[i]));
                    }

                }

                if (searchItemModels.size() == 0) {
                    if (searchListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                searchListener.onNoResultFound();
                            }
                        });
                    }
                } else {
                    if (searchListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                searchListener.onSearched(searchItemModels);
                            }
                        });
                    }
                }

            }

        }.start();
    }

    public void invokeEnglishSearchResults(final String word) {

        new Thread() {
            @Override
            public void run() {

                final ArrayList<SearchItemModel> searchItemModels = new ArrayList<>();
                // read in a loop
                for (int i = 0; i < english_sources.length; i++) {

                    String content = fileReader.getContentAsString(english_sources[i]);
                    // search
                    if (content.contains(word)) {
                        searchItemModels.add(new SearchItemModel(word, english_sources[i]));
                    }

                }


                if (searchItemModels.size() == 0) {
                    if (searchListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                searchListener.onNoResultFound();
                            }
                        });
                    }
                } else {
                    if (searchListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                searchListener.onSearched(searchItemModels);
                            }
                        });
                    }
                }

            }
        }.start();

    }

    private SearchListener searchListener;

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public interface SearchListener {
        void onSearched(ArrayList<SearchItemModel> results);

        void onNoResultFound();
    }

}
