package com.vivekanand.literature.literatureofvivekanand;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.adapter.BookListAdapter;
import com.vivekanand.literature.literatureofvivekanand.adapter.SearchAdapter;
import com.vivekanand.literature.literatureofvivekanand.indexer.IndexerCore;
import com.vivekanand.literature.literatureofvivekanand.indexer.SearchItemModel;
import com.vivekanand.literature.literatureofvivekanand.indexer.SearchManager;
import com.vivekanand.literature.literatureofvivekanand.models.IndexCacheModel;
import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;
import com.vivekanand.literature.literatureofvivekanand.utils.InstallKeyboardUtil;

import java.util.ArrayList;

public class BengaliActivity extends AppCompatActivity implements SearchManager.SearchListener {

    String[] bookNames = {
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

    String[] bookPaths = {
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

    String[] sources = {
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

    private SharedPreferenceLoader sharedPreferenceLoader;
    private SearchAdapter searchAdapter;
    private ListView hintListView;
    private SearchManager searchManager;
    private ProgressBar searchingProgressBar;
    private TextView searchingTv;
    private RelativeLayout searchHintsContainer;
    private SearchView searchView;
    private InstallKeyboardUtil installKeyboardUtil = new InstallKeyboardUtil();


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
        prepareSearch();

        if (!installKeyboardUtil.isRidmikKeyboardInstalled(this) && !sharedPreferenceLoader.isBanglaKeyboardInstalled() ) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Install Bangla Keyboard")
                    .setMessage("Install Bangla keyboard to enable Bengali search")
                    .setPositiveButton("Install", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("install clicked");
                            installKeyboardUtil.installKeyboard(getApplicationContext());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("cancel clicked");
                        }
                    }).show();
        }
        Toast.makeText(this, "change keyboard to ridmik keyboard to enable bengali search", Toast.LENGTH_SHORT).show();
    }

    // Modified search view
    private void prepareSearch() {

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
        Toast.makeText(this, "No Search Found!", Toast.LENGTH_LONG).show();
    }

    private void searchResultAndShowHints() {

        String query = searchView.getQuery().toString();
        showSearching();
        searchManager.invokeBengaliSearchResults(query);

    }

    private void showSearching() {

        searchHintsContainer.setVisibility(View.VISIBLE);
        searchingTv.setVisibility(View.VISIBLE);
        searchingProgressBar.setVisibility(View.VISIBLE);

    }

    private void showSuggestionList() {
        searchHintsContainer.setVisibility(View.VISIBLE);
        hintListView.setVisibility(View.VISIBLE);
        searchingProgressBar.setVisibility(View.GONE);
        searchingTv.setVisibility(View.GONE);

    }

    private void hideSuggestionsView() {
        searchHintsContainer.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchResultAndShowHints();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
//            case R.id.search:
//                // TODO open search view to search
//                break;
            case R.id.clear_search_history:
                // TODO clear previous search history
                break;
            default:
                System.out.println("Do nothing");
        }
        return super.onOptionsItemSelected(item);
    }
}
