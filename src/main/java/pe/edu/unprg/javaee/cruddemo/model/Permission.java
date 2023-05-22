package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class Permission {

    private Integer permissionId;
    private Role role;
    private Menu menu;
    private boolean read;
    private boolean write;
    private boolean delete;
    private boolean active;

}