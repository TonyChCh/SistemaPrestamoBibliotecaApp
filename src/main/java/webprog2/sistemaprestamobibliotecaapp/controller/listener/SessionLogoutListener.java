package webprog2.sistemaprestamobibliotecaapp.controller.listener;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SessionLogoutListener implements HttpSessionListener {

    private final BookRepository bookRepository;

    // Inyección por constructor
    public SessionLogoutListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        List<Book> cart = (List<Book>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            // Extraemos los IDs para una actualización masiva (Batch)
            List<Long> inCartBookIds = new ArrayList<>();
            cart.forEach(book -> inCartBookIds.add(book.getId()));
            // Liberamos los libros en una sola operación de base de datos
            bookRepository.releaseBooksByIds(inCartBookIds);
            log.info("Sesión expirada: Se liberaron {} libros del carrito.", inCartBookIds.size());
        }
    }
}