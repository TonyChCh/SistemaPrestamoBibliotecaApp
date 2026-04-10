package webprog2.sistemaprestamobibliotecaapp.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webprog2.sistemaprestamobibliotecaapp.data.Book;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import webprog2.sistemaprestamobibliotecaapp.service.LoanService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/loans")
public class ApiLoanController {
    private final LoanService loanService;

    public ApiLoanController(LoanService loanService) { this.loanService = loanService; }

    @GetMapping
    public Iterable<Loan> getAllLoans() { return loanService.getAllLoans();}

    @GetMapping("/{id}")
    public ResponseEntity<List<Book>> getLoanById(@PathVariable Long id) {
        List<Book> BooksInLoan = loanService.getLoanRegistry(id);
        return !BooksInLoan.isEmpty() ? ResponseEntity.ok(BooksInLoan) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Loan> getLoanByUserId(@PathVariable Long userId) {
        Iterable<Loan> userLoans = loanService.getLoansByUserId(userId);
        return userLoans.iterator().hasNext() ? ResponseEntity.ok(userLoans.iterator().next()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/active")
    public Iterable<Loan> getActiveLoans() { return loanService.getActiveLoans(); }

    @GetMapping("/returned")
    public Iterable<Loan> getReturnedLoans() { return loanService.getReturnedLoans(); }

    @PostMapping("/user/{userId}/loan")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan createLoan(@PathVariable Long userId,
                           @RequestBody List<Long> bookIds) {
        return loanService.createLoan(userId, bookIds);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Loan returnLoan(@PathVariable Long id) { return loanService.returnLoan(id); }
}
