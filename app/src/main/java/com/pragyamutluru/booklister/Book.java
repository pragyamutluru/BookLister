package com.pragyamutluru.booklister;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by suresh on 6/2/18.
 */

public class Book {

    private String title;
    private ArrayList<String> authors;
    private String url;
    private String imageUrl;
    private String publisher;
    private String date;
    private String id;
    private String description;



//    public Book(String title, ArrayList<String> authors, String url, String publisher,Date date, String id){
//        this.title=title;
//        this.id=id;
//        this.authors=authors;
//        this.url=url;
//        this.publisher=publisher;
//        this.date=date;
//    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {

        return title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getUrl() {
        return url;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDate() {
        return date;
    }
}
