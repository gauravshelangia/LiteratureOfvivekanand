package com.vivekanand.literature.literatureofvivekanand.indexer;

import android.content.Context;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ankit on 3/7/2018.
 */

public class FileReader {

    Context context;
    private byte[] buffer;

    public FileReader(Context context) {
        this.context = context;
    }

    public String getContentAsString(String filename) {

        InputStream is = null;
        try {
            is = context.getAssets().open(filename);

            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(buffer);
        str = extractString(str);

        return str;

    }

    private String extractString(String htmlcontent){
        return Jsoup.parse(htmlcontent).text();
    }

}
