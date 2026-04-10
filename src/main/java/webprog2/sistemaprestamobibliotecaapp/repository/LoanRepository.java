package webprog2.sistemaprestamobibliotecaapp.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import java.util.List;
/**
 * Repository interface for Loan-related operations.
 * Implementations will be provided later (in-memory, JPA, etc.).
 */
public interface LoanRepository extends CrudRepository<Loan, Long> {
    /**
     * Return the list of loans for a given userId. If user doesn't exist, return empty list.
     */
    List<Loan> findByUserId(Long userId);

    /**
     * Return the list of active loans. If no active loans exist, return empty list.
     */
    List<Loan> findByActiveTrue();
    /**
     * Return the list of returned loans. If no inactive loans exist, return empty list.
     */
    List<Loan> findByActiveFalse();
    /**
     * Return the list of all loans.
     */
    List<Loan> findAll();


    // Metodo para insertar un registro en la tabla de historial de préstamos (LOAN_HISTORY)
    @Modifying
    @Query("INSERT INTO LOAN_HISTORY (loan_id, book_id) VALUES (:loanId, :bookId)")
    void saveLoanHistory(Long loanId, Long bookId);
}
