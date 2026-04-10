package webprog2.sistemaprestamobibliotecaapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.service.BookService;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    // Constructor injection of UserService
    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping("/menu")
    public String showBookMenu(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        // Add categories to the model for the dropdown
        model.addAttribute("categories", Book.Category.values());
        // Fetch all books and add to the model
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "bookmenu";
    }

    // Implement for Search Book by Title
    @GetMapping("/search")
    public String searchBooks(@RequestParam("title") String title,
                              Model model,
                              @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        if (title == null || title.trim().isEmpty()) {
            return "redirect:/book/menu";
        }
        List<Book> searchResults = bookService.searchBooksByTitle(title);

        model.addAttribute("books", searchResults);
        model.addAttribute("categories", Book.Category.values());
        log.info("Books {} ", model.getAttribute("books"));
        log.info("Search for title '{}' returned {} as results", title, searchResults);
        return "bookmenu";
    }



}
