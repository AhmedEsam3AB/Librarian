package com.a3abcarinho.ahmed.librarian;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.Tracker;

import java.util.List;
import android.os.AsyncTask;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private BookDB database;
    private SearchView searchView;
    private BookAdapter mAdapter;
    private AdView adView;
    private Tracker mTracker;
    RecyclerView bookRV;
    private static final int BOOK_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111");
        adView = findViewById(R.id.adView);
        adView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.mainActivity);
        bookRV = (RecyclerView) findViewById(R.id.books_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        bookRV.setLayoutManager(linearLayoutManager);
        bookRV.setHasFixedSize(true);
        database = new BookDB(this);
        List<Book> allBooks = database.listBooks();
        mAdapter = new BookAdapter(this,allBooks);




        if(allBooks.size()>0){
            bookRV.setVisibility(View.VISIBLE);
            bookRV.setAdapter(mAdapter);

        }else{
            bookRV.setVisibility(View.GONE);
            Toast.makeText(this, R.string.Nobook,Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
            }
        });
        getSupportLoaderManager().initLoader(BOOK_LOADER_ID,null,this);

    }



    private void addBookDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_book_layout,null);
        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText authorField = (EditText)subView.findViewById(R.id.enter_author);
        final EditText pagesField = (EditText)subView.findViewById(R.id.enter_pages);
        final EditText dateField = (EditText)subView.findViewById(R.id.enter_date);
        final EditText categoryField = (EditText)subView.findViewById(R.id.enter_category);
        final EditText publisherField = (EditText)subView.findViewById(R.id.enter_publisher);
        final EditText priceField = (EditText)subView.findViewById(R.id.enter_price);
        final EditText shelfField = (EditText)subView.findViewById(R.id.enter_shelf);
        final EditText overviewField = (EditText)subView.findViewById(R.id.enter_overview);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.AddNew);
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton(R.string.AddBook, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String name = nameField.getText().toString();
                final String author = authorField.getText().toString();
                final String pages = pagesField.getText().toString();
                final String date = dateField.getText().toString();
                final String category = categoryField.getText().toString();
                final String publisher = publisherField.getText().toString();
                final String price = priceField.getText().toString();
                final String shelf = shelfField.getText().toString();
                final String overview = overviewField.getText().toString();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this,R.string.NameRequired, Toast.LENGTH_SHORT).show();
                }else{
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKNAME, name);
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKAUTHOR, author);
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKPAGES, pages);
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKDATE, date);
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKCATEGORY, category);
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKPUBLISHER, publisher);
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKPRICE, price);
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKSHELF, shelf);
                    contentValues.put(BookContract.BookEntry.COLUMN_BOOKOVERVIEW, overview);



                    Uri uri = getContentResolver().insert(BookContract.BookEntry.CONTENT_URI, contentValues);

                 finish();
                 startActivity(getIntent());


                }




            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,R.string.cancelled,Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(database != null){
            database.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(BOOK_LOADER_ID,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.search){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mBookData = null;
            @Override
            protected void onStartLoading() {
                if(mBookData!=null){
                    deliverResult(mBookData);
                }else{
                    forceLoad();
                }
            }



            @Override
            public Cursor loadInBackground() {
                try{
                    return getContentResolver().query(BookContract.BookEntry.CONTENT_URI,null,null,null,null);

                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            public void deliverResult(Cursor data){
                mBookData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }

    private class MyAsyncTask extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {
            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            addBookDialog();
        }
    }
}
