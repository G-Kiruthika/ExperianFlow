package pomPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * LoanCalculator class handles input and verification on the loan calculator page.
 * Test Case Traceability: Loan calculation input and result verification steps.
 */
public class LoanCalculator {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // Loan amount input field
    @FindBy(xpath = "//input[@id='loanAmount']")
    WebElement loanAmtInput;

    // Interest rate input field
    @FindBy(xpath = "//input[@id='interestRate']")
    WebElement interestRateInput;

    // Loan term input field
    @FindBy(xpath = "//input[@id='term']")
    WebElement termInput;

    // Calculate button
    @FindBy(xpath = "//form[@class='mb-4 border rouded rounded-3 p-3 p-lg-4 ']//button")
    WebElement calculateBtn;

    // Result text after calculation
    @FindBy(xpath = "//div[@class='bg-light rounded-3 p-4 d-flex flex-wrap text-center mb-5']//span")
    WebElement resultText;

    public LoanCalculator(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        // Wait for page to load
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Enters the loan amount.
     * @param n Loan amount as String
     */
    public void enterLoanAmount(String n) {
        js.executeScript("arguments[0].scrollIntoView(true);", loanAmtInput);
        wait.until(ExpectedConditions.elementToBeClickable(loanAmtInput));
        loanAmtInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        loanAmtInput.sendKeys(n);
    }

    /**
     * Enters the interest rate.
     * @param n Interest rate as String
     */
    public void enterInterestRate(String n) {
        wait.until(ExpectedConditions.elementToBeClickable(interestRateInput));
        interestRateInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        interestRateInput.sendKeys(n);
    }

    /**
     * Enters the loan term.
     * @param n Loan term as String
     */
    public void enterTerm(String n) {
        wait.until(ExpectedConditions.elementToBeClickable(termInput));
        termInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        termInput.sendKeys(n);
    }

    /**
     * Clicks the calculate button.
     */
    public void clickButton() {
        wait.until(ExpectedConditions.elementToBeClickable(calculateBtn));
        calculateBtn.click();
    }

    /**
     * Returns the result text after calculation.
     * @return Result text
     */
    public String verifyResult() {
        wait.until(ExpectedConditions.visibilityOf(resultText));
        return resultText.getText();
    }
}
