package com.a3abcarinho.ahmed.librarian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKAUTHOR;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKCATEGORY;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKDATE;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKNAME;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKOVERVIEW;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKPAGES;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKPRICE;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKPUBLISHER;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.COLUMN_BOOKSHELF;
import static com.a3abcarinho.ahmed.librarian.BookContract.BookEntry.TABLE_BOOKS;

/**
 * Created by ahmed on 28/01/18.
 */

public class BookDB extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "book";


  public BookDB(Context context){
      super(context,DATABASE_NAME,null,DATABASE_VERSION);
  }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "(" + BookContract.BookEntry.COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_BOOKNAME + " TEXT," + COLUMN_BOOKAUTHOR + " TEXT," + COLUMN_BOOKPAGES + " TEXT," + COLUMN_BOOKDATE + " TEXT," + COLUMN_BOOKCATEGORY + " TEXT," + COLUMN_BOOKPUBLISHER + " TEXT," + COLUMN_BOOKPRICE + " TEXT," + COLUMN_BOOKSHELF + " TEXT," + COLUMN_BOOKOVERVIEW + " TEXT" + ")";
      sqLiteDatabase.execSQL(CREATE_BOOKS_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
      onCreate(sqLiteDatabase);

    }

    public void deleteBook(int id) {
      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
      sqLiteDatabase.delete(TABLE_BOOKS,BookContract.BookEntry.COLUMN_ID + "    = ?",new String[]{String.valueOf(id)});
    }

    public void updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKNAME, book.getName());
        values.put(COLUMN_BOOKAUTHOR, book.getAuthor());
        values.put(COLUMN_BOOKPAGES, book.getPages());
        values.put(COLUMN_BOOKDATE, book.getDate());
        values.put(COLUMN_BOOKCATEGORY, book.getCategory());
        values.put(COLUMN_BOOKPUBLISHER, book.getPublisher());
        values.put(COLUMN_BOOKPRICE, book.getPrice());
        values.put(COLUMN_BOOKSHELF, book.getBookshelf());
        values.put(COLUMN_BOOKOVERVIEW, book.getOverview());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(TABLE_BOOKS,values,BookContract.BookEntry.COLUMN_ID + "    = ?",new String[]{String.valueOf(book.getId())});
    }

    public List<Book> listBooks() {
      String sql = "select * from " + TABLE_BOOKS;
      SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
      List<Book> storeBooks = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String author = cursor.getString(2);
                String pages = cursor.getString(3);
                String date = cursor.getString(4);
                String category = cursor.getString(5);
                String publisher = cursor.getString(6);
                String price = cursor.getString(7);
                String shelf = cursor.getString(8);
                String overview = cursor.getString(9);
                storeBooks.add(new Book(id,name,author,pages,date,category,publisher,price,shelf,overview));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeBooks;
    }

    public void addBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKNAME, book.getName());
        values.put(COLUMN_BOOKAUTHOR, book.getAuthor());
        values.put(COLUMN_BOOKPAGES, book.getPages());
        values.put(COLUMN_BOOKDATE, book.getDate());
        values.put(COLUMN_BOOKCATEGORY, book.getCategory());
        values.put(COLUMN_BOOKPUBLISHER, book.getPublisher());
        values.put(COLUMN_BOOKPRICE, book.getPrice());
        values.put(COLUMN_BOOKSHELF, book.getBookshelf());
        values.put(COLUMN_BOOKOVERVIEW, book.getOverview());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(TABLE_BOOKS,null,values);

    }




}
