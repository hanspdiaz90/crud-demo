package pe.edu.unprg.javaee.inventariolibros.models;

import lombok.Data;

@Data
public class Genre {

    private int id;
    private String nombre;
    private boolean activo;

}