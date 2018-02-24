package com.vivekanand.literature.literatureofvivekanand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.vivekanand.literature.literatureofvivekanand.adapter.BookListAdapter;
import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

public class BengaliActivity extends AppCompatActivity {

    String [] bookNames = {
            "01. স্বামী বিবেকানন্দের বাণী ও রচনা - প্রথম খণ্ড",
            "02 .স্বামী বিবেকানন্দের বাণী ও রচনা - দ্বিতীয় খণ্ড",
            "03. স্বামী বিবেকানন্দের বাণী ও রচনা - তৃতীয় খণ্ড",
            "04. স্বামী বিবেকানন্দের বাণী ও রচনা - চতুর্থ খণ্ড",
            "05. স্বামী বিবেকানন্দের বাণী ও রচনা - পঞ্চম খণ্ড",
            "06. স্বামী বিবেকানন্দের বাণী ও রচনা - ষষ্ঠ খণ্ড",
            "07. স্বামী বিবেকানন্দের বাণী ও রচনা - সপ্তম খণ্ড",
            "08. স্বামী বিবেকানন্দের বাণী ও রচনা - অষ্টম খণ্ড",
            "09. স্বামী বিবেকানন্দের বাণী ও রচনা - নবম খণ্ড",
            "10. স্বামী বিবেকানন্দের বাণী ও রচনা - দশম খণ্ড"
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
        setContentView(R.layout.activity_bengali);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BookListAdapter bookListAdapter = new BookListAdapter(bookNames, this);
        ListView bookList = (ListView) findViewById(R.id.bookList);
        bookList.setAdapter(bookListAdapter);

    }
}
