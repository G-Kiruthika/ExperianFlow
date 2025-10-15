package pomPages;

public class LoanCalculator {
    public double calculateLoan(double principal, double rate, int years) {
        return principal * Math.pow(1 + rate, years);
    }
}
