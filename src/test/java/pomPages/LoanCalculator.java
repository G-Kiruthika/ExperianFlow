package pomPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoanCalculator {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @FindBy(xpath = "//input[@id='loanAmount']")
    WebElement loanAmtInput;

    @FindBy(xpath = "//input[@id='interestRate']")
    WebElement interestRateInput;

    @FindBy(xpath = "//input[@id='term']")
    WebElement termInput;

    @FindBy(xpath = "//form[@class='mb-4 border rouded rounded-3 p-3 p-lg-4 ']/button")
    WebElement calculateBtn;

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

    // Enters the loan amount
    public void enterLoanAmount(String n) {
        js.executeScript("arguments[0].scrollIntoView(true);", loanAmtInput);
        wait.until(ExpectedConditions.elementToBeClickable(loanAmtInput));
        loanAmtInput.clear();
        loanAmtInput.sendKeys(n);
    }

    // Enters the interest rate
    public void enterInterestRate(String n) {
        wait.until(ExpectedConditions.elementToBeClickable(interestRateInput));
        interestRateInput.clear();
        interestRateInput.sendKeys(n);
    }

    // Enters the loan term
    public void enterTerm(String n) {
        wait.until(ExpectedConditions.elementToBeClickable(termInput));
        termInput.clear();
        termInput.sendKeys(n);
    }

    // Clicks the Calculate button
    public void clickButton() {
        wait.until(ExpectedConditions.elementToBeClickable(calculateBtn));
        calculateBtn.click();
    }

    // Returns the result text after calculation
    public String verifyResult() {
        wait.until(ExpectedConditions.visibilityOf(resultText));
        return resultText.getText();
    }
}
