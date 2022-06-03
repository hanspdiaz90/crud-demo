package pe.edu.unprg.javaee.inventariolibros.models;

import lombok.Data;

@Data
public class Book {

    private int id;
    private String isbn;
    private String titulo;
    private String portada;
    private String resenia;
    private int anioEdicion;
    private int nroPaginas;
    private int ejemplares;
    private double precio;
    private Author autor;
    private Publisher editorial;
    private Genre genero;
    private boolean activo;

}