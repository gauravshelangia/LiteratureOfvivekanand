package com.vivekanand.literature.literatureofvivekanand.indexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Ankit on 3/7/2018.
 */

public class SearchManager {

    private ArrayList<SearchItemModel> searchItemModels;
    private HashMap<String, HashSet<String>> wordsMap;

    public SearchManager() {
        searchItemModels = new ArrayList<>();
    }

    public void setIndexes(HashMap<String, HashSet<String>> wordsToFileMap) {
        this.wordsMap = wordsToFileMap;
    }

    public ArrayList<SearchItemModel> getSearchResults(String word) {

        ArrayList<SearchItemModel> searchItemModels = new ArrayList<>();
        HashSet<String> files = wordsMap.get(word);
        if (files != null) {
            String[] filesArray = files.toArray(new String[files.size()]);
            for (int i = 0; i < filesArray.length; i++) {
                searchItemModels.add(new SearchItemModel(word,filesArray[i]));
            }
        }

        return searchItemModels;

    }

}
