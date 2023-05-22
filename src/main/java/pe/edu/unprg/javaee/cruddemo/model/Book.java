package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class Book {

    private Integer bookId;
    private String isbn;
    private String title;
    private String coverImage;
    private String review;
    private Integer yearEdition;
    private Integer numberPages;
    private Double price;
    private Author author;
    private Publisher publisher;
    private Genre genre;
    private boolean active;

}