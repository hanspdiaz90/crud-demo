package pe.edu.unprg.javaee.inventariolibros.models;

import lombok.Data;

@Data
public class Author {

    private int id;
    private String nombres;
    private String apellidos;
    private String ciudad;
    private boolean activo;

}