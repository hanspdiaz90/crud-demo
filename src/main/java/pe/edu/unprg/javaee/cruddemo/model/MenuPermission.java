package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuPermission {

    private Menu menu;
    private Integer level;
    private boolean create;
    private boolean read;
    private boolean update;
    private boolean delete;
    private boolean active;
    List<MenuPermission> children = new ArrayList<>();

}