package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;
import pe.edu.unprg.javaee.cruddemo.model.enums.UserRole;
import pe.edu.unprg.javaee.cruddemo.model.enums.UserStatus;

@Data
public class User {

    private int userId;
    private String email;
    private String password;
    private UserRole role;
    private UserStatus status;
    private boolean activo;

}