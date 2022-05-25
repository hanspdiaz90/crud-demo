package pe.edu.unprg.javaee.inventariolibros.models.enums;

public enum Status {

    ACTIVE(0),
    DEACTIVE(1);

    private final int statusId;

    Status(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
