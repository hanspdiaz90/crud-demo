package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;
import pe.edu.unprg.javaee.cruddemo.utils.enums.RoleType;

@Data
public class Role {

    private int roleId;
    private RoleType roleType;
    private boolean root;
    private boolean active;

}
