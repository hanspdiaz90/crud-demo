package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuPermission {

    private Integer menuId;
    private String title;
    private String module;
    private String icon;
    private String route;
    private Integer parentId;
    private Integer order;
    private Integer level;
    private boolean read;
    private boolean write;
    private boolean delete;
    private boolean active;
    List<MenuPermission> children = new ArrayList<>();

}