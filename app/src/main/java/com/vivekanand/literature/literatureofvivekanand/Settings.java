package com.vivekanand.literature.literatureofvivekanand;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.Constants.Constants;
import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Settings extends AppCompatActivity {

    private SeekBar brightnessSeekBar;
    private TextView brightnessTextView;
    private SeekBar fontSeekBar;
    private TextView fontTextView;
    private SwitchCompat dayNightSwitch;
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
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getPermission();

        // Setting for nightMode
        dayNightSwitch = findViewById(R.id.night_mode_witch);
        if (sharedPreferenceLoader.loadNightMode()){
            dayNightSwitch.setChecked(true);
        } else {
            dayNightSwitch.setChecked(false);
        }


        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPreferenceLoader.saveNighMode(b);
                    restartApp();
                } else {
                    sharedPreferenceLoader.saveNighMode(b);
                    restartApp();
                }
            }
        });

        // Setting the brightness
        setBrightness();
        // setting the font-size
        setFont();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(this,MainActivity.class));
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.search:
//                // TODO open search view to search
//                break;
//            case R.id.clear_search_history:
//                // TODO clear previous search history
//                break;
//            default:
//                System.out.println("Do nothing");
//        }
//        return super.onOptionsItemSelected(item);
//    }



    private void setBrightness() {
        brightnessSeekBar = findViewById(R.id.brightness_seek_bar);
        brightnessTextView = findViewById(R.id.brightness_value);

        int currentBrightness = getScreenBrightness();
        brightnessSeekBar.setProgress(currentBrightness);
        brightnessTextView.setText(String.valueOf(currentBrightness));

        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setScreenBrightness(i);
                brightnessTextView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private int getScreenBrightness() {
        /*
            public static int getInt (ContentResolver cr, String name, int def)
                Convenience function for retrieving a single system settings value as an integer.
                Note that internally setting values are always stored as strings; this function
                converts the string to an integer for you. The default value will be returned
                if the setting is not defined or not an integer.

            Parameters
                cr : The ContentResolver to access.
                name : The name of the setting to retrieve.
                def : Value to return if the setting is not defined.
            Returns
                The setting's current value, or 'def' if it is not defined or not a valid integer.
        */
        int brightnessValue = android.provider.Settings.System.getInt(
                this.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, 0);
        return brightnessValue;
    }

    private void setScreenBrightness(int brightnessValue) {
        /*
            public abstract ContentResolver getContentResolver ()
                Return a ContentResolver instance for your application's package.
        */
        /*
            Settings
                The Settings provider contains global system-level device preferences.

            Settings.System
                System settings, containing miscellaneous system preferences. This table holds
                simple name/value pairs. There are convenience functions for accessing
                individual settings entries.
        */
        /*
            public static final String SCREEN_BRIGHTNESS
                The screen backlight brightness between 0 and 255.
                Constant Value: "screen_brightness"
        */
        /*
            public static boolean putInt (ContentResolver cr, String name, int value)
                Convenience function for updating a single settings value as an integer. This will
                either create a new entry in the table if the given name does not exist, or modify
                the value of the existing row with that name. Note that internally setting values
                are always stored as strings, so this function converts the given value to a
                string before storing it.

            Parameters
                cr : The ContentResolver to access.
                name : The name of the setting to modify.
                value : The new value for the setting.
            Returns
                true : if the value was set, false on database errors
        */

        // Make sure brightness value between 0 to 255
        try {
            if (brightnessValue >= 0 && brightnessValue <= 255) {
                android.provider.Settings.System.putInt(
                        this.getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        brightnessValue
                );
            }
        } catch (SecurityException e) {
            // create a Popup to get the permission
            // TODO open app setting to get access
            Toast.makeText(this, "Do not have permission to change setting", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_SETTINGS}, PERMISSION_GRANTED
            );
        }
    }

    private void setFont() {
        fontSeekBar = findViewById(R.id.font_seek_bar);
        fontTextView = findViewById(R.id.font_value);

        String defaultFontSize = getValueFromSharedPreference(Constants.FONT_SIZE_KEY);
        if (defaultFontSize == null) {
            storeToSharedPreference(Constants.FONT_SIZE_KEY, Constants.FONT_SIZE_MEDIUM);
        }
        setFontSeekBar(getValueFromSharedPreference(Constants.FONT_SIZE_KEY));
        fontSeekBar.setMax(100);

        fontSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setFontTextView(i);
                fontSeekBar.setProgress(i);
                if (i <= 25) {
                    fontSeekBar.setProgress(0);
                } else if (i <= 75 && i > 25) {
                    fontSeekBar.setProgress(50);
                } else {
                    fontSeekBar.setProgress(100);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setFontTextView(int value) {
        switch (value) {
            case 0:
                fontTextView.setText(Constants.FONT_SIZE_SMALL);
                storeToSharedPreference(Constants.FONT_SIZE_KEY, Constants.FONT_SIZE_SMALL);
                Constants.FONT_SIZE_VALUE = Constants.FONT_SMALL;
                break;
            case 50:
                fontTextView.setText(Constants.FONT_SIZE_MEDIUM);
                storeToSharedPreference(Constants.FONT_SIZE_KEY, Constants.FONT_SIZE_MEDIUM);
                Constants.FONT_SIZE_VALUE = Constants.FONT_MEDIUM;
                break;
            case 100:
                fontTextView.setText(Constants.FONT_SIZE_LARGE);
                storeToSharedPreference(Constants.FONT_SIZE_KEY, Constants.FONT_SIZE_LARGE);
                Constants.FONT_SIZE_VALUE = Constants.FONT_LARGE;
                break;
            default:
                break;
        }
    }

    private void setFontSeekBar(String value) {
        switch (value) {
            case "Small":
                fontTextView.setText(Constants.FONT_SIZE_SMALL);
                Constants.FONT_SIZE_VALUE = Constants.FONT_SMALL;
                fontSeekBar.setProgress(0);
                break;
            case "Medium":
                fontTextView.setText(Constants.FONT_SIZE_MEDIUM);
                Constants.FONT_SIZE_VALUE = Constants.FONT_MEDIUM;
                fontSeekBar.setProgress(50);
                break;
            case "Large":
                fontTextView.setText(Constants.FONT_SIZE_LARGE);
                Constants.FONT_SIZE_VALUE = Constants.FONT_LARGE;
                fontSeekBar.setProgress(100);
                break;
            default:
                break;
        }
    }

    private String getValueFromSharedPreference(String key) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(key, null);
    }

    private void storeToSharedPreference(String key, String value) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        sharedPreferenceLoader.saveFontSize(value);
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!android.provider.Settings.System.canWrite(this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                startActivity(intent);

            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_SETTINGS}, PERMISSION_GRANTED
            );
        }
    }

    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
        finish();
    }
}

