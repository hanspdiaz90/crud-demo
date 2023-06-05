package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum Gender {

    MALE(1),
    FEMALE(2),
    BINARY(3),
    OTHERS(4);

    private final int code;

    Gender(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}