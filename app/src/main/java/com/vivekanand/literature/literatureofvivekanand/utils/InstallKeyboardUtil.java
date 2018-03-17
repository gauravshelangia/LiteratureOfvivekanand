package com.vivekanand.literature.literatureofvivekanand.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

import java.util.List;

/**
 * Created by gaurav on 17/03/18.
 */

public class InstallKeyboardUtil {
    private SharedPreferenceLoader sharedPreferenceLoader;

    public void installKeyboard(Context context) {
//        Context context = getApplicationContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> inputMethodInfos = inputMethodManager.getInputMethodList();
        System.out.println(inputMethodInfos.toArray().toString());
        boolean hasBanglaKeyboard = false;
        for (InputMethodInfo inputMethodInfo : inputMethodInfos){
            if(inputMethodInfo.getComponent().getPackageName().equals("ridmik.keyboard")){
                hasBanglaKeyboard = true;
            }
        }

        if (!hasBanglaKeyboard){
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + "ridmik.keyboard"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }

        sharedPreferenceLoader = new SharedPreferenceLoader(context);
        sharedPreferenceLoader.makeBanglaKeyboardInstalled();

    }
}
