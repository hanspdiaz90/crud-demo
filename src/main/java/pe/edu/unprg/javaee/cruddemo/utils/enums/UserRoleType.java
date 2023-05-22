package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum UserRoleType {

    ADMIN(1),
    EDITOR(2),
    USER(3),
    REPORT(4),
    GUEST(5);

    private final int statusId;

    UserRoleType(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}