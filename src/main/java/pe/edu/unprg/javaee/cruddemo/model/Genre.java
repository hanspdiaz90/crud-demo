package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class Genre {

    private int genreId;
    private String name;
    private boolean active;

}