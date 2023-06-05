package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum UserStatus {

    PENDING("PENDIENTE", 1),
    REGISTERED("REGISTRADO", 2),
    BLOCKED("BLOQUEADO", 3);

    private final String value;
    private final int code;

    UserStatus(String value, int code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }

}