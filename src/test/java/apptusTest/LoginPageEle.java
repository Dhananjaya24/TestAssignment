package apptusTest;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class LoginPageEle {
	
	SoftAssert softAssertion= new SoftAssert();
	
	WebDriver driver;
	//WebDriverWait w =new WebDriverWait(driver,5);
	
	@FindBy(css=".login")
	WebElement Login;
	
	@FindBy(css="#email")
	WebElement inputEmail;
	
	@FindBy(css="#passwd")
	WebElement inputPassword;
	
	@FindBy(css="button#SubmitLogin")
	WebElement signInButton;
	
	@FindBy(xpath="(//a[@title='T-shirts'])[2]")
	WebElement tShirts; 
	
	@FindBy(xpath="//div[@class='product-image-container']//img")
	WebElement fadedShortSleevClick;
	
	@FindBy(css="#add_to_cart")
	WebElement addToCart;
	
	@FindBy(xpath="//a[@title=\"Proceed to checkout\"]/span")
	WebElement proceedToCheckoutClick;
	
	@FindBy(xpath="(//p[@class='product-name'])[2]")
	WebElement productName;
	String expectedProduct="Faded Short Sleeve T-shirts";
	
	@FindBy(xpath="(//p[@class='product-name'])[2]/following-sibling::small[2]")
	WebElement colorAndSize;
	
	@FindBy(xpath="//td[@class='cart_unit']/span")
	WebElement unitPrice;
	
	@FindBy(xpath="//input[@name='quantity_1_1_0_364543']")
	WebElement quantity;
	
	@FindBy(xpath="//td[@class='cart_total']/span")
	WebElement total;

	
	
	
	public LoginPageEle(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void signIn()
	{
		Login.click();
		inputEmail.sendKeys("jetblue@grr.la");
		inputPassword.sendKeys("jetblue");
		signInButton.click();	
	}
	
	public void tShirtClick()
	{
		tShirts.click();
	}
	
	public void fadedShortSleevClick()
	{
		fadedShortSleevClick.click();
	}
	
	public void addToCart()
	{
		addToCart.click();
		
	}
	
	public void proceedToCheckoutClick()
	{
		//w.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutClick));
		proceedToCheckoutClick.click();
	}
	
	public void productNameVerify()
	{
		String actualProductName=productName.getText().toString();
		assertTrue(expectedProduct.equalsIgnoreCase(actualProductName),"Product names are mis matching");
		System.out.println("product name: "+actualProductName);
		
	}
	
	public void verifyColorAndSize()
	{
		String expColor = "Orange";
		String expSize = "S";
		String[] cs=colorAndSize.getText().split(":");
		String[] cr=cs[1].split(",");
		String actColor=cr[0].trim();
		System.out.println(actColor);
		String actSize=cs[2].trim();
		System.out.println(actSize);
		
		softAssertion.assertEquals(actColor, expColor,"Both Colors are not matching");
		softAssertion.assertEquals(actSize, expSize, "Both Sizes are not matching");
		softAssertion.assertAll();	
	}
	
	public void verifyTotalPrice()
	{
		String qty=quantity.getAttribute("value");
		int quantity = Integer.parseInt(qty);
		
		String up=unitPrice.getText();
		String unitPrice=up.substring(1);
		double unitPrce = Double.parseDouble(unitPrice);
		
		String tl=total.getText();
		String tl1=tl.substring(1);
		double exptotal=Double.parseDouble(tl1);
		
		double actTotal=unitPrce*quantity;
		System.out.println(actTotal);
	
		Assert.assertEquals(actTotal, exptotal,"Both prices are not matching");	
	}
	
}
