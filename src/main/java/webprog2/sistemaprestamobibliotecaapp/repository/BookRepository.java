package webprog2.sistemaprestamobibliotecaapp.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.data.repository.CrudRepository;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import java.util.List;

/**
 * Repository abstraction for task operations.
 */
public interface BookRepository extends CrudRepository<Book, Long> {
    /**
     * Return the book with the given id. If book doesn't exist, return null.
     */
    Book findBookById(Long bookId);
    /**
     * Return the list of books for a given title. If book doesn't exist, return empty list.
     */
    List<Book> findBooksByTitle(String title);
    /**
     * Return the list of books for a given category. If book doesn't exist, return empty list.
     */
    List<Book> findBookByCategory(String type);
    /**
     * Return the list of all books. If no books exist, return empty list.
     */
    List<Book> findAllBooks();
    /**
     * Add a book to the book repository.
     */
    void saveBook(Book book);

    @PostConstruct
    default void init() {
        // Initialize with default books list
        Book book = new Book("Don Quijote de la Mancha", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("Cien Años de Soledad", Book.Category.NOVEL);  // También es NOVEL
        saveBook(book);
        book = new Book("El Principito", Book.Category.STORY);
        saveBook(book);
        book = new Book("La Casa de los Espíritus", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("Breve Historia del Tiempo", Book.Category.SCIENCE);
        saveBook(book);
        book = new Book("Sapiens: De animales a dioses", Book.Category.SCIENCE);
        saveBook(book);
        book = new Book("Historia de Roma", Book.Category.HISTORY);
        saveBook(book);
        book = new Book("El Arte de la Guerra", Book.Category.OTHER);
        saveBook(book);
        book = new Book("Cuentos de la Selva", Book.Category.STORY);
        saveBook(book);
        book = new Book("La Guerra y la Paz", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("El amor en los tiempos del cólera", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("Rayuela", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("Pedro Páramo", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("La ciudad y los perros", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("Conversación en La Catedral", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("La tía Julia y el escribidor", Book.Category.NOVEL);
        saveBook(book);
        book = new Book("El alquimista", Book.Category.STORY);
        saveBook(book);
        book = new Book("La historia interminable", Book.Category.STORY);
        saveBook(book);
        book = new Book("El caballero de la armadura oxidada", Book.Category.STORY);
        saveBook(book);
        book = new Book("Breve historia del tiempo", Book.Category.SCIENCE);
        saveBook(book);
        book = new Book("El universo en una cáscara de nuez", Book.Category.SCIENCE);
        saveBook(book);
        book = new Book("Cosmos", Book.Category.SCIENCE);
        saveBook(book);
        book = new Book("La realidad no es lo que parece", Book.Category.SCIENCE);
        saveBook(book);
        book = new Book("Homo Deus", Book.Category.HISTORY);
        saveBook(book);
        book = new Book("21 lecciones para el siglo XXI", Book.Category.HISTORY);
        saveBook(book);
        book = new Book("La historia de la humanidad", Book.Category.HISTORY);
        saveBook(book);
    }
}
