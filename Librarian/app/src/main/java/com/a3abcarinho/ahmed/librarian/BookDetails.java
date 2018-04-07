package com.a3abcarinho.ahmed.librarian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ahmed on 02/02/18.
 */

public class BookDetails extends AppCompatActivity {
    TextView name_tv;
    TextView author_tv;
    TextView date_tv;
    TextView publisher_tv;
    TextView category_tv;
    TextView pages_tv;
    TextView shelf_tv;
    TextView price_tv;
    TextView overview_tv;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details_activity);
        name_tv = (TextView) findViewById(R.id.nameDetail);
        author_tv = (TextView) findViewById(R.id.authorDetail);
        date_tv = (TextView) findViewById(R.id.dateDetail);
        publisher_tv = (TextView) findViewById(R.id.publisherDetail);
        category_tv = (TextView) findViewById(R.id.categoryDetail);
        pages_tv = (TextView) findViewById(R.id.pagesDetail);
        shelf_tv = (TextView) findViewById(R.id.shelfDetail);
        price_tv = (TextView) findViewById(R.id.priceDetail);
        overview_tv = (TextView) findViewById(R.id.overviewDet);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("Book Name");
        name_tv.setText(name);
        String author = intent.getExtras().getString("Author Name");
     if (author != null && !author.isEmpty()){
         author_tv.setText(author);
     }
        String date = intent.getExtras().getString("Date");
        if (date != null && !date.isEmpty()){
            date_tv.setText(date);
        }
        String publisher = intent.getExtras().getString("Publisher");
        if (publisher != null && !publisher.isEmpty()){
            publisher_tv.setText(publisher);
        }
        String category = intent.getExtras().getString("Category");
        if (category != null && !category.isEmpty()){
            category_tv.setText(category);
        }
        String pages = intent.getExtras().getString("Pages");
        if (pages != null && !pages.isEmpty()){
            pages_tv.setText(pages);
        }
        String shelf = intent.getExtras().getString("Shelf");
        if (shelf != null && !shelf.isEmpty()){
            shelf_tv.setText(shelf);
        }
        String price = intent.getExtras().getString("Price");
        if (price != null && !price.isEmpty()){
            price_tv.setText(price);
        }
        String overview = intent.getExtras().getString("Overview");
        if (overview != null && !overview.isEmpty()){
            overview_tv.setText(overview);
        }
    }
}
