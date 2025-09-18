package pomTest;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pomPages.HomePage;
import pomPages.LoanCalculator;
import org.testng.Assert;

public class testScripts {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    @Test(priority = 1, description = "Navigate to Personal Loan Calculator from Home Page")
    public void testNavigateToLoanCalculator() {
        HomePage home = new HomePage(driver);
        home.clickLoanNav();
        home.clickLoanCalculator();
        Assert.assertTrue(driver.getCurrentUrl().contains("personal-loan-calculator"), "Failed to navigate to Personal Loan Calculator page");
    }

    @Test(priority = 2, description = "Valid Loan Calculation")
    public void testValidLoanCalculation() throws InterruptedException {
        LoanCalculator loanCalc = new LoanCalculator(driver);
        loanCalc.enterLoanAmount("10000");
        loanCalc.enterInterestRate("15");
        loanCalc.enterTerm("5");
        loanCalc.clickButton();
        Thread.sleep(3000); // Wait for calculation
        String result = loanCalc.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Result does not contain expected text");
    }

    @Test(priority = 3, description = "Invalid Loan Amount Input")
    public void testInvalidLoanAmount() throws InterruptedException {
        LoanCalculator loanCalc = new LoanCalculator(driver);
        loanCalc.enterLoanAmount("abc");
        loanCalc.enterInterestRate("10");
        loanCalc.enterTerm("3");
        loanCalc.clickButton();
        Thread.sleep(2000);
        String result = loanCalc.verifyResult();
        Assert.assertTrue(result.toLowerCase().contains("please enter a valid loan amount") || result.toLowerCase().contains("invalid"), "Error message not displayed for invalid loan amount");
    }

    @Test(priority = 4, description = "Empty Fields Validation")
    public void testEmptyFields() throws InterruptedException {
        LoanCalculator loanCalc = new LoanCalculator(driver);
        loanCalc.enterLoanAmount("");
        loanCalc.enterInterestRate("");
        loanCalc.enterTerm("");
        loanCalc.clickButton();
        Thread.sleep(2000);
        String result = loanCalc.verifyResult();
        Assert.assertTrue(result.toLowerCase().contains("please fill out this field") || result.toLowerCase().contains("required"), "Error message not displayed for empty fields");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
