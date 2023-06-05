package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum BookReaderType {

    STUDENT(1),
    TEACHER(2);

    private final int code;

    BookReaderType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}