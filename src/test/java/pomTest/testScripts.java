package pomTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pomPages.HomePage;
import pomPages.LoanCalculator;
import org.testng.Assert;

public class testScripts {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set the path to chromedriver if necessary
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    // Test: Verify homepage loads and Loans navigation is present
    @Test(priority = 1)
    public void homePage() {
        HomePage home = new HomePage(driver);
        // Step: Click on Loans navigation link
        home.clickLoanNav();
        // Step: Click on Personal loan calculator
        home.clickLoanCalculator();
    }

    // Test: Perform loan calculation and verify result
    @Test(priority = 2, dependsOnMethods = {"homePage"})
    public void loanCalculation() throws InterruptedException {
        LoanCalculator loanCalc = new LoanCalculator(driver);
        // Step: Enter loan details
        loanCalc.enterLoanAmount("10000");
        loanCalc.enterInterestRate("15");
        loanCalc.enterTerm("5");
        // Step: Click Calculate
        loanCalc.clickButton();
        // Wait for result to appear
        Thread.sleep(3000);
        // Step: Verify result text
        String result = loanCalc.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Result text validation failed. Actual: " + result);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
