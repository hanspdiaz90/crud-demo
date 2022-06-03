package pe.edu.unprg.javaee.inventariolibros.models;

import lombok.Data;

@Data
public class Publisher {

    private int id;
    private String nombre;
    private String direccion;
    private String email;
    private String paginaWeb;
    private String nroTelefono;
    private boolean activo;

}