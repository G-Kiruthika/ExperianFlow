package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[@class='d-lg-flex align-items-lg-center']//li[5]//button")
    WebElement loansNavLink;

    @FindBy(xpath = "//a[text()='All loan types']")
    WebElement loanTypes;

    @FindBy(xpath = "//a[text()='Personal loan calculator']")
    WebElement loanCalculatorLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Navigates to the Loans section
    public void clickLoanNav() {
        wait.until(ExpectedConditions.visibilityOf(loansNavLink));
        loansNavLink.click();
    }

    // Navigates to the Personal Loan Calculator via hover and click
    public void clickLoanCalculator() {
        Actions actions = new Actions(driver);
        actions.moveToElement(loanTypes).perform();
        wait.until(ExpectedConditions.visibilityOf(loanCalculatorLink));
        loanCalculatorLink.click();
    }
}
