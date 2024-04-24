package com.example.olamitdbfx.Classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Books {
    public SimpleIntegerProperty bookid;
    public SimpleStringProperty title;
    public SimpleStringProperty author;
    public SimpleStringProperty dateadded;
    public SimpleIntegerProperty yearpublished;

    public Books(int id, String title, String author, String dateadded, int yearpublished) {
        this.bookid = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.dateadded = new SimpleStringProperty(dateadded);
        this.yearpublished = new SimpleIntegerProperty(yearpublished);
    }

    public SimpleIntegerProperty bookidProperty() {
        return bookid;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }


    public SimpleStringProperty authorProperty() {
        return author;
    }


    public SimpleStringProperty dateaddedProperty() {
        return dateadded;
    }


    public SimpleIntegerProperty yearpublishedProperty() {
        return yearpublished;
    }
}
