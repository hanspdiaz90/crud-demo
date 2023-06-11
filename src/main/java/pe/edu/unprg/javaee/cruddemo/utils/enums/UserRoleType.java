package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum UserRoleType {

    ADMIN("ADMINISTRADOR", 1),
    EDITOR("EDITOR", 2),
    USER("USUARIO", 3),
    REPORTS("REPORTES", 4),
    GUEST("INVITADO", 5);

    private final String key;
    private final int value;

    UserRoleType(String key, int value) {
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