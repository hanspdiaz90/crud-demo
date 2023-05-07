package pe.edu.unprg.javaee.cruddemo.model.enums;

public enum UserRole {

    ADMIN(1),
    EDITOR(2),
    GUEST(3);

    private final int statusId;

    UserRole(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}