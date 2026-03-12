package webprog2.sistemaprestamobibliotecaapp.service;

import org.springframework.stereotype.Service;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.repository.BookRepository;
import java.util.List;

/**
 * Service responsible for book-related business logic.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    // Constructor injection
    public BookService(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    /**
     * Get a book by its id.
     * @param id the id of the book to retrieve
     * @return Book with the given id, or null if not found
     */
    public Book getBookById(Long id) { return bookRepository.findBookById(id); }
    /**
     * Get a list of books by their title.
     * @param title the title to search for
     * @return List of Books matching the title
     */
    public List<Book> searchBookByTitle(String title) { return bookRepository.findBooksByTitle(title); }
    /**
     * Get a list of books by their category.
     * @param category the type to search for
     * @return List of Books matching the category
     */
    public List<Book> getBookByCategory(String category) { return bookRepository.findBookByCategory(category); }
    /**
     * @return List of all Books
     */
    public List<Book> getAllBooks() { return bookRepository.findAllBooks(); }
}
