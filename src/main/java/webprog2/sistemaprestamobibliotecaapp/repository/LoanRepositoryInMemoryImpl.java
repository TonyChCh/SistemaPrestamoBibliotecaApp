package webprog2.sistemaprestamobibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import webprog2.sistemaprestamobibliotecaapp.data.Loan;
import java.util.ArrayList;
import java.util.List;

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
    public List<Loan> findLoanByUserId(Long userId) {
        if (userId == null) {
            return new ArrayList<>(); // Return empty list for null userId
        }
        return LoanList.stream()
                .filter(loan -> loan.getUserId().equals(userId))
                .toList();
    }

    @Override
    public void saveLoan(Loan loan) {
        if (loan != null) { LoanList.add(loan); }
    }
}
