package apptus;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import apptusTest.LoginPageEle;

public class AddItemsToCart {
	
	 WebDriver driver;
	 LoginPageEle objLoginPageEle;
	
	@BeforeTest
	public void setup()
	{
		//System.setProperty("webdriver.chrome.driver",
		//		"C:\\Users\\Mani\\Documents\\chromedriver_win32\\chromedriver.exe");
		driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get("http://automationpractice.com/");
		
	}
	
	@AfterTest
	public void closeBrowser()
	{
		driver.quit();
	}
	
	@Test
	public void verifyAddingItemsIntoCart()
	{
		objLoginPageEle = new LoginPageEle(driver);
		objLoginPageEle.signIn();
		objLoginPageEle.tShirtClick();
		objLoginPageEle.fadedShortSleevClick();
		objLoginPageEle.addToCart();
		objLoginPageEle.proceedToCheckoutClick();
		objLoginPageEle.productNameVerify();
		objLoginPageEle.verifyColorAndSize();
		objLoginPageEle.verifyTotalPrice();
		
	}
	

		
	
	
	
	
	

}