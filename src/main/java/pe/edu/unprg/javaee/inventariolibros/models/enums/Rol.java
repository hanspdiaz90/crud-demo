package pe.edu.unprg.javaee.inventariolibros.models.enums;

public enum Rol {

    ADMIN(1),
    EDITOR(2),
    GUEST(3);

    private final int statusId;

    Rol(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}