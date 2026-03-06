package webprog2.sistemaprestamobibliotecaapp.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
    private static int cont = 1;
    private final Long id;
    private final String userName;
    private final Type type;

    public User(String userName, Type type) {
        this.id = (long) cont++;
        this.userName = userName;
        this.type = type;
    }

    public enum Type { ADMIN, REGULAR }
}
