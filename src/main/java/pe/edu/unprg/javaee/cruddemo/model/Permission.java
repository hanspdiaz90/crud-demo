package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;
import pe.edu.unprg.javaee.cruddemo.utils.enums.UserStatus;

@Data
public class Permission {

    private int permissionId;
    private Role role;
    private NavMenu menu;
    private boolean read;
    private boolean write;
    private boolean delete;
    private boolean active;

}