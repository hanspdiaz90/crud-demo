package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {

    private Integer menuId;
    private String title;
    private String module;
    private String icon;
    private String route;
    private Integer parentId;
    private Integer order;
    private boolean active;
    private List<Menu> children = new ArrayList<>();

}