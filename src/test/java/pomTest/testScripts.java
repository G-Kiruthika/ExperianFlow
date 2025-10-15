package pomTest;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pomPages.HomePage;
import pomPages.LoanCalculator;
import org.testng.Assert;

public class testScripts {
    WebDriver driver;
    HomePage homePage;
    LoanCalculator loanCalculator;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    @Test(priority = 1, description = "Navigate to Loan Calculator page")
    public void navigateToLoanCalculator() {
        homePage = new HomePage(driver);
        homePage.clickLoanNav();
        homePage.clickLoanCalculator();
    }

    @Test(priority = 2, description = "Calculate loan with valid data", dependsOnMethods = "navigateToLoanCalculator")
    public void calculateLoan_ValidData() throws InterruptedException {
        loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("10000");
        loanCalculator.enterInterestRate("15");
        loanCalculator.enterTerm("5");
        loanCalculator.clickButton();
        Thread.sleep(3000); // Wait for result to appear
        String result = loanCalculator.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Result text validation failed.");
    }

    // Example for negative test case (invalid input)
    @Test(priority = 3, description = "Calculate loan with invalid data", dependsOnMethods = "navigateToLoanCalculator")
    public void calculateLoan_InvalidData() throws InterruptedException {
        loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount(""); // Empty loan amount
        loanCalculator.enterInterestRate("15");
        loanCalculator.enterTerm("5");
        loanCalculator.clickButton();
        Thread.sleep(2000);
        String result = loanCalculator.verifyResult();
        Assert.assertTrue(result.contains("Please enter a valid loan amount") || result.contains("required"), "Error message for invalid loan amount not displayed.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
