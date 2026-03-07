package webprog2.sistemaprestamobibliotecaapp.service;

import org.springframework.stereotype.Service;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.repository.LoanRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service responsible for loan-related business logic.
 */
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    // Constructor injection of UserRepository
    public LoanService(LoanRepository loanRepository) { this.loanRepository = loanRepository; }

    /**
     * Get all loans for a given user.
     *
     * @param user the user to get loans for
     * @return Optional containing a list of Loans if found
     */
    public Optional<List<Loan>> getLoansForUser(User user) {
        return loanRepository.findLoanByUserId(user.getId());
    }
    /**
     * Create a new loan for a user with the given list of books.
     * This method checks if the books are available before creating the loan.
     * @param user  the user to loan the books to
     * @param books the list of books to loan
     */
    public void loanBookToUser(User user, List<Book> books) {
        Loan loan = new Loan(user.getId(), books);
        // Check if any of the books are not available before saving the loan
        if (books.stream().anyMatch(book -> !book.isAvailable())){
            throw new IllegalStateException("One or more books are not available for loan.");
        }
        // Update the availability of the books
        books.forEach(book -> book.setAvailable(false));
        // Save the loan to the repository
        loanRepository.saveLoan(loan);
    }

    /**
     * Return a loan by its id. This method updates the availability
     * of the books in the loan and changes the status of the loan to "RETURNED".
     */
     public void returnLoan(Long LoanId) {
        Loan loan = loanRepository.findLoanByLoanId(LoanId);
        if (loan != null) {
            // Update the availability of the books in the loan
            loan.getBooks().forEach(book -> book.setAvailable(true));
            // Change the status of the loan to returned
            loan.setStatus(Loan.Status.RETURNED);
        } else {
            throw new IllegalArgumentException("Loan with id " + LoanId + " not found.");
        }
    }
}
