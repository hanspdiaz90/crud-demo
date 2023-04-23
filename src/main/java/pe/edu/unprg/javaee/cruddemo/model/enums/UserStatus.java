package pe.edu.unprg.javaee.cruddemo.model.enums;

public enum UserStatus {

    REGISTERED(1),
    PENDING(2),
    BLOCKED(3);

    private final int statusId;

    UserStatus(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}