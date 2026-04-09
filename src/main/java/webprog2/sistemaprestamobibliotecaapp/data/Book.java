package webprog2.sistemaprestamobibliotecaapp.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("BOOK")
public class Book {
    @Id
    private Long id;
    @Column("loan_id")
    private Long loanId;
    @NotNull
    private String title;
    @NotNull
    private Category category;
    private boolean available = true;
    private Status status = Status.AVAILABLE;

    public Book(String title, Category category) {
        this.title = title;
        this.category = category;
    }

    public enum Status { AVAILABLE, RESERVED, LOANED}

    public enum Category { NOVEL, STORY, SCIENCE, HISTORY, OTHER }
}

