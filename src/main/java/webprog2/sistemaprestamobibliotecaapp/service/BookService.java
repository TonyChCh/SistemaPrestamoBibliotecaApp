package webprog2.sistemaprestamobibliotecaapp.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @param title the title to search for
     * @return List of Books matching the title
     */
    public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }
        // Limpia y separa por espacios (ej: "quijote mancha")
        String[] words = title.trim().toLowerCase().split("\\s+");

        // 2. Buscamos los libros que coincidan con la PRIMERA palabra
        List<Book> results = bookRepository.findByTitleContainingIgnoreCase(words[0]);

        // 3. Si hay más palabras, filtramos la lista en memoria (¡Súper rápido y sin tocar la DB!)
        if (words.length > 1) {
            for (int i = 1; i < words.length; i++) {
                String word = words[i];
                results = results.stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(word))
                        .collect(Collectors.toList());
            }
        }
        return results;
    }

    /**
     * Get a list of books by their category.
     * @param category the type to search for
     * @return List of Books matching the category
     */
    public List<Book> getBooksByCategory(String category) { return bookRepository.findBookByCategory(category); }
    /**
     * @return List of all Books
     */
    public List<Book> getAllBooks() {return bookRepository.findAll(); }

    public Optional<Book> getBookById(Long id) {
        if (id == null || id < 0) {
            return Optional.empty();
        }
        return bookRepository.findById(id);
    }

    public Book createBook(Book Book) { return bookRepository.save(Book); }

    public Book updateBookFields(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (updatedBook.getCategory() != null) {
                        existingBook.setCategory(updatedBook.getCategory());
                    }
                    if (updatedBook.getTitle() != null && !updatedBook.getTitle().trim().isEmpty()) {
                        existingBook.setTitle(updatedBook.getTitle().trim());
                    }
                    return bookRepository.save(existingBook);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public void deleteBook(Long id) { bookRepository.deleteById(id); }
}
