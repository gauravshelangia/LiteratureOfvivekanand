package com.vivekanand.literature.literatureofvivekanand.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Ankit on 3/7/2018.
 */

public class IndexCacheModel {

    @SerializedName("cache")
    public HashMap<String, HashSet<String>> indexMap;

    public IndexCacheModel(HashMap<String, HashSet<String>> indexMap) {
        this.indexMap = indexMap;
    }

    public HashMap<String, HashSet<String>> getIndexMap() {
        return indexMap;
    }

    public void setIndexMap(HashMap<String, HashSet<String>> indexMap) {
        this.indexMap = indexMap;
    }

}
