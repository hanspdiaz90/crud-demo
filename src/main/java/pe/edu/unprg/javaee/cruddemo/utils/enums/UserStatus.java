package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum UserStatus {

    PENDING(1),
    REGISTERED(2),
    BLOCKED(3);

    private final int statusId;

    UserStatus(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}