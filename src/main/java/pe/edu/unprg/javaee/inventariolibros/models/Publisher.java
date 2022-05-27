package pe.edu.unprg.javaee.inventariolibros.models;

import lombok.Data;

@Data
public class Publisher {

    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;

}