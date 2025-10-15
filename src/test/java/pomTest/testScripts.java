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

    @Test(priority = 1, description = "Navigate to Loan Calculator page")
    public void homePageNavigation() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLoanNav();
        homePage.clickLoanCalculator();
    }

    @Test(priority = 2, description = "Calculate loan and verify result")
    public void loanCalculation() throws InterruptedException {
        LoanCalculator loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("10000");
        loanCalculator.enterInterestRate("15");
        loanCalculator.enterTerm("5");
        loanCalculator.clickButton();
        Thread.sleep(3000); // Wait for calculation result
        String result = loanCalculator.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Result text validation failed. Actual: " + result);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
