package pomPages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoanCalculator{
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	@FindBy(xpath="//input[@id='loanAmount']")
	WebElement loanAmtInput;
	@FindBy(xpath="//input[@id='interestRate']")
	WebElement interestRateInput;
	@FindBy(xpath="//input[@id='term']")
	WebElement termInput;
	
	public LoanCalculator(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
	}
	public void enterLoanAmount(String n) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView(true);", loanAmtInput);
		wait.until(ExpectedConditions.elementToBeClickable(loanAmtInput));
		loanAmtInput.clear();
		loanAmtInput.sendKeys(n);
	}
	public void enterInterestRate(String n) {
		wait.until(ExpectedConditions.elementToBeClickable(interestRateInput));
		interestRateInput.clear();
		interestRateInput.sendKeys(n);
	}
	public void enterTerm(String n) {
		wait.until(ExpectedConditions.elementToBeClickable(termInput));
		termInput.clear();
		termInput.sendKeys(n);
	}
}