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
     * Get a list of books by their title.
     *
     * @param title the title to search for
     * @return List of Books matching the title
     */
    List<Book> getBookByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }
    /**
     * Get a list of books by their type.
     * @param type the type to search for
     * @return List of Books matching the type
     */
    List<Book> getBookByType(String type) {
        return bookRepository.findBookByType(type);
    }

}
