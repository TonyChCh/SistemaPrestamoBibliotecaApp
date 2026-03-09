package webprog2.sistemaprestamobibliotecaapp.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import java.util.ArrayList;
import java.util.List;


/**
 * Simple in-memory implementation that stores tasks keyed by username.
 * For initial purposes it delegates to the seeded users in UserRepositoryImpl when possible.
 */
@Repository
public class BookRepositoryInMemoryImpl implements BookRepository {

    private final List<Book> BooksList = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Initialize default books list
        Book book = new Book("El Quijote", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("Cien Años de Soledad", Book.Category.STORY);
        BooksList.add(book);
    }

    @Override
    public Book findBookById(Long bookId) {
        if (bookId == null) {
            return null;
        }
        return BooksList.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElse(null);
    }

    // Find books by title, ignoring case and allowing partial matches,
    // if a word in the title matches, it will be included in the results.
    @Override
    public List<Book> findBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        String trimmedTitle = title.trim().toLowerCase();
        return BooksList.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(trimmedTitle))
                .toList();
    }

    // Find books by category
    // if no books of the given category are found, return an empty list (not null).
    @Override
    public List<Book> findBookByCategory(String category) {
        return BooksList.stream()
                .filter(book -> book.getCategory().name().equalsIgnoreCase(category))
                .toList();
    }

    // Return a copy of the list to prevent external modifications
    @Override
    public List<Book> findAllBooks() { return new ArrayList<>(BooksList); }

    @Override
    public void saveBook(Book book) {
        if (book != null) {  BooksList.add(book); }
    }
}
