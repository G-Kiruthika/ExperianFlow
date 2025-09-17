package pomTest;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pomPages.HomePage;
import pomPages.LoanCalculator;
import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class testscripts {
    WebDriver driver;
    HomePage homePage;
    LoanCalculator loanCalculator;

    @BeforeClass
    public void setup() {
        // Set up ChromeDriver (ensure chromedriver is in PATH)
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.experian.com/");
    }

    @Test(priority = 1)
    public void verifyHomePageNavigationLinks() {
        // Test Case HAP-600 TS-001 TC-001
        homePage = new HomePage(driver);
        // Verify navigation links are displayed
        assertTrue(homePage.loansNavLink.isDisplayed(), "Loans navigation link should be visible");
    }

    @Test(priority = 2)
    public void verifyLoansNavPopup() {
        // Test Case HAP-600 TS-002 TC-001
        homePage = new HomePage(driver);
        homePage.clickLoanNav();
        assertTrue(homePage.loanTypes.isDisplayed(), "Loan types popup should be displayed");
    }

    @Test(priority = 3)
    public void navigateToLoanCalculator() {
        // Test Case HAP-600 TS-003 TC-001
        homePage = new HomePage(driver);
        homePage.clickLoanNav();
        homePage.clickLoanCalculator();
        // After click, LoanCalculator page should load
        loanCalculator = new LoanCalculator(driver);
        assertTrue(driver.getCurrentUrl().contains("/loans/calculator"), "Should navigate to Personal Loan Calculator page");
    }

    @Test(priority = 4)
    public void loanCalculationTest() throws InterruptedException {
        // Test Case HAP-600 TS-004 TC-001
        loanCalculator = new LoanCalculator(driver);
        loanCalculator.enterLoanAmount("10000");
        loanCalculator.enterInterestRate("5");
        loanCalculator.enterTerm("24");
        loanCalculator.clickButton();
        Thread.sleep(3000); // Wait for calculation result
        String result = loanCalculator.verifyResult();
        assertTrue(result.contains("Total estimated monthly payment"), "Result should contain monthly payment info");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
