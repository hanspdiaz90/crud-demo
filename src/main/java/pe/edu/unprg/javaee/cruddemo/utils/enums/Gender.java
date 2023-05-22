package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum Gender {

    MALE(1),
    FEMALE(2),
    BINARY(3),
    OTHERS(4);

    private final int statusId;

    Gender(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}