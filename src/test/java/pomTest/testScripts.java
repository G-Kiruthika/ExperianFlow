package pomTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pomPages.HomePage;
import pomPages.LoanCalculator;
import org.testng.Assert;

public class testScripts {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    @Test(priority = 1)
    public void homePageNavigation() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLoanNav();
        homePage.clickLoanCalculator();
    }

    @Test(priority = 2)
    public void loanCalculation() throws InterruptedException {
        LoanCalculator loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("10000");
        loanCalculator.enterInterestRate("15");
        loanCalculator.enterTerm("5");
        loanCalculator.clickButton();
        Thread.sleep(3000);
        String result = loanCalculator.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"), "Result text validation failed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
