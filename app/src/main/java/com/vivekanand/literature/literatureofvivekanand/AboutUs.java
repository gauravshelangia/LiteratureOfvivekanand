package com.vivekanand.literature.literatureofvivekanand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

public class AboutUs extends AppCompatActivity {

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
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
