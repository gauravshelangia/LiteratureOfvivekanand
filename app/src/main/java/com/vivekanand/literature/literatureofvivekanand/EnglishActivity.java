package com.vivekanand.literature.literatureofvivekanand;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.adapter.BookListAdapter;
import com.vivekanand.literature.literatureofvivekanand.adapter.SearchAdapter;
import com.vivekanand.literature.literatureofvivekanand.indexer.FileReader;
import com.vivekanand.literature.literatureofvivekanand.indexer.IndexerCore;
import com.vivekanand.literature.literatureofvivekanand.indexer.SearchItemModel;
import com.vivekanand.literature.literatureofvivekanand.indexer.SearchManager;
import com.vivekanand.literature.literatureofvivekanand.models.IndexCacheModel;
import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

import java.util.ArrayList;

public class EnglishActivity extends AppCompatActivity implements SearchManager.SearchListener {

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
    private SearchAdapter searchAdapter;
    private ListView hintListView;
    private TextView searchingTv;
    private ProgressBar searchingProgressBar;
    private RelativeLayout searchHintsContainer;
    private SearchManager searchManager;




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

        prepareSearch();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchResultAndShowHints(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    searchAdapter.resetList();
                    hideSuggestionsView();
                } else {

                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                hideSuggestionsView();
                return true;
            }
        });

        return true;
    }

    // Modified search view
    private void prepareSearch(){

        hintListView = findViewById(R.id.searchHints);
        searchingProgressBar = findViewById(R.id.searchingProgressBar);
        searchingTv = findViewById(R.id.searchingTv);
        searchHintsContainer = findViewById(R.id.search_hint_view);
        searchManager = new SearchManager(this);
        searchManager.setSearchListener(this);


        searchAdapter = new SearchAdapter(this);
        hintListView.setAdapter(searchAdapter);


    }

    @Override
    public void onSearched(ArrayList<SearchItemModel> results) {

        showSuggestionList();
        searchAdapter.setSearchItemModels(results);

    }

    @Override
    public void onNoResultFound() {
        hideSuggestionsView();
        Toast.makeText(this,"No Search Found!",Toast.LENGTH_LONG).show();
    }

    private void searchResultAndShowHints(String query) {

        showSearching();
        searchManager.invokeEnglishSearchResults(query);

    }

    private void showSearching() {

        searchHintsContainer.setVisibility(View.VISIBLE);
        searchingTv.setVisibility(View.VISIBLE);
        searchingProgressBar.setVisibility(View.VISIBLE);

    }

    private void showSuggestionList(){
        searchHintsContainer.setVisibility(View.VISIBLE);
        hintListView.setVisibility(View.VISIBLE);
        searchingProgressBar.setVisibility(View.GONE);
        searchingTv.setVisibility(View.GONE);

    }

    private void hideSuggestionsView(){
        searchHintsContainer.setVisibility(View.GONE);
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
