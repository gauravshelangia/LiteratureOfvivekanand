package com.vivekanand.literature.literatureofvivekanand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

    String [] bookPaths = {
            "file:///android_asset/01_Bengali.htm",
            "file:///android_asset/02_Bengali.htm",
            "file:///android_asset/03_Bengali.htm",
            "file:///android_asset/04_Bengali.htm",
            "file:///android_asset/05_Bengali.htm",
            "file:///android_asset/06_Bengali.htm",
            "file:///android_asset/07_Bengali.htm",
            "file:///android_asset/08_Bengali.htm",
            "file:///android_asset/09_Bengali.htm",
            "file:///android_asset/10_Bengali.htm"
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

        BookListAdapter bookListAdapter = new BookListAdapter(bookNames, this, bookPaths);
        ListView bookList = (ListView) findViewById(R.id.bookList);
        bookList.setAdapter(bookListAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.search:
                // TODO open search view to search
                break;
            case R.id.clear_search_history:
                // TODO clear previous search history
                break;
            default:
                System.out.println("Do nothing");
        }
        return super.onOptionsItemSelected(item);
    }
}
