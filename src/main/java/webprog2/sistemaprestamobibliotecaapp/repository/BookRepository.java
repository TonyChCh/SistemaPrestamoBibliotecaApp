package webprog2.sistemaprestamobibliotecaapp.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import java.util.List;

/**
 * Repository abstraction for task operations.
 */
public interface BookRepository extends CrudRepository<Book, Long> {
    /**
     * Return the list of books for a given title. If book doesn't exist, return empty list.
     */
    List<Book> findBooksByTitle(String title);
    /**
     * Return the list of books for a given category. If book doesn't exist, return empty list.
     */
    List<Book> findBookByCategory(String type);
    /**
     * Return the list of all books.
     */
    List<Book> findAll();

    List<Book> findAllByLoanId(Long loanId);
    /** Aquí se agregan los métodos personalizados para liberar libros, reservar libros y confirmar préstamos.
     *  Estos métodos utilizan anotaciones de Spring Data JDBC para realizar operaciones de actualización
     *  en la base de datos.
    */
    @Modifying
    @Query("UPDATE BOOK b SET b.status = 'AVAILABLE', b.available = TRUE, b.loan_id = null " +
            "WHERE b.status = 'RESERVED'")
    void releaseAllReservedBooks();

    // Libera los libros cuando se devuelve un préstamo, se libera los lirbos asociados a ese préstamo.
    @Modifying // Indica que es una operación de escritura (UPDATE/DELETE)
    @Query("UPDATE BOOK b SET b.status = 'AVAILABLE', b.available = TRUE, b.loan_id = null " +
            "WHERE b.loan_id = :loanId")
    void releaseBooksByLoanId(Long loanId);
    // Libera los libros que estaban reservados en el carrito cuando la sesión destruya.
    @Modifying
    @Query("UPDATE BOOK b SET b.status = 'AVAILABLE', b.available = TRUE " +
            "WHERE b.id IN (:bookIds) AND b.status = 'RESERVED'")
    void releaseBooksByIds(List<Long> bookIds);
    // Reserva un libro para el carrito, solo si el libro está disponible. Retorna el número de filas afectadas (0 o 1).
    @Modifying
    @Query("UPDATE BOOK b SET b.status = 'RESERVED', b.available = FALSE " +
            "WHERE b.id = :id AND b.available = TRUE ")
    int reserveBook(Long id);
    // Libera un libro del carrito, solo si el libro estaba reservado. Retorna el número de filas afectadas (0 o 1).
    @Modifying
    @Query("UPDATE BOOK b SET b.status = 'AVAILABLE', b.available = TRUE " +
            "WHERE b.id = :id AND b.status = 'RESERVED'")
    int releaseBookFromCart(Long id);
    // Actualiza el estado de los libros y le asigna el ID del préstamo, solo si los libros estaban reservados.
    @Modifying
    @Query("UPDATE BOOK b SET b.status = 'LOANED', b.available = false, b.loan_id = :loanId " +
            "WHERE b.id IN (:bookIds) AND b.status = 'RESERVED'")
    int confirmBooksLoan(List<Long> bookIds, Long loanId);
}
