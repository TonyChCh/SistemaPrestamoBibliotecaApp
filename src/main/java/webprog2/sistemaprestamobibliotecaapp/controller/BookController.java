package webprog2.sistemaprestamobibliotecaapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.service.BookService;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/bookmenu")
@SessionAttributes("user")
public class BookController {

    private final BookService bookService;

    // Constructor injection of UserService
    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public String showBookMenu(Model model) {
        model.addAttribute("newBook", new Book("", null));
        model.addAttribute("categories", Book.Category.values());
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "bookmenu";
    }

    @GetMapping("/booksearch ")
    public String bookSearch(@RequestParam("query") String qString, Model model) {
        log.info("Searching for books with query: {}", qString);
        return "redirect:/user/booksearch/" + qString;
     }



}
