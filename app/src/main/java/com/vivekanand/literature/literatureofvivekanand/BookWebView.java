package com.vivekanand.literature.literatureofvivekanand;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

import java.io.IOException;
import java.io.InputStream;

public class BookWebView extends AppCompatActivity {

    private SharedPreferenceLoader sharedPreferenceLoader;
    String white_color = "white";
    String color_invert = "<script>document.addEventListener('DOMContentLoaded', function() {  document.body.style.color = \"" + white_color + "\"; });";
    String toReplace = "<script src=\"V1%20C1%20Response%20to%20welcome_files/jquery.js\">";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferenceLoader = new SharedPreferenceLoader(this);
        if (sharedPreferenceLoader.loadNightMode()) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppThemeDay);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_web_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String bookUrl = bundle.getString("bookPath");
        WebView webView = findViewById(R.id.book_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        InputStream inputStream;
        byte[] buffer;
        try {
            inputStream = getApplicationContext().getAssets().open(bookUrl);
            buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            if (sharedPreferenceLoader.loadNightMode()) {
                webView.setBackgroundColor(0);
                webView.loadData(new String(buffer).replace(toReplace,color_invert ),
                        "text/html; charset=UTF-8", null);
            } else {
                webView.loadData(new String(buffer),
                        "text/html", "UTF-8");
            }
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
