package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum UserStatus {

    PENDING("PENDIENTE", 1),
    REGISTERED("REGISTRADO", 2),
    BLOCKED("BLOQUEADO", 3);

    private final String key;
    private final int value;

    UserStatus(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

}