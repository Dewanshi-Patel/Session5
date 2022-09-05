package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class HW2 {

	WebDriver driver;
	String browser;
	String url;

//	Element List
	By USERNAME_FIELD = By.xpath("//input[@id='username']");
	By PASSWORD_FIELD = By.xpath("//input[@type='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@class='btn btn-success block full-width']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//span[text()='Dashboard']");
	By CUSTOMER_MENU_FIELD = By.xpath("//span[text()='Customers']");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//a[text()='Add Customer']");
	By ADD_CONTACT_HEADER_FIELD = By.xpath("//h5[text()='Add Contact']");
	By FULL_NAME_FIELD = By.xpath("//input[@name='account']");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@id='cid']");
	By EMAIL_FIELD = By.xpath("//input[@id='email']");
	By PHONE_FIELD = By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@id='address']");
	By CITY_FIELD = By.xpath("//input[@id='city']");
	By STATE_FIELD = By.xpath("//input[@id='state']");
	By ZIP_FIELD = By.xpath("//input[@id='zip']");
	By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@id='country']");
	By TAGS_DROPDOWN_FIELD = By.xpath("//select[@id='tags']");

//	Test Data
	String userName = "demo@techfios.com";
	String passWord = "abc123";
	String fullName = "Dewanshi Patel";
	String email = "abc123@gmail.com";
	String phone = "849-946-0890";
	String address = "124 Hill Rd";
	String city = "Sacramento";
	String state = "California";
	String zip = "956820";
	String tag_name = "ABC";

	@BeforeSuite
	public void readConfig() {

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");

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
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().deleteAllCookies();
		driver.get("https://www.techfios.com/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test
	public void login() {

		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(passWord);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", "Wrong Page!!!");

		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
		Assert.assertEquals(driver.findElement(ADD_CONTACT_HEADER_FIELD).getText(), "Add Contact", "Wrong Page!!");

		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName);

		Select sel1 = new Select(driver.findElement(COMPANY_DROPDOWN_FIELD));
		sel1.selectByVisibleText("Apple");

		driver.findElement(EMAIL_FIELD).sendKeys(email);
		driver.findElement(PHONE_FIELD).sendKeys(phone);
		driver.findElement(ADDRESS_FIELD).sendKeys(address);
		driver.findElement(CITY_FIELD).sendKeys(city);
		driver.findElement(STATE_FIELD).sendKeys(state);
		driver.findElement(ZIP_FIELD).sendKeys(zip);

		Select sel2 = new Select(driver.findElement(COUNTRY_DROPDOWN_FIELD));
		sel2.selectByVisibleText("India");

		Select sel3 = new Select(driver.findElement(TAGS_DROPDOWN_FIELD));
		sel3.selectByVisibleText("IT Training");

		driver.findElement(By.xpath("//button[@class='md-btn md-btn-primary waves-effect waves-light']")).click();

	}

//	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}