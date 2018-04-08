package com.vivekanand.literature.literatureofvivekanand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

public class Help extends AppCompatActivity {

    private SharedPreferenceLoader sharedPreferenceLoader;

    private static String keyboardHelp1 = "Open Settings --> Additional Setting --> Language & Input " +
            "--> Select Gboard/Google Keyboard -->" + " select 'Languages' --> add Keyboard " +
            "--> then add Bengali Keyboard";
    private static String keyboardHelp2 = "Open Settings --> Language & Input -> Select Google keyboard -->"
            +" select 'Languages' --> Disable System Language (if Enabled)--> " +
            "then select Bengali Language";
    private static String keyboardHelp3 = "Open Settings --> Language & Input -> Select Languages -->"
            + " Add Language --> Add a Language --> then add Bangla Language";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferenceLoader = new SharedPreferenceLoader(this);
        if (sharedPreferenceLoader.loadNightMode()) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppThemeDay);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setKeyboardHelpContent();
    }

    private void setKeyboardHelpContent(){
        TextView textView1 = findViewById(R.id.keyboard_help1);
        textView1.setText(keyboardHelp1);
        TextView textView2 = findViewById(R.id.keyboard_help2);
        textView2.setText(keyboardHelp3);
        TextView textView3 = findViewById(R.id.keyboard_help3);
        textView3.setText(keyboardHelp3);
    }
}
