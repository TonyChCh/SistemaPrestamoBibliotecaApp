package webprog2.sistemaprestamobibliotecaapp.data;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Loan {
    private static int cont = 1;
    private final Long id;
    private final Long userId;
    private final List<Book> books;
    private final LocalDateTime loanTime;
    private LocalDateTime returnTime;
    private final Status status;

    public Loan(Long userId, List<Book> books) {
        this.id = (long) cont++;
        this.userId = userId;
        this.books = books;
        this.loanTime = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    public enum Status { ACTIVE, RETURNED }

}
