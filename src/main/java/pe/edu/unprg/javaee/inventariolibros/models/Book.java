package pe.edu.unprg.javaee.inventariolibros.models;

import lombok.Data;

@Data
public class Book {

    private int id;
    private String isbn;
    private String titulo;
    private String resenia;
    private int existencias;
    private double precio;
    private Author autor;
    private Publisher editorial;
    private Genre genero;
    private boolean activo;

}