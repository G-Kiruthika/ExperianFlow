package pomPages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class HomePage{
	WebDriver driver;
	WebDriverWait wait;
	
	@FindBy(xpath="//div[@class='d-lg-flex align-items-lg-center']//li[5]//button")
	WebElement loansNavLink;

	@FindBy(xpath="//a[text()='All loan types']")
	WebElement loanTypes;
	@FindBy(xpath="//a[text()='Personal loan calculator']")
	WebElement loanCalculatorLink;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
	}
	
	public void clickLoanNav() {
		wait.until(ExpectedConditions.visibilityOf(loansNavLink));
		loansNavLink.click();
	}
	public void clickLoanCalculator() {
		Actions actions = new Actions(driver);
		actions.moveToElement(loanTypes).perform();
		wait.until(ExpectedConditions.visibilityOf(loanCalculatorLink));
		loanCalculatorLink.click();	
	}
	
}