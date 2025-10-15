package pomTest;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pomPages.HomePage;
import pomPages.LoanCalculator;
import static org.testng.Assert.assertTrue;

/**
 * TestNG test scripts for Experian Loan Calculator automation.
 * Each test method corresponds to a test case and uses POM classes.
 * Traceable to test cases and knowledge base for assertions and error handling.
 */
public class testScripts {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        // Set ChromeDriver path if necessary
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    /**
     * Test Case: Navigate to Loan Calculator page
     * Steps: Click Loans nav, hover All loan types, click Personal loan calculator
     * Expected: Loan calculator page is loaded
     */
    @Test(priority = 1)
    public void testNavigateToLoanCalculator() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLoanNav();
        homePage.clickLoanCalculator();
        // Optionally, assert page title or URL
        assertTrue(driver.getCurrentUrl().contains("personal-loan-calculator"), "Loan Calculator page not loaded.");
    }

    /**
     * Test Case: Calculate loan and verify result
     * Steps: Enter loan amount, interest rate, term, click Calculate
     * Expected: Result contains 'Total estimated monthly payment'
     */
    @Test(priority = 2)
    public void testLoanCalculation() throws InterruptedException {
        LoanCalculator loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("10000");
        loanCalculator.enterInterestRate("15");
        loanCalculator.enterTerm("5");
        loanCalculator.clickButton();
        Thread.sleep(3000); // Wait for calculation result
        String result = loanCalculator.verifyResult();
        assertTrue(result.contains("Total estimated monthly payment"), "Result text validation failed. Actual: " + result);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
