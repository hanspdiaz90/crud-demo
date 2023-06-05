package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class Menu {

    private Integer menuId;
    private String title;
    private Module module;
    private String icon;
    private String route;
    private Integer parentId;
    private Integer sortOrder;
    private boolean active;

}