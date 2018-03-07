package com.vivekanand.literature.literatureofvivekanand.indexer;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Ankit on 3/7/2018.
 */

public class IndexerCore {

    private HashMap<String, HashSet<String>> wordToFileMap;
    private FileReader fileReader;

    public IndexerCore(Context context) {
        wordToFileMap = new HashMap<>();
        fileReader = new FileReader(context);
    }


    public void createIndex(String[] fileNames) {

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

        printIndex();

    }

    public HashMap<String, HashSet<String>> getWordToFileMap() {
        return wordToFileMap;
    }


    public void printIndex() {

        for (String name : wordToFileMap.keySet()) {

            String key = name;
            HashSet<String> fileSet = wordToFileMap.get(name);
            System.out.println(key + " " + fileSet.toString());
            Log.d("INDEX", key + " " + fileSet.toString());

        }

    }

}
