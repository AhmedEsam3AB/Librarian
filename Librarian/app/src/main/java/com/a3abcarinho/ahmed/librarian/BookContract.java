package com.a3abcarinho.ahmed.librarian;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ahmed on 04/02/18.
 */

public class BookContract {
    public static final String AUTHORITY = "com.a3abcarinho.ahmed.librarian";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_BOOKS = "books";
    public static final class BookEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOKS).build();
        public static final String TABLE_BOOKS = "books";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_BOOKNAME = "bookname";
        public static final String COLUMN_BOOKAUTHOR = "bookauthor";
        public static final String COLUMN_BOOKPAGES = "bookpages";
        public static final String COLUMN_BOOKDATE = "bookdate";
        public static final String COLUMN_BOOKCATEGORY = "bookcategory";
        public static final String COLUMN_BOOKPUBLISHER = "bookpublisher";
        public static final String COLUMN_BOOKPRICE = "bookprice";
        public static final String COLUMN_BOOKSHELF = "bookshelf";
        public static final String COLUMN_BOOKOVERVIEW = "bookoverview";

    }
}
