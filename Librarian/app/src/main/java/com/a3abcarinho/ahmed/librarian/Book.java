package com.a3abcarinho.ahmed.librarian;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by ahmed on 27/01/18.
 */

public class Book {
    private int id;
    private String name;
    private String author;
    private String pages;
    private String date;
    private String category;
    private String publisher;
    private String price;
    private String bookshelf;
    private String overview;
    public Book(String name,String author,String pages,String date,String category,String publisher,String price,String bookshelf,String overview){
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.date = date;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.bookshelf = bookshelf;
        this.overview = overview;
    }
    public Book(int id,String name,String author,String pages,String date,String category,String publisher,String price,String bookshelf,String overview){
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.date = date;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.bookshelf = bookshelf;
        this.overview = overview;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public  String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getPages(){
        return pages;
    }
    public void setPages(String pages){
        this.pages = pages;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getPublisher(){
        return publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public String getPrice(){
        return price;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getBookshelf(){
        return bookshelf;
    }
    public void setBookshelf(String bookshelf){
        this.bookshelf = bookshelf;
    }
    public String getOverview(){
        return overview;
    }
    public void setOverview(String overview){
        this.overview = overview;
    }



}
