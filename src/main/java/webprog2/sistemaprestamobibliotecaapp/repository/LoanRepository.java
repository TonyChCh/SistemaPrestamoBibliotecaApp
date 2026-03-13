package webprog2.sistemaprestamobibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import java.util.List;
/**
 * Repository interface for Loan-related operations.
 * Implementations will be provided later (in-memory, JPA, etc.).
 */
@Repository
public interface LoanRepository {
    /**
     * Return the loan with the given id. If loan doesn't exist, return empty optional.
     */
    Loan findLoanByLoanId(Long loanId);
    /**
     * Return the list of loans for a given userId. If user doesn't exist, return empty list.
     */
    List<Loan> findLoanByUserId(Long userId);
    /**
     * Add a loan to the loan repository.
     */
    void saveLoan(Loan loan);
}
