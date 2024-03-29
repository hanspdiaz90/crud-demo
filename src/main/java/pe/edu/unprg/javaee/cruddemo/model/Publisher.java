package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class Publisher {

    private Integer publisherId;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String cellphone;
    private String webSite;
    private boolean active;

}