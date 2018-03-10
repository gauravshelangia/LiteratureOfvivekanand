package com.vivekanand.literature.literatureofvivekanand;


import android.os.CountDownTimer;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class BookWebView extends AppCompatActivity {

    private SharedPreferenceLoader sharedPreferenceLoader;
    String white_color = "white";
    String color_invert = "<script>document.addEventListener('DOMContentLoaded', function() {  document.body.style.color = \"" + white_color + "\"; });";
    String toReplace = "<script src=\"V1%20C1%20Response%20to%20welcome_files/jquery.js\">";
    WebView webView;
    String bookUrl;
    CountDownTimer toastCountDown;
    private BookWebView thisClass = this;
    PopupWindow popupWindow;
    RelativeLayout popUpRelative;
    private String searchTerm;

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
        bookUrl = bundle.getString("bookPath");
        searchTerm = bundle.getString("searchTerm", "");
        webView = findViewById(R.id.book_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setDefaultFontSize(sharedPreferenceLoader.getFontSize());

        if (bookUrl.contains("Bengali")) {
            webView.loadUrl(bookUrl);
            callPopup();
        } else {
            InputStream inputStream;
            byte[] buffer;
            try {
                inputStream = getApplicationContext().getAssets().open(bookUrl);
                buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                if (sharedPreferenceLoader.loadNightMode()) {
                    webView.setBackgroundColor(0);
                    webView.loadData(new String(buffer).replace(toReplace, color_invert),
                            "text/html; charset=UTF-8", null);
                    callPopup();
                } else {
                    webView.loadData(new String(buffer),
                            "text/html", "UTF-8");
                    callPopup();
                }
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        focusSearchTexts();

    }

    private void focusSearchTexts() {

        if (searchTerm.length() > 0) {

            webView.findAll(searchTerm);

            try {
                // Can't use getMethod() as it's a private method
                for (Method m : WebView.class.getDeclaredMethods()) {
                    if (m.getName().equals("setFindIsUp")) {
                        m.setAccessible(true);
                        m.invoke(webView, true);
                        break;
                    }
                }
            } catch (Exception ignored) {
            }
        }

    }

    @Override
    protected void onPostResume() {
        if (sharedPreferenceLoader.loadBookMark(bookUrl) != 0) {
            callPopup();
        }
        super.onPostResume();
    }

    @Override
    public void onBackPressed() {
        sharedPreferenceLoader.saveBookMark(bookUrl, webView.getScrollY());
//        toastCountDown.cancel();
        super.onBackPressed();
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
                searchResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void searchResult(String query) {
        searchTerm = query;
        focusSearchTexts();
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

    private void callPopup() {
        int bookMarkPosition = sharedPreferenceLoader.loadBookMark(bookUrl);
        final Toast continueToast = Toast.makeText(this, "Click Continue to resume where you left....", Toast.LENGTH_SHORT);

        if (bookMarkPosition != 0) {
            this.toastCountDown = new CountDownTimer(25000, 5000) {
                public void onTick(long millisUntilFinished) {
                    if (BookWebView.this.thisClass.hasWindowFocus()) {
                        continueToast.show();
                        return;
                    }
                    continueToast.cancel();
                }

                @Override
                public void onFinish() {
                    continueToast.cancel();
                }
            };
            toastCountDown.start();

            View bookMarkButtons = ((LayoutInflater) getApplicationContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bookmark_popup, null);
            popupWindow = new PopupWindow(bookMarkButtons, -2, -2);
            ((Button) bookMarkButtons.findViewById(R.id.bookmark_no_thanks)).
                    setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            toastCountDown.onFinish();
                            toastCountDown.cancel();
                        }
                    });

            ((Button) bookMarkButtons.findViewById(R.id.bookmark_continue)).
                    setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (sharedPreferenceLoader.loadBookMark(bookUrl) != 0) {
                                webView.scrollTo(0, sharedPreferenceLoader.loadBookMark(bookUrl));
                            }
                            popupWindow.dismiss();
                            toastCountDown.onFinish();
                            toastCountDown.cancel();
                        }
                    });

            popUpRelative = (RelativeLayout) findViewById(R.id.popLayout);
            popUpRelative.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            popUpRelative.post(new Runnable() {
                @Override
                public void run() {
                    popupWindow.showAtLocation(popUpRelative, 8388693, 0, 0);
                }
            });
        }

    }

}
