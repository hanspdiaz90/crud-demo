package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;
import pe.edu.unprg.javaee.cruddemo.utils.enums.UserStatus;

@Data
public class User {

    private Integer userId;
    private String username;
    private String email;
    private String password;
    private Role role;
    private UserStatus status;
    private boolean active;

}