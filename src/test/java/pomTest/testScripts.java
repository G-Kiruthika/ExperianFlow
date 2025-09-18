package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
pomPages.HomePage;
pomPages.LoanCalculator;

/**
 * TestNG test scripts for Experian Loan Calculator functionalities.
 * Covers navigation, input validation, calculation, and error handling.
 */
public class testScripts {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    /**
     * Test navigation from homepage to loan calculator page.
     * TestCaseId: HAP-600 TS-001 TC-001
     */
    @Test(priority = 1)
    public void homePage() {
        HomePage home = new HomePage(driver);
        home.clickLoanNav();
        home.clickLoanCalculator();
    }

    /**
     * Test loan calculation with valid data.
     * TestCaseId: HAP-600 TS-002 TC-002
     */
    @Test(priority = 2, dependsOnMethods = {"homePage"})
    public void loanCalculation() throws InterruptedException {
        LoanCalculator calc = new LoanCalculator(driver);
        calc.enterLoanAmount("10000");
        calc.enterInterestRate("15");
        calc.enterTerm("5");
        calc.clickButton();
        Thread.sleep(3000); // Wait for calculation result
        String result = calc.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Result text validation failed. Actual: " + result);
    }

    /**
     * Test loan calculation with invalid data (input validation & error handling).
     * TestCaseId: HAP-600 TS-003 TC-003
     */
    @Test(priority = 3, dependsOnMethods = {"homePage"})
    public void loanCalculationInvalidInput() throws InterruptedException {
        LoanCalculator calc = new LoanCalculator(driver);
        calc.enterLoanAmount(""); // Empty loan amount
        calc.enterInterestRate("abc"); // Invalid interest rate
        calc.enterTerm("-1"); // Invalid term
        calc.clickButton();
        Thread.sleep(2000); // Wait for error message or validation
        String result = calc.verifyResult();
        Assert.assertFalse(result.contains("Total estimated monthly payment"), "Invalid input should not produce a valid result. Actual: " + result);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
