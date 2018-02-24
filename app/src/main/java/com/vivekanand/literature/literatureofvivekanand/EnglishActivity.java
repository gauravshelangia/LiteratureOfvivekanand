package com.vivekanand.literature.literatureofvivekanand;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.vivekanand.literature.literatureofvivekanand.adapter.BookListAdapter;
import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

public class EnglishActivity extends AppCompatActivity {

    String[] bookNames = {
            "Complete Works - Volume 1",
            "Complete Works - Volume 2",
            "Complete Works - Volume 3",
            "Complete Works - Volume 4",
            "Complete Works - Volume 5",
            "Complete Works - Volume 6",
            "Complete Works - Volume 7",
            "Complete Works - Volume 8",
            "Complete Works - Volume 9",
            "Gospel of Sri Ramakrishna",
            "The Gospel of the Holy Mother",
            "Biography by Swami Nikhilananda"
    };

    private SharedPreferenceLoader sharedPreferenceLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferenceLoader = new SharedPreferenceLoader(this);
        if (sharedPreferenceLoader.loadNightMode()) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppThemeDay);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BookListAdapter bookListAdapter = new BookListAdapter(bookNames, this);
        ListView bookList = (ListView) findViewById(R.id.bookList);
        bookList.setAdapter(bookListAdapter);

    }
}
