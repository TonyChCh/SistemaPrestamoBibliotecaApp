package webprog2.sistemaprestamobibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LoanRepositoryInMemoryImpl implements LoanRepository {

    private final List<Loan> LoanList = new ArrayList<>();


    @Override
    public Loan findLoanByLoanId(Long loanId) {
        if (loanId == null) {
            return null;
        }
        return LoanList.stream()
                .filter(loan -> loan.getId().equals(loanId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<List<Loan>> findLoanByUserId(Long userId) {
        if (userId == null) {
            return Optional.empty();
        }
        List<Loan> userLoans = LoanList.stream()
                .filter(loan -> loan.getUserId().equals(userId))
                .toList();
        return Optional.of(userLoans);
    }

    @Override
    public void saveLoan(Loan loan) {
        if (loan != null) { LoanList.add(loan); }
    }
}
