package pe.edu.unprg.javaee.inventariolibros.model.enums;

public enum Role {

    ADMIN(1),
    EDITOR(2),
    GUEST(3);

    private final int statusId;

    Role(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}