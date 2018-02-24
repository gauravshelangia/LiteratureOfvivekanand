package com.vivekanand.literature.literatureofvivekanand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.R;

/**
 * Created by gaurav on 24/02/18.
 */

public class BookListAdapter extends BaseAdapter {

    String[] bookNames;
    Context context;
    private static LayoutInflater layoutInflater;

    public BookListAdapter(String[] bookNames, Context context) {
        this.bookNames = bookNames;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bookNames.length;
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.book_list_item, null);
        TextView textView = view.findViewById(R.id.book_list_item_id);
        textView.setText(bookNames[i]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked " + bookNames[i], Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
