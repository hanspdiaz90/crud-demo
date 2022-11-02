package pe.edu.unprg.javaee.inventariolibros.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Author {

    private int authorId;
    private String firstname;
    private String lastname;
    private String city;
    private LocalDate dateBirth;
    private boolean active;

}