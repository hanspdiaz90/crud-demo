package pe.edu.unprg.javaee.inventariolibros.model;

import lombok.Data;

@Data
public class Genre {

    private int genreId;
    private String genre;
    private boolean active;

}