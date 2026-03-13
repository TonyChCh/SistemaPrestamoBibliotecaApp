package webprog2.sistemaprestamobibliotecaapp.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequestMapping("/user/loan")
@SessionAttributes({"user", "cart"})
public class LoanController {

    private final LoanService loanService;

    // Constructor injection of UserService
    public LoanController(LoanService loanService) { this.loanService = loanService;}

    @ModelAttribute("cart")
    public List<Book> createCart() {
        return new ArrayList<>();  // Carrito vacío al inicio
    }

    @GetMapping("loanhistory")
    public String showLoanHistory(Model model, @ModelAttribute("user") User user) {
        List<Loan> loanHistory = loanService.getLoansForUser(user);

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
        return "redirect:/user/bookmenu";
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam("bookId") Long bookId,
                                 @ModelAttribute("cart") List<Book> cart) {
        if (loanService.removeBookFromCart(bookId, cart)) {
            log.info("Book with ID {} removed from cart.", bookId);
        }
        return "redirect:/user/bookmenu";
    }

    @PostMapping("/confirm")
    public String confirmLoan(@ModelAttribute("cart") List<Book> cart,
                              @ModelAttribute("user") User user,
                              RedirectAttributes redirectAttributes) {
        int bookCount = cart.size();
        loanService.loanBookToUser(user, cart);
        log.info("Loan confirmed for user {} with {} books.", user.getUserName(), bookCount);
        List<Loan> loanHistory = loanService.getLoansForUser(user);
        // Guardar mensaje de éxito antes de limpiar
        redirectAttributes.addFlashAttribute("successMessage",
                "¡Préstamo confirmado exitosamente! Se prestaron " + bookCount + " libros.");
        // Limpiar carrito después del préstamo
        cart.clear();
        return "redirect:/user/bookmenu";
    }

    @PostMapping("/return")
    public String returnLoan(@RequestParam("loanId") long loanId,
                             RedirectAttributes redirectAttributes){
        loanService.returnLoan(loanId);
        log.info("Loan with id: {} was returned", loanId);
        redirectAttributes.addFlashAttribute("successMessage",
                "¡Préstamo devuelto exitosamente!");
        return "redirect:/user/loan/loanhistory";
    }



}
