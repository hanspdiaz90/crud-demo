package pe.edu.unprg.javaee.inventariolibros.models;

import lombok.Data;
import pe.edu.unprg.javaee.inventariolibros.models.enums.Rol;

@Data
public class User {

    private int id;
    private String email;
    private String contrasenia;
    private Rol rol;
    private boolean activo;

}