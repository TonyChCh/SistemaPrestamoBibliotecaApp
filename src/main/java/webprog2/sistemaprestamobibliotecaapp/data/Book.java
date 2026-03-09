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
    private final Category category;
    @NotNull
    private boolean available;

    public Book(String title, Category category) {
        this.id = (long) cont++;
        this.title = title;
        this.category = category;
        this.available = true;
    }

    public enum Status { INLOAN, AVAILABLE}

    public enum Category { NOVEL, STORY, SCIENCE, HISTORY, OTHER }
}

