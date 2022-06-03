package pe.edu.unprg.javaee.inventariolibros.models.enums;

public enum Rol {

    ADMIN(0),
    EDITOR(1),
    GUEST(2);

    private final int statusId;

    Rol(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
