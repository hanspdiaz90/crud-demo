package pe.edu.unprg.javaee.cruddemo.utils.enums;

public enum UserRoleType {

    ADMIN("ADMINISTRADOR", 1),
    EDITOR("EDITOR", 2),
    USER("USUARIO", 3),
    REPORTS("REPORTES", 4),
    GUEST("INVITADO", 5);

    private final String value;
    private final int code;

    UserRoleType(String value, int code) {
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