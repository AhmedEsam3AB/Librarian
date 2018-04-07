package com.a3abcarinho.ahmed.librarian;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.TABLE_BOOKS;

/**
 * Created by ahmed on 04/02/18.
 */

public class MyContentProvider extends ContentProvider {
    public static final int BOOKS = 100;
    public static final int BOOK_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(BookContract.AUTHORITY,BookContract.PATH_BOOKS,BOOKS);
        uriMatcher.addURI(BookContract.AUTHORITY,BookContract.PATH_BOOKS+"/#",BOOK_WITH_ID);
        return uriMatcher;
    }
    private BookDB bookDB;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        bookDB = new BookDB(context);
        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s,String[] strings1,String s1) {
        final SQLiteDatabase database = bookDB.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match){
            case BOOKS:
                returnCursor = database.query(TABLE_BOOKS,strings,s,strings1,null,null,s1);
                break;
                case BOOK_WITH_ID:
                    String id = uri.getPathSegments().get(1);
                    String mSelection = "_id=?";
                    String[] mSelectionArgs = new String[]{id};
                    returnCursor = database.query(TABLE_BOOKS,strings,s,strings1,null,null,s1);

                    break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
       returnCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase database = bookDB.getWritableDatabase();
        
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case BOOKS:
                long id = database.insert(TABLE_BOOKS,null,contentValues);
                if(id>0){
                    returnUri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, id);

                }else{
                    throw new android.database.SQLException("Failed to insert row into "+ uri);
                }

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,  ContentValues contentValues,  String s,  String[] strings) {
        int bookUpdated;
        int match = sUriMatcher.match(uri);
        switch (match){
            case BOOK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                bookUpdated = bookDB.getWritableDatabase().update(TABLE_BOOKS,contentValues,"_id=?",new String[]{id});
                break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(bookUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return bookUpdated;
    }
}
