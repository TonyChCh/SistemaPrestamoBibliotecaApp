package webprog2.sistemaprestamobibliotecaapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.repository.LoanRepository;
import webprog2.sistemaprestamobibliotecaapp.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
     * @param userId of the user to get loans for
     * @return Optional containing a list of Loans if found
     */
    @Transactional(readOnly = true)
    public List<Loan> getLoansForUser(Long userId) {
        List<Loan> loanHistory = loanRepository.findByUserId(userId);
        loanHistory.forEach(loan -> {
            List<Book> booksInLoan = bookRepository.findAllByLoanId(loan.getId());
            loan.setBooks(booksInLoan);
        });
        // Ordenar para mejor vista, orden: RETURNED > ACTIVE > fecha_mas_vieja > fecha_mas_reciente
        loanHistory.sort(Comparator
                // Por estado (RETURNED primero, luego ACTIVE)
                .comparing((Loan loan) -> loan.getReturnTime() != null ? 0 : 1)
                // Luego por fecha (ascendente)
                .thenComparing(Loan::getLoanTime)
        );
        // Invertir el orden, los mas viejos aparecen abajo, los PENDIENTES se muestran primero
        Collections.reverse(loanHistory);
        return loanHistory;
    }
    /**
     * Add a book to the user's cart if it's available. This method checks the availability of the book
     * before adding it to the cart and updates the availability status of the book accordingly.
     *
     * @param bookId the id of the book to add to the cart
     * @param cart   the current list of books in the user's cart
     * @return true if the book was successfully added to the cart, false otherwise
     */
    @Transactional
    public boolean addBookToCart(Long bookId, List<Book> cart) {
        // Try to reserve the book in DB, this method returns the number of rows affected (0 or 1)
        int rowsAffected = bookRepository.reserveBook(bookId);

        if (rowsAffected > 0) {
            // Only if the book was successfully reserved, we add it to the cart
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found."));
            cart.add(book);
            return true;
        }
        // If rowsAffected is 0, it means the book was already reserved by someone else
        return false;
    }
    /**
     * Remove a book from the user's cart and update its availability status.
     *
     * @param bookId the id of the book to remove from the cart
     * @param cart   the current list of books in the user's cart
     * @return true if the book was successfully removed from the cart, false otherwise
     */
    @Transactional
    public boolean removeBookFromCart(Long bookId, List<Book> cart) {
        // Try to release the book in DB, this method returns the number of rows affected (0 or 1)
        int rowsAffected = bookRepository.releaseBookFromCart(bookId);

        if (rowsAffected > 0) {
            // Only if the book was successfully released, we remove it from the cart
            return cart.removeIf(book -> book.getId().equals(bookId));
        }
        // If rowsAffected is 0, it means the book was not in the cart or was already released
        return false;
    }
    /**
     * Create a new loan for a user with the given list of books.
     * This method checks if the books are available before creating the loan.
     * @param userId of the user to loan the books to
     * @param cart the list of books to loan
     */
    @Transactional
    public void loanBookToUser(Long userId, List<Book> cart) {
        // Create a new loan for the user
        Loan newLoan = new Loan();
        newLoan.setUserId(userId);
        // Save the loan to the repository
        // Validation of books availability is already done in the addBookToCart method
        Loan savedLoan = loanRepository.save(newLoan);
        // Get the list of book IDs from the cart
        List<Long> inCartBookIds = cart.stream()
                .map(Book::getId)
                .collect(Collectors.toList());
        // Update the availability of the books
        int updatedCount = bookRepository.confirmLoanBooks(inCartBookIds);
        // If the number of updated books does not match the size of the cart,
        // it means one or more books are no longer available
        if (updatedCount != inCartBookIds.size()) {
            throw new RuntimeException("Uno o más libros ya no están disponibles.");
        }
        // Register the loan history for each book in the loan
        for (Long bookId : inCartBookIds) {
            loanRepository.saveLoanHistory(savedLoan.getId(), bookId);
        }
    }
    /**
     * Return a loan by its id. This method updates the availability
     * of the books in the loan and changes the status of the loan to "RETURNED".
     */
    @Transactional
     public Loan returnLoan(Long loanId) {
        // Find the loan by its ID, if it doesn't exist, throw an exception
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan with id " + loanId + " not found."));
        // If the loan is already returned, do nothing
        if (!loan.isActive()) { return loan; }
        // Update the books availability in the repository
        bookRepository.releaseBooksByLoanId(loanId);
        // Change the status of the loan to returned and set the return time
        loan.setActive(false);
        loan.setReturnTime(LocalDateTime.now());
        // Update the loan in the repository
        return loanRepository.save(loan);
    }

    // API Methods

    public List<Loan> getAllLoans() { return loanRepository.findAll(); }

    public Optional<Loan> getLoanById(Long id) { return loanRepository.findById(id); }

    public List<Book> getLoanRegistry(Long id) { return bookRepository.findAllByLoanId(id); }

    public List<Loan> getLoansByUserId(Long userId) { return loanRepository.findByUserId(userId); }

    public List<Loan> getActiveLoans() { return loanRepository.findByActiveTrue(); }

    public List<Loan> getReturnedLoans() { return loanRepository.findByActiveFalse(); }

    // The difference with the loanBookToUser method is that this method receives a list of book IDs instead of a cart of Book.
    // And it checks directly the availability of the books in the database without needing to reserve them.
    @Transactional
    public Loan createLoan(Long userId, List<Long> bookIds) {
        Loan newLoan = new Loan();
        newLoan.setUserId(userId);
        Loan savedLoan = loanRepository.save(newLoan);
        int updatedCount = bookRepository.confirmLoanBooksAPI(bookIds);
        if (updatedCount != bookIds.size()) {
            throw new RuntimeException("Uno o más libros ya no están disponibles.");
        }
        for (Long bookId : bookIds) { loanRepository.saveLoanHistory(savedLoan.getId(), bookId); }
        return savedLoan;
    }
}
