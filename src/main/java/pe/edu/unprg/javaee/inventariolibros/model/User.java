package pe.edu.unprg.javaee.inventariolibros.model;

import lombok.Data;
import pe.edu.unprg.javaee.inventariolibros.model.enums.Role;

@Data
public class User {

    private int id;
    private String email;
    private String password;
    private Role role;
    private boolean admin;
    private boolean activo;

}