package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pomPages.HomePage;
import pomPages.LoanCalculator;

public class testScripts {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    // Test navigation to Loan Calculator
    @Test(priority = 1)
    public void testNavigateToLoanCalculator() {
        HomePage home = new HomePage(driver);
        home.clickLoanNav();
        home.clickLoanCalculator();
        Assert.assertTrue(driver.getCurrentUrl().contains("personal-loan-calculator"), "Failed to navigate to Personal Loan Calculator page");
    }

    // Test valid loan calculation
    @Test(priority = 2)
    public void testValidLoanCalculation() throws InterruptedException {
        LoanCalculator calc = new LoanCalculator(driver);
        calc.enterLoanAmount("10000");
        calc.enterInterestRate("15");
        calc.enterTerm("5");
        calc.clickButton();
        Thread.sleep(3000);
        String result = calc.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Calculation result not as expected");
    }

    // Test blank input validation
    @Test(priority = 3)
    public void testBlankInputValidation() throws InterruptedException {
        LoanCalculator calc = new LoanCalculator(driver);
        calc.enterLoanAmount("");
        calc.enterInterestRate("");
        calc.enterTerm("");
        calc.clickButton();
        Thread.sleep(1000);
        String result = calc.verifyResult();
        Assert.assertTrue(result.toLowerCase().contains("please enter"), "Blank input validation failed");
    }

    // Test negative value input
    @Test(priority = 4)
    public void testNegativeInputValidation() throws InterruptedException {
        LoanCalculator calc = new LoanCalculator(driver);
        calc.enterLoanAmount("-5000");
        calc.enterInterestRate("-10");
        calc.enterTerm("-2");
        calc.clickButton();
        Thread.sleep(1000);
        String result = calc.verifyResult();
        Assert.assertTrue(result.toLowerCase().contains("invalid") || result.toLowerCase().contains("please enter"), "Negative input validation failed");
    }

    // Test non-numeric input
    @Test(priority = 5)
    public void testNonNumericInputValidation() throws InterruptedException {
        LoanCalculator calc = new LoanCalculator(driver);
        calc.enterLoanAmount("abcd");
        calc.enterInterestRate("xyz");
        calc.enterTerm("pqrs");
        calc.clickButton();
        Thread.sleep(1000);
        String result = calc.verifyResult();
        Assert.assertTrue(result.toLowerCase().contains("invalid") || result.toLowerCase().contains("please enter"), "Non-numeric input validation failed");
    }

    // Test system failure handling (simulate by closing browser before calculation)
    @Test(priority = 6)
    public void testSystemFailureHandling() {
        driver.quit();
        try {
            LoanCalculator calc = new LoanCalculator(driver);
            calc.enterLoanAmount("10000");
            calc.enterInterestRate("15");
            calc.enterTerm("5");
            calc.clickButton();
            Assert.fail("System failure not handled as expected");
        } catch (Exception e) {
            Assert.assertTrue(true, "System failure handled gracefully");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
