package com.vivekanand.literature.literatureofvivekanand.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.BookWebView;
import com.vivekanand.literature.literatureofvivekanand.R;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by gaurav on 07/03/18.
 */

public class SearchHistoryAdapter extends BaseAdapter {
    String[] searchHistory;
    Context context;
    private static LayoutInflater layoutInflater;

    public SearchHistoryAdapter(String[] searchHistory, Context context) {
        this.context = context;
        this.searchHistory = searchHistory;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return searchHistory.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, final View convertView, ViewGroup viewGroup) {

        View view = layoutInflater.inflate(R.layout.search_history_item, null);
        TextView textView = view.findViewById(R.id.search_history_item_text);
        textView.setText(searchHistory[i]);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "You Clicked " + bookNames[i], Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(context, BookWebView.class);
//                if (i<bookPaths.length){
//                    intent.putExtra("bookPath", bookPaths[i]);
//                    startActivity(context,intent, null);
//                }
//            }
//        });

        return view;
    }
}
