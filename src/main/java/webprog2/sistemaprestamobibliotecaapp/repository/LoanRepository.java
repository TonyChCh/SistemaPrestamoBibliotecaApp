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
    List<Loan> findLoanByUserId(Long userId);

    // Metodo para insertar un registro en la tabla de historial de préstamos (LOAN_HISTORY)
    @Modifying
    @Query("INSERT INTO LOAN_HISTORY (loan_id, book_id) VALUES (:loanId, :bookId)")
    void saveLoanHistory(Long loanId, Long bookId);
}
