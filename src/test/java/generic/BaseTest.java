package generic;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {
	public static final String CONFIG_PATH="./config.properties";
	public static final String XL_PATH="./data/input.xlsx";
	public static ExtentReports extent;
	
	public WebDriver driver;
	public WebDriverWait wait;
	public ExtentTest test;
	
	@BeforeSuite
	public void initReport()
	{
		extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);
	}
	
	@AfterSuite
	public void generateReport()
	{
		extent.flush();
	}
	
	@Parameters("property")
	@BeforeMethod
	public void preCondition(Method method,@Optional(CONFIG_PATH) String property) throws Exception
	{
		String testName = method.getName();
		test = extent.createTest(testName);
		
		String grid = Utility.getProperty(property,"GRID");
		String grid_url = Utility.getProperty(property,"GRID_URL");
		String browser = Utility.getProperty(property,"BROWSER");
		String appURL = Utility.getProperty(property,"APP_URL");
		String ITO = Utility.getProperty(property,"ITO");
		String ETO = Utility.getProperty(property,"ETO");
		
		if(grid.equalsIgnoreCase("yes"))
		{
			
			if(browser.equalsIgnoreCase("chrome"))
			{
				test.info("Open Chrome Browser in Remote System");
				driver=new RemoteWebDriver(new URL(grid_url),new ChromeOptions());
			}
			else if(browser.equalsIgnoreCase("firefox"))
			{
				test.info("Open Firefox Browser in Remote System");
				driver=new RemoteWebDriver(new URL(grid_url),new FirefoxOptions());
			}
			else
			{
				test.info("Open Edge Browser in Remote System");
				driver=new RemoteWebDriver(new URL(grid_url),new EdgeOptions());
			}	
		}
		else
		{
			if(browser.equalsIgnoreCase("chrome"))
			{
				test.info("Open Chrome Browser in Local System");
				driver=new ChromeDriver();
			}
			else if(browser.equalsIgnoreCase("firefox"))
			{
				test.info("Open Firefox Browser in Local System");
				driver=new FirefoxDriver();
			}
			else
			{
				test.info("Open Edge Browser in Local System");
				driver=new EdgeDriver();
			}	
		}
		
		test.info("Enter the URL:"+appURL);
		driver.get(appURL);
		
		test.info("Maximize the Browser");
		driver.manage().window().maximize();
		
		test.info("Set implicit Wait:"+ITO);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ITO)));
		
		test.info("Set Explicit Wait:"+ETO);
		wait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(ETO)));
	}
	
	@AfterMethod
	public void postCondition(ITestResult testResult) throws IOException
	{
		String testName = testResult.getName();
		
		int status = testResult.getStatus();
		if(status==1)
		{
			test.pass("The Test is Passed");
		}
		else
		{
			String timeStamp = Utility.getTimeStamp();
			Utility.takeScreenshot(driver, "target/"+testName+timeStamp+".png");
			test.fail("The Test is Failed",MediaEntityBuilder.createScreenCaptureFromPath(testName+timeStamp+".png").build());
		}
		test.info("Close the Browser");
		driver.quit();
	}
}
