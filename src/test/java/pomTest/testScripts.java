package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pomPages.HomePage;
import pomPages.LoanCalculator;
import static org.testng.Assert.assertTrue;

public class testScripts {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    @Test(priority = 1)
    public void homePage() {
        HomePage home = new HomePage(driver);
        home.clickLoanNav();
        home.clickLoanCalculator();
    }

    @Test(priority = 2, dependsOnMethods = {"homePage"})
    public void loanCalculation() throws InterruptedException {
        LoanCalculator loanCalc = new LoanCalculator(driver);
        loanCalc.enterLoanAmount("10000");
        loanCalc.enterInterestRate("15");
        loanCalc.enterTerm("5");
        loanCalc.clickButton();
        Thread.sleep(3000);
        String result = loanCalc.verifyResult();
        assertTrue(result.contains("Total estimated monthly payment"), "Result text validation failed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
