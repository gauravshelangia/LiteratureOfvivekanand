package com.vivekanand.literature.literatureofvivekanand.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vivekanand.literature.literatureofvivekanand.BookWebView;
import com.vivekanand.literature.literatureofvivekanand.R;
import com.vivekanand.literature.literatureofvivekanand.indexer.SearchItemModel;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Ankit on 3/7/2018.
 */

public class SearchAdapter extends ArrayAdapter<SearchItemModel> {

    ArrayList<SearchItemModel> searchItemModels;
    Context context;

    public SearchAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
        searchItemModels = new ArrayList<>();
    }

    public void setSearchItemModels(ArrayList<SearchItemModel> searchItemModels) {
        this.searchItemModels = searchItemModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.search_item_view, null);
        TextView title = view.findViewById(R.id.title);
        TextView sub_title = view.findViewById(R.id.sub_title);
        title.setText(searchItemModels.get(position).getTitle());
        sub_title.setText(searchItemModels.get(position).getBook_path());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, BookWebView.class);
                intent.putExtra("bookPath", searchItemModels.get(position).getBook_path());
                intent.putExtra("searchTerm",searchItemModels.get(position).getTitle());
                context.startActivity(intent);

            }
        });

        return view;

    }

    @Override
    public int getCount() {
        return searchItemModels.size();
    }

    public void resetList() {
        searchItemModels.clear();
        notifyDataSetChanged();
    }
}
