package webprog2.sistemaprestamobibliotecaapp.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.tags.Param;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.service.LoanService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/user/loan")
@SessionAttributes("cart")
public class LoanController {

    private final LoanService loanService;

    // Constructor injection of UserService
    public LoanController(LoanService loanService) { this.loanService = loanService;}

    @ModelAttribute("cart")
    public List<Book> createCart() {
        return new ArrayList<>();  // Carrito vacío al inicio
    }

    @GetMapping("/loanhistory")
    public String showLoanHistory(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        List<Loan> loanHistory = loanService.getLoansForUser(user.getId());

        if (!loanHistory.isEmpty()) {
            log.info("Loan history for user {}: {}", user.getUserName(), loanHistory);
        } else {
            log.info("No loan history found for user {}.", user.getUserName());
        }
        model.addAttribute("loanHistory", loanHistory);
        return "loanhistory";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("bookId") Long bookId,
                            @ModelAttribute("cart") List<Book> cart) {
        if (loanService.addBookToCart(bookId, cart)) {
            log.info("Book with ID {} added to cart.", bookId);
        } else {
            log.warn("Attempted to add unavailable book with ID {} to cart.", bookId);
        }
        return "redirect:/book/menu";
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam("bookId") Long bookId,
                                 @ModelAttribute("cart") List<Book> cart) {
        if (loanService.removeBookFromCart(bookId, cart)) {
            log.info("Book with ID {} removed from cart.", bookId);
        }
        return "redirect:/book/menu";
    }

    @PostMapping("/confirm")
    public String confirmLoan(@ModelAttribute("cart") List<Book> cart,
                              @AuthenticationPrincipal User user,
                              RedirectAttributes redirectAttributes) {
        // Realizar el préstamo de los libros en el carrito para el usuario
        loanService.loanBookToUser(user.getId(), cart);

        log.info("Loan confirmed for user {} with {} books.", user.getUserName(), cart.size());
        // Guardar mensaje de éxito antes de limpiar
        redirectAttributes.addFlashAttribute("successMessage",
                "¡Préstamo confirmado exitosamente! Se prestaron " + cart.size() + " libros.");
        // Limpiar carrito después del préstamo
        cart.clear();
        return "redirect:/book/menu";
    }

    @PostMapping("/return")
    public String returnLoan(@RequestParam("loanId") Long loanId,
                             RedirectAttributes redirectAttributes){
        loanService.returnLoan(loanId);
        log.info("Loan with id: {} was returned", loanId);
        redirectAttributes.addFlashAttribute("successMessage",
                "¡Préstamo devuelto exitosamente!");
        return "redirect:/user/loan/loanhistory";
    }
}
