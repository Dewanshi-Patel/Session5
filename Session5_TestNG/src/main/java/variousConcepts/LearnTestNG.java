package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LearnTestNG {

	WebDriver driver;
	String browser;
	String url;

//	Element List
	By USERNAME_FIELD = By.xpath("//input[@id='username']");
	By PASSWORD_FIELD = By.xpath("//input[@type='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@class='btn btn-success block full-width']");

	By DASHBOARD_HEADER_FIELD = By.xpath("//span[text()='Dashboard']");
	By CUSTOMER_MENU_LOCATOR = By.xpath("//span[text()='Customers']");
	By ADD_CUSTOMER_MENU_LOCATOR = By.xpath("//a[text()='Add Customer']");
	By ADD_CONTACT_HEADER_LOCATOR = By.xpath("//h5[text()='Add Contact']");
	By FULL_NAME_LOCATOR = By.xpath("//input[@name='account']");
	By COMPANY_DROPDOWN_LOCATOR = By.xpath("//select[@id='cid']");
	By EMAIL_LOCATOR = By.xpath("//input[@id='email']");
	By PHONE_LOCATOR = By.xpath("//input[@id='phone']");
	By ADDRESS_LOCATOR = By.xpath("//input[@id='address']");
	By CITY_LOCATOR = By.xpath("//input[@id='city']");
	By STATE_LOCATOR = By.xpath("//input[@id='state']");
	By ZIP_LOCATOR = By.xpath("//input[@id='zip']");
	By COUNTRY_DROPDOWN_LOCATOR = By.xpath("//select[@id=\"country\"]");
	By TAGS_LOCATOR = By.xpath("//ul[@class='select2-selection__rendered']");

//	Test Data
	String userName = "demo@techfios.com";
	String passWord = "abc123";

	@BeforeSuite
	public void readConfig() {

		// FileReader //Scanner //InputStream //BufferedReader

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);

		} catch (IOException e) {
			e.getStackTrace();

		}

	}

	@BeforeMethod
	public void init() {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

//		System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver.exe");
//		driver = new FirefoxDriver();

		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://www.techfios.com/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

////	@Test(priority=2)
//	public void login() {
//
//		driver.findElement(USERNAME_FIELD).sendKeys(userName);
//		driver.findElement(PASSWORD_FIELD).sendKeys(passWord);
//		driver.findElement(SIGNIN_BUTTON_FIELD).click();
//
//		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", "Wrong Page!!!");
//
//	}
//
////	@Test(priority=1)
//	public void neglogin() {
//
//		driver.findElement(USERNAME_FIELD).sendKeys(userName);
//		driver.findElement(PASSWORD_FIELD).sendKeys(passWord);
//		driver.findElement(SIGNIN_BUTTON_FIELD).click();

//		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", "Wrong Page!!!");

	@Test
	public void addCustomerTest() {

		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(passWord);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", "Wrong Page!!!");

		driver.findElement(CUSTOMER_MENU_LOCATOR).click();

//		WebDriverWait wait = new WebDriverWait(driver, 5);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_CUSTOMER_MENU_LOCATOR));

		waitForElement(driver, 5, ADD_CUSTOMER_MENU_LOCATOR);

		driver.findElement(ADD_CUSTOMER_MENU_LOCATOR).click();

		Assert.assertEquals(driver.findElement(ADD_CONTACT_HEADER_LOCATOR).getText(), "Add Contact", "Wrong Page!!");

		driver.findElement(FULL_NAME_LOCATOR).sendKeys("Tom" + generateRandomNo(999));

		SelectFromDropdown(COMPANY_DROPDOWN_LOCATOR, "Apple");
		
		driver.findElement(EMAIL_LOCATOR).sendKeys(generateRandomNo(99999) + "abc@gmail.com");
		
		driver.findElement(PHONE_LOCATOR).sendKeys(generateRandomNo(999)+ "-456-7894");

		SelectFromDropdown(COUNTRY_DROPDOWN_LOCATOR, "Algeria");

	}

	
	private int generateRandomNo(int boundaryNo) {

		Random rmd = new Random();
		int generatedNo = rmd.nextInt(boundaryNo);
		return generatedNo;

	}

	private void SelectFromDropdown(By locator, String visibleText) {

		Select sel1 = new Select(driver.findElement(locator));
		sel1.selectByVisibleText(visibleText);

	}

	private void waitForElement(WebDriver driver, int timeInSeconds, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

//	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();

	}

}
