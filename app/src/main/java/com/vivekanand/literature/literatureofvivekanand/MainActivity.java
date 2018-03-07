package com.vivekanand.literature.literatureofvivekanand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIntent().setAction("Already created");
    }

    @Override
    protected void onResume() {
        String action =  getIntent().getAction();
        if (action == null || !action.equals("Already created")){
            Intent intent =  new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            getIntent().setAction(null);
        }
        super.onResume();
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

    public void goToEnglish(View view) {
        startActivity(new Intent(this, EnglishActivity.class));
    }

    public void goToBengali(View view) {
        startActivity(new Intent(this, BengaliActivity.class));
    }

    public void goToGallery(View view) {
        startActivity(new Intent(this, Gallery.class));
    }

    public void goToSettings(View view) {
        startActivity(new Intent(this, Settings.class));
    }

    public void goToAboutUs(View view) {
        startActivity(new Intent(this, AboutUs.class));
    }



}
