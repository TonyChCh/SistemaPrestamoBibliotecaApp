package webprog2.sistemaprestamobibliotecaapp.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.service.BookService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class ApiBookController {
    private final BookService bookService;

    public ApiBookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public Iterable<Book> getAllBooks() { return bookService.getAllBooks();}

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookOpt = bookService.getBookById(id);
        return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{title}/search")
    public ResponseEntity<Iterable<Book>> searchBooksByTitle(@PathVariable String title) {
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Iterable<Book> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PatchMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBookFields(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
