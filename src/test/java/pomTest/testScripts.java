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
    HomePage homePage;
    LoanCalculator loanCalculator;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
        homePage = new HomePage(driver);
        loanCalculator = new LoanCalculator(driver);
    }

    @Test
    public void testNavigateToLoanCalculator() {
        homePage.clickLoanNav();
        Assert.assertTrue(driver.getCurrentUrl().contains("loan-calculator"), "Navigation to Loan Calculator failed.");
    }

    @Test(dependsOnMethods = "testNavigateToLoanCalculator")
    public void testLoanCalculation() throws InterruptedException {
        loanCalculator.enterLoanAmount("5000");
        String result = loanCalculator.getResultText();
        Assert.assertTrue(result.contains("5000"), "Loan calculation result is incorrect.");
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}