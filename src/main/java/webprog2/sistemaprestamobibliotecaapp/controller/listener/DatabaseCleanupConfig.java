package webprog2.sistemaprestamobibliotecaapp.controller.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import webprog2.sistemaprestamobibliotecaapp.repository.BookRepository;

@Component
public class DatabaseCleanupConfig {

    @Autowired
    private BookRepository bookRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void clearStaleReservations() {
        // Al arrancar, cualquier libro que haya quedado RESERVED se libera
        // porque las sesiones anteriores ya no existen.
        bookRepository.releaseAllReservedBooks();
        System.out.println("SISTEMA: Se han liberado reservas antiguas al iniciar.");
    }
}
