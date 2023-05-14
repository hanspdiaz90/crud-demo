package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum ReaderType {

    STUDENT(1),
    TEACHER(2);

    private final int statusId;

    ReaderType(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}