package pomPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * LoanCalculator class handles input actions and result verification on the loan calculator page.
 * Test Case Reference: HAP-600 TS-002 TC-002
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
        // Wait for page to be fully loaded
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Enters the loan amount into the input field.
     * @param amount The loan amount to enter.
     */
    public void enterLoanAmount(String amount) {
        js.executeScript("arguments[0].scrollIntoView(true);", loanAmtInput);
        wait.until(ExpectedConditions.elementToBeClickable(loanAmtInput));
        loanAmtInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        loanAmtInput.sendKeys(amount);
    }

    /**
     * Enters the interest rate into the input field.
     * @param rate The interest rate to enter.
     */
    public void enterInterestRate(String rate) {
        wait.until(ExpectedConditions.elementToBeClickable(interestRateInput));
        interestRateInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        interestRateInput.sendKeys(rate);
    }

    /**
     * Enters the loan term into the input field.
     * @param term The loan term to enter.
     */
    public void enterTerm(String term) {
        wait.until(ExpectedConditions.elementToBeClickable(termInput));
        termInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        termInput.sendKeys(term);
    }

    /**
     * Clicks the calculate button.
     */
    public void clickButton() {
        wait.until(ExpectedConditions.elementToBeClickable(calculateBtn));
        calculateBtn.click();
    }

    /**
     * Waits for the result text to be visible and returns its text.
     * @return The result text after calculation.
     */
    public String verifyResult() {
        wait.until(ExpectedConditions.visibilityOf(resultText));
        return resultText.getText();
    }
}
