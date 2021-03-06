package com.vivekanand.literature.literatureofvivekanand.indexer;

import android.content.Context;

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
            "10_Bengali.htm"
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
                    if (content.contains(word)) {
                        searchItemModels.add(new SearchItemModel(word, bengali_sources[i]));
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
