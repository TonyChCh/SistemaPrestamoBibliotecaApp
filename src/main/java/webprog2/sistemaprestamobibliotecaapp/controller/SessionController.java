package webprog2.sistemaprestamobibliotecaapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.service.UserService;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class SessionController {

    private final UserService userService;

    // Constructor injection of UserService
    public SessionController(UserService userService) { this.userService = userService; }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, HttpSession session) {
        log.info("Login attempt for user: {}", username);

        Optional<User> userOpt = userService.authenticate(username);
        if (userOpt.isPresent()) {
            log.info("User authenticated successfully: {}", username);
            // Save user in session
            session.setAttribute("user", userOpt.get());
            return "redirect:/user/bookmenu";
        } else {
            log.warn("Authentication failed for user: {}", username);
            return "redirect:/?error=invalid_user";
        }
    }

    @GetMapping("/logout")
    public String logoutGet(HttpSession session) {
        return doLogout(session);
    }

    private String doLogout(HttpSession session) {
        // get the cart and set available to true for each book before invalidating session
        List<Book> cart = (List<Book>) session.getAttribute("cart");
        if (cart != null) {
            cart.forEach(book -> {
                book.setAvailable(true);
            });
        }
        try {
            session.invalidate();
            log.info("Session invalidated successfully on logout.");
        } catch (IllegalStateException e) {
            log.warn("Session was already invalidated.", e);
        }
        return "redirect:/";
    }
}


