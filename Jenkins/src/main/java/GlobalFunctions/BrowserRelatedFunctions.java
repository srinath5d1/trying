package GlobalFunctions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import GlobalFunctions.PropertyFileRelatedFunctions;
public class BrowserRelatedFunctions {

	public static WebDriver driver;
	
	/**
	 * @return driver
	 */
	public WebDriver getDriver() {
		return driver;
	}
	
	/**
	 * Takes the Browser name and Driver location from Properties files<p>
	 * starts the Browser and navigates to the Project URL
	 */
	public void startBrowser() {
		try {
			
			PropertyFileRelatedFunctions propertyObject = new PropertyFileRelatedFunctions();
			String browser = propertyObject.getPropertyValue("Browser");
			
			switch(browser) {
				case "Internet Explorer":
					break;
				case "Firefox":	
					System.setProperty("webdriver.firefox.marionette",
							propertyObject.getPropertyValue("BrowserDriverLocation"));
					driver = new FirefoxDriver();
					driver.manage().window().maximize();
					driver.get(propertyObject.getPropertyValue("ProjectURL"));
					break;
				case "Google Chrome":
					System.out.println("Starting browser at "+propertyObject.getPropertyValue("BrowserDriverLocation"));
					System.setProperty("webdriver.chrome.driver",
							propertyObject.getPropertyValue("BrowserDriverLocation"));
					driver = new ChromeDriver();
					
					//Store the current window handle
					String currentWindowHandle = BrowserRelatedFunctions.driver.getWindowHandle();

					//run your javascript and alert code
					((JavascriptExecutor)BrowserRelatedFunctions.driver).executeScript("alert('Test')"); 
					BrowserRelatedFunctions.driver.switchTo().alert().accept();

					//Switch back to to the window using the handle saved earlier
					BrowserRelatedFunctions.driver.switchTo().window(currentWindowHandle);
					driver.get(propertyObject.getPropertyValue("ProjectURL"));
					
					break;
			}
		} catch(Exception e) {
			e.printStackTrace();
			new TestCaseFileRelatedFunctions().setTestCaseStatus(false);
			new ReportRelatedFunctions().addReportData("error", e);
		}
	}
}
