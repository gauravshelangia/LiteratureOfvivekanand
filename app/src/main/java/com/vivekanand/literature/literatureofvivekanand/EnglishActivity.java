package com.vivekanand.literature.literatureofvivekanand;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    //file:///android_asset/
    String[] bookPaths = {
            "01_English_Partly.htm",
            "02_English_Partly.htm"
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

        BookListAdapter bookListAdapter = new BookListAdapter(bookNames, this, bookPaths);
        ListView bookList = (ListView) findViewById(R.id.bookList);
        bookList.setAdapter(bookListAdapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(EnglishActivity.this, BookWebView.class));
            }
        });

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
