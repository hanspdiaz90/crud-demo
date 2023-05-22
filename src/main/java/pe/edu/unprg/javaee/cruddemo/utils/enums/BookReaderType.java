package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum BookReaderType {

    STUDENT(1),
    TEACHER(2);

    private final int statusId;

    BookReaderType(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

}