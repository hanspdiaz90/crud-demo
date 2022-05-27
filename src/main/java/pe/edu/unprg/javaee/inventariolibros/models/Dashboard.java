package pe.edu.unprg.javaee.inventariolibros.models;

import lombok.Data;

@Data
public class Dashboard {

    private int cantidadAutores;
    private int cantidadEditoriales;
    private int cantidadGeneros;
    private int cantidadLibros;

}