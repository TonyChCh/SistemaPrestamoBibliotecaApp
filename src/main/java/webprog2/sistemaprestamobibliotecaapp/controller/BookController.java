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
@RequestMapping("/user")
public class BookController {

    private final BookService bookService;

    // Constructor injection of UserService
    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping("/bookmenu")
    public String showBookMenu(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        // Add categories to the model for the dropdown
        model.addAttribute("categories", Book.Category.values());
        log.info("Categories added to model: {}", List.of(Book.Category.values()));
        // Fetch all books and add to the model
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        log.info("Books added to model: {}", books.size());
        return "bookmenu";
    }

    // Implement for Search Book by Title
    @GetMapping("/searchbook")
    public String searchBooks(@RequestParam("query") String query,
                              Model model,
                              @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        List<Book> searchResults = bookService.searchBookByTitle(query);
        if (query.isEmpty()) {
            return "redirect:/user/bookmenu";
        }
        model.addAttribute("books", searchResults);
        model.addAttribute("categories", Book.Category.values());
        log.info("Books {} ", model.getAttribute("books"));
        log.info("Search for title '{}' returned {} as results", query, searchResults);
        return "bookmenu";
    }



}
