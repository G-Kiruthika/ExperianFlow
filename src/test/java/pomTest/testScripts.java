package pomTest;

import pomPages.HomePage;
import pomPages.LoanCalculator;

public class testScripts {
    public static void main(String[] args) {
        HomePage home = new HomePage();
        home.openHome();
        LoanCalculator calc = new LoanCalculator();
        double result = calc.calculateLoan(10000, 0.05, 5);
        System.out.println("Calculated Loan: " + result);
    }
}
