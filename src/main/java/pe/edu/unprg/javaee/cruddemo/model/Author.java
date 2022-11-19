package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Author {

    private int authorId;
    private String firstName;
    private String lastName;
    private String city;
    private LocalDate dob;
    private boolean active;

}