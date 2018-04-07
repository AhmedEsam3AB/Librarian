package com.a3abcarinho.ahmed.librarian;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 28/01/18.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> implements Filterable {
    private Cursor mCursor;
    private static Context context;
    private static List<Book> bookList;
    private List<Book> bookListFiltered;
    private BookDB database;
    public BookAdapter(Context context, List<Book> bookList){
        this.context = context;
        this.bookList = bookList;
       bookListFiltered = bookList;
        database = new BookDB(context);
    }



    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_list_layout,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(BookContract.BookEntry.COLUMN_ID);
        int nameIndex = mCursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOKNAME);
        mCursor.moveToPosition(position);
        final int id = mCursor.getInt(idIndex);
        String name = mCursor.getString(nameIndex);

        final Book singleBook = bookListFiltered.get(position);
        holder.name.setText(name);
        holder.editBook.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                editBookDialog(singleBook);
            }
        });
        holder.deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteBook(singleBook.getId());
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
            }
        });

    }

    private void editBookDialog(final Book book) {
        LayoutInflater inflater = LayoutInflater.from(context);
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
        if(book != null){
            nameField.setText(book.getName());
            authorField.setText(book.getAuthor());
            pagesField.setText(book.getPages());
            dateField.setText(book.getDate());
            categoryField.setText(book.getCategory());
            publisherField.setText(book.getPublisher());
            priceField.setText(book.getPrice());
            shelfField.setText(book.getBookshelf());
            overviewField.setText(book.getOverview());

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.EditBook);
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton(R.string.EditBook, new DialogInterface.OnClickListener() {
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
                    Toast.makeText(context, R.string.NameRequired, Toast.LENGTH_LONG).show();
                }else{
                    database.updateBook(new Book(book.getId(),name,author,pages,date,category,publisher,price,shelf,overview));
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, R.string.cancelled,Toast.LENGTH_LONG).show();
            }
        });
        builder.show();

    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return bookListFiltered.size();



    }
    public Cursor swapCursor(Cursor c){
        if (mCursor == c){
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;
        if(c!=null){
            this.notifyDataSetChanged();
        }
        return temp;
    }
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView deleteBook;
        public ImageView editBook;

        public BookViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.book_name);
            deleteBook = (ImageView) itemView.findViewById(R.id.delete_book);
            Picasso.with(context).load(R.drawable.pdelete).into(deleteBook);
            editBook = (ImageView) itemView.findViewById(R.id.edit_book);
            Picasso.with(context).load(R.drawable.pedit).into(editBook);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Book book = bookList.get(position);
                    Intent intent = new Intent(context, BookDetails.class);
                    intent.putExtra("Book Name",book.getName());
                    intent.putExtra("Author Name",book.getAuthor());
                    intent.putExtra("Date",book.getDate());
                    intent.putExtra("Publisher",book.getPublisher());
                    intent.putExtra("Category",book.getCategory());
                    intent.putExtra("Pages",book.getPages());
                    intent.putExtra("Shelf",book.getBookshelf());
                    intent.putExtra("Price",book.getPrice());
                    intent.putExtra("Overview",book.getOverview());
                    context.startActivity(intent);

                }
            });
        }
    }

    private Filter filter;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
               String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    bookListFiltered = bookList;

                } else {
                    ArrayList<Book> filteredList = new ArrayList<>();
                    for(Book book:bookList){
                        if(book.getName().toLowerCase().contains(charString)){
                            filteredList.add(book);
                        }

                    }
                    bookListFiltered = filteredList;

                }

                FilterResults results = new FilterResults();
                results.values = bookListFiltered;
                return results;
            }
       


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                bookListFiltered = (ArrayList<Book>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

}
