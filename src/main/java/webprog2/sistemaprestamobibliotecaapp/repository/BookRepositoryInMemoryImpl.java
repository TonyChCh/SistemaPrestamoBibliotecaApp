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
        // Initialize with default books list
        Book book = new Book("Don Quijote de la Mancha", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("Cien Años de Soledad", Book.Category.NOVEL);  // También es NOVEL
        BooksList.add(book);
        book = new Book("El Principito", Book.Category.STORY);
        BooksList.add(book);
        book = new Book("La Casa de los Espíritus", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("Breve Historia del Tiempo", Book.Category.SCIENCE);
        BooksList.add(book);
        book = new Book("Sapiens: De animales a dioses", Book.Category.SCIENCE);
        BooksList.add(book);
        book = new Book("Historia de Roma", Book.Category.HISTORY);
        BooksList.add(book);
        book = new Book("El Arte de la Guerra", Book.Category.OTHER);
        BooksList.add(book);
        book = new Book("Cuentos de la Selva", Book.Category.STORY);
        BooksList.add(book);
        book = new Book("La Guerra y la Paz", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("El amor en los tiempos del cólera", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("Rayuela", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("Pedro Páramo", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("La ciudad y los perros", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("Conversación en La Catedral", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("La tía Julia y el escribidor", Book.Category.NOVEL);
        BooksList.add(book);
        book = new Book("El alquimista", Book.Category.STORY);
        BooksList.add(book);
        book = new Book("La historia interminable", Book.Category.STORY);
        BooksList.add(book);
        book = new Book("El caballero de la armadura oxidada", Book.Category.STORY);
        BooksList.add(book);
        book = new Book("Breve historia del tiempo", Book.Category.SCIENCE);
        BooksList.add(book);
        book = new Book("El universo en una cáscara de nuez", Book.Category.SCIENCE);
        BooksList.add(book);
        book = new Book("Cosmos", Book.Category.SCIENCE);
        BooksList.add(book);
        book = new Book("La realidad no es lo que parece", Book.Category.SCIENCE);
        BooksList.add(book);
        book = new Book("Homo Deus", Book.Category.HISTORY);
        BooksList.add(book);
        book = new Book("21 lecciones para el siglo XXI", Book.Category.HISTORY);
        BooksList.add(book);
        book = new Book("La historia de la humanidad", Book.Category.HISTORY);
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
            return new ArrayList<>(); // Return empty list for null or empty title
        }
        String trimmedTitle = title.trim().toLowerCase();
        return BooksList.stream()
                .filter(book -> book.getTitle().toLowerCase().endsWith(trimmedTitle) // matches end of title
                        || book.getTitle().toLowerCase().contains(" " + trimmedTitle + " ") // matches middle of title
                        || book.getTitle().toLowerCase().startsWith(trimmedTitle)) // matches start of title
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
