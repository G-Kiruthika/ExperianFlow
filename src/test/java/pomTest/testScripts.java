package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pomPages.HomePage;
import pomPages.LoanCalculator;

/**
 * TestNG test scripts for Experian Loan Calculator functionalities.
 * Covers navigation, input, validation, and assertions as per test cases.
 */
public class testScripts {
    WebDriver driver;
    HomePage homePage;
    LoanCalculator loanCalculator;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    /**
     * Test Case: Navigate to Loan Calculator page.
     */
    @Test(priority = 1)
    public void navigateToLoanCalculator() {
        homePage = new HomePage(driver);
        homePage.clickLoanNav();
        homePage.clickLoanCalculator();
        // Optionally assert URL or page title here
    }

    /**
     * Test Case: Positive loan calculation scenario.
     * Inputs: loanAmount=10000, interestRate=15, term=5
     * Expects: Result contains 'Total estimated monthly payment'
     */
    @Test(priority = 2, dependsOnMethods = {"navigateToLoanCalculator"})
    public void positiveLoanCalculation() throws InterruptedException {
        loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("10000");
        loanCalculator.enterInterestRate("15");
        loanCalculator.enterTerm("5");
        loanCalculator.clickButton();
        Thread.sleep(3000); // Wait for calculation result
        String result = loanCalculator.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Result validation failed: " + result);
    }

    /**
     * Test Case: Negative scenario - Invalid loan amount (e.g., empty or non-numeric)
     * Inputs: loanAmount="", interestRate=15, term=5
     * Expects: Error message or no result
     */
    @Test(priority = 3, dependsOnMethods = {"navigateToLoanCalculator"})
    public void negativeLoanAmount() throws InterruptedException {
        loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("");
        loanCalculator.enterInterestRate("15");
        loanCalculator.enterTerm("5");
        loanCalculator.clickButton();
        Thread.sleep(2000);
        String result = loanCalculator.verifyResult();
        Assert.assertTrue(result.isEmpty() || result.contains("Please enter a valid loan amount") || result.contains("required"), "Expected error or empty result for invalid loan amount, got: " + result);
    }

    /**
     * Test Case: Negative scenario - Invalid interest rate (e.g., negative value)
     * Inputs: loanAmount=10000, interestRate="-5", term=5
     * Expects: Error message or no result
     */
    @Test(priority = 4, dependsOnMethods = {"navigateToLoanCalculator"})
    public void negativeInterestRate() throws InterruptedException {
        loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("10000");
        loanCalculator.enterInterestRate("-5");
        loanCalculator.enterTerm("5");
        loanCalculator.clickButton();
        Thread.sleep(2000);
        String result = loanCalculator.verifyResult();
        Assert.assertTrue(result.isEmpty() || result.contains("Please enter a valid interest rate") || result.contains("required"), "Expected error or empty result for invalid interest rate, got: " + result);
    }

    /**
     * Test Case: Negative scenario - Invalid term (e.g., zero or negative)
     * Inputs: loanAmount=10000, interestRate=15, term="0"
     * Expects: Error message or no result
     */
    @Test(priority = 5, dependsOnMethods = {"navigateToLoanCalculator"})
    public void negativeTerm() throws InterruptedException {
        loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("10000");
        loanCalculator.enterInterestRate("15");
        loanCalculator.enterTerm("0");
        loanCalculator.clickButton();
        Thread.sleep(2000);
        String result = loanCalculator.verifyResult();
        Assert.assertTrue(result.isEmpty() || result.contains("Please enter a valid term") || result.contains("required"), "Expected error or empty result for invalid term, got: " + result);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
