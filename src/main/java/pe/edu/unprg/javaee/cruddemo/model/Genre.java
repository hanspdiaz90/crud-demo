package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class Genre {

    private Integer genreId;
    private String name;
    private boolean active;

}