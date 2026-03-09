package webprog2.sistemaprestamobibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import java.util.List;

/**
 * Repository abstraction for task operations.
 */
@Repository
public interface BookRepository {
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
}
