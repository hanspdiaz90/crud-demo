package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;
import pe.edu.unprg.javaee.cruddemo.utils.enums.UserRoleType;

@Data
public class Role {

    private Integer roleId;
    private UserRoleType roleType;
    private boolean root;
    private boolean active;

}
