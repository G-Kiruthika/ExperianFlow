package pomTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.testng.Assert;
import org.testng.AssertJUnit;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import pomPages.*;

public class testScripts{
	WebDriver driver;
	@BeforeClass
	public void setup() {
		driver = new ChromeDriver();
		 
        driver.manage().window().maximize();
		driver.get("https://www.experian.com");
		System.out.println("Navigating to url");
		
	}
	@Test(priority=1)
	public void homePage() {
		HomePage home = new HomePage(driver);
		home.clickLoanNav();
		home.clickLoanCalculator();
	}
	
	@Test(priority=2)
	public void loanCalculation() throws Exception {
		LoanCalculator loan = new LoanCalculator(driver);
		loan.enterLoanAmount("10000");
		loan.enterInterestRate("15");
		loan.enterTerm("5");
		Thread.sleep(3000);
	}
	
	@AfterClass
	public void tearDown() {
		driver.close();
	}
}