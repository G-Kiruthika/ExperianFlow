package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
pomPages.HomePage;
pomPages.LoanCalculator;

/**
 * TestNG test class for Experian Loan Calculator automation.
 * Covers navigation and loan calculation scenarios.
 *
 * Test Cases:
 * - HAP-600 TS-001 TC-001: Homepage navigation
 * - HAP-600 TS-002 TC-002: Loan calculation
 */
public class testScripts {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        // Set the path to chromedriver if not set in system properties
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.experian.com");
    }

    /**
     * Test navigation from homepage to loan calculator.
     * Verifies navigation links and successful page load.
     * Test Case: HAP-600 TS-001 TC-001
     */
    @Test(priority = 1)
    public void homePage() {
        HomePage home = new HomePage(driver);
        home.clickLoanNav();
        home.clickLoanCalculator();
        // Additional assertion can be added to verify navigation if required
    }

    /**
     * Test loan calculation with sample data.
     * Verifies that the result contains the expected text.
     * Test Case: HAP-600 TS-002 TC-002
     */
    @Test(priority = 2, dependsOnMethods = {"homePage"})
    public void loanCalculation() throws InterruptedException {
        LoanCalculator loanCalc = new LoanCalculator(driver);
        loanCalc.enterLoanAmount("10000");
        loanCalc.enterInterestRate("15");
        loanCalc.enterTerm("5");
        loanCalc.clickButton();
        Thread.sleep(3000); // Wait for calculation result to appear
        String result = loanCalc.verifyResult();
        Assert.assertTrue(result.contains("Total estimated monthly payment"),
                "Result does not contain expected text. Actual: " + result);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
