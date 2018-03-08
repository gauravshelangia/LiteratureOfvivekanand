package com.vivekanand.literature.literatureofvivekanand.indexer;

/**
 * Created by Ankit on 3/7/2018.
 */

public class SearchItemModel {

    String title;
    String book_path;

    public SearchItemModel(String title, String sub_tittle) {
        this.title = title;
        this.book_path = sub_tittle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBook_path() {
        return book_path;
    }

    public void setBook_path(String book_path) {
        this.book_path = book_path;
    }
}
