package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class NavMenu {

    private int menuId;
    private String title;
    private String module;
    private String icon;
    private String route;
    private int parentId;
    private int sortOrder;
    private boolean active;

}