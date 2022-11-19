package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;
import pe.edu.unprg.javaee.cruddemo.model.enums.Role;

@Data
public class User {

    private int userId;
    private String email;
    private String password;
    private Role role;
    private boolean admin;
    private boolean activo;

}