package com.vivekanand.literature.literatureofvivekanand.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.R;

/**
 * Created by gaurav on 07/03/18.
 */

public class SearchSuggestionAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SearchView searchView;

    public SearchSuggestionAdapter(Context context, Cursor cursor, SearchView sv) {
        super(context, cursor, false);
        mContext = context;
        searchView = sv;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.suggestion_layout, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        String deal = cursor.getString(cursor.getColumnIndexOrThrow("deal"));

        TextView suggestionItem = (TextView) view.findViewById(R.id.suggestion_item);
        suggestionItem.setText(deal);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take next action based user selected item
                TextView dealText = (TextView) view.findViewById(R.id.suggestion_item);
                searchView.setIconified(true);
                Toast.makeText(context, "Selected suggestion "+dealText.getText(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }
}
