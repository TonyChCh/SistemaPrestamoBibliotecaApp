package webprog2.sistemaprestamobibliotecaapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.repository.LoanRepository;
import webprog2.sistemaprestamobibliotecaapp.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service responsible for loan-related business logic.
 */
@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    // Constructor injection of UserRepository
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Get all loans for a given user.
     *
     * @param user the user to get loans for
     * @return Optional containing a list of Loans if found
     */
    public List<Loan> getLoansForUser(User user) {
        return loanRepository.findLoanByUserId(user.getId());
    }

    /**
     * Add a book to the user's cart if it's available. This method checks the availability of the book
     * before adding it to the cart and updates the availability status of the book accordingly.
     *
     * @param bookId the id of the book to add to the cart
     * @param cart   the current list of books in the user's cart
     * @return true if the book was successfully added to the cart, false otherwise
     */
    public boolean addBookToCart(long bookId, @ModelAttribute("cart") List<Book> cart) {
        Book book = bookRepository.findBookById(bookId);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            cart.add(book);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a book from the user's cart and update its availability status.
     *
     * @param bookId the id of the book to remove from the cart
     * @param cart   the current list of books in the user's cart
     * @return true if the book was successfully removed from the cart, false otherwise
     */
    public boolean removeBookFromCart(long bookId, @ModelAttribute("cart") List<Book> cart) {
        // Find the book in the cart
        Book bookInCart = cart.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElse(null);
        // Remove the book from the cart
        if (bookInCart != null) {
            bookInCart.setAvailable(true);
            cart.remove(bookInCart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Create a new loan for a user with the given list of books.
     * This method checks if the books are available before creating the loan.
     * @param user  the user to loan the books to
     * @param cart the list of books to loan
     */
    public void loanBookToUser(User user, List<Book> cart) {
        // Create a copy of the cart to avoid modifying the original list outside this method
        List<Book> books = cart.stream().toList();
        Loan loan = new Loan(user.getId(), books);
        // No available's book can't get into loan cart, the controller guarantees it
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
            // If the loan is already returned, do nothing
            if (loan.getStatus() == Loan.Status.RETURNED) { return; }
            // Update the availability of the books in the loan
            loan.getBooks().forEach(book -> book.setAvailable(true));
            // Change the status of the loan to returned
            loan.setReturnTime(LocalDateTime.now());
            loan.setStatus(Loan.Status.RETURNED);
        } else {
            throw new IllegalArgumentException("Loan with id " + LoanId + " not found.");
        }
    }
}
