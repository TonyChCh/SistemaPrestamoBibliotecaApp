package webprog2.sistemaprestamobibliotecaapp.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
public class Book {
    private static int cont = 1;
    private final Long id;
    private final String title;
    private final Type type;
    @NotNull
    private final Status status;

    public Book(String title, Type type) {
        this.id = (long) cont++;
        this.title = title;
        this.type = type;
        this.status = Status.AVAILABLE;
    }

    public enum Status { INLOAN, AVAILABLE}

    public enum Type { NOVEL, STORY, SCIENCE, HISTORY, OTHER }
}

