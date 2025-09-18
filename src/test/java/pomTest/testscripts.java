package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pomPages.HomePage;
import pomPages.LoanCalculator;

/**
 * Test scripts for Experian Loan Calculator using POM.
 * Each test case is mapped to a method with @Test annotation.
 */
public class testscripts {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        // Set the path to chromedriver if needed
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    /**
     * Test navigation from homepage to loan calculator page.
     * Traceability: Navigation test case.
     */
    @Test(priority = 1)
    public void homePage() {
        HomePage home = new HomePage(driver);
        home.clickLoanNav();
        home.clickLoanCalculator();
    }

    /**
     * Test loan calculation with sample data and verify result.
     * Traceability: Loan calculation test case.
     */
    @Test(priority = 2, dependsOnMethods = {"homePage"})
    public void loanCalculation() throws InterruptedException {
        LoanCalculator calc = new LoanCalculator(driver);
        // Test data: Loan Amount = 10000, Interest Rate = 15, Term = 5
        calc.enterLoanAmount("10000");
        calc.enterInterestRate("15");
        calc.enterTerm("5");
        calc.clickButton();
        Thread.sleep(3000); // Wait for result to appear
        String result = calc.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Result text validation failed. Actual: " + result);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
