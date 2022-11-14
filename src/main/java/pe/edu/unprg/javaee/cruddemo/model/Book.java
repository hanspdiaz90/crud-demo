package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class Book {

    private int bookId;
    private String isbn;
    private String title;
    private String coverImage;
    private String review;
    private int yearEdition;
    private int numberPages;
    private int copies;
    private double price;
    private Author author;
    private Publisher publisher;
    private Genre genre;
    private boolean active;

}