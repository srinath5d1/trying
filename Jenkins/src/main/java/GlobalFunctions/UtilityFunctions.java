package GlobalFunctions;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import GlobalFunctions.BrowserRelatedFunctions;

import GlobalFunctions.ReportRelatedFunctions;
import ScreenObjects.HomeScreenObjects;
public class UtilityFunctions {

	/**
	 * To verify actual and expected values
	 * @param expectedValue
	 * @param actualValue
	 */
	public void verification(String expectedValue, String actualValue) {
		try {
			ReportRelatedFunctions reportObject = new ReportRelatedFunctions();
			if(expectedValue.equals(actualValue))
				reportObject.addReportData("pass", "Expected Value="+expectedValue+"<p>"
						+ "Actual Value="+actualValue, takeScreenshot());
			else {
				reportObject.addReportData("fail", "Expected Value="+expectedValue+"<p>"
						+ "Actual Value="+actualValue, takeScreenshot());
				new TestCaseFileRelatedFunctions().setTestCaseStatus(false);
			}
		} catch(Exception e) {
			new TestCaseFileRelatedFunctions().setTestCaseStatus(false);
			e.printStackTrace();
			new ReportRelatedFunctions().addReportData("error", e);
		}
	}
	
	/**
	 * Takes screenshot and saves it in ScreenshotFolder
	 * @return Screenshot location
	 */
	public String takeScreenshot() {
		try {
			TakesScreenshot screenshot =((TakesScreenshot)new BrowserRelatedFunctions().getDriver());
			File sourceFile=screenshot.getScreenshotAs(OutputType.FILE);
			String destinationFileName = new PropertyFileRelatedFunctions().getPropertyValue("ScreenshotFolder")+
	        		new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())+".jpg";
	        File destinationFile=createFile(destinationFileName);
	        FileUtils.copyFile(sourceFile, destinationFile);
	        System.out.println(destinationFileName);
			return destinationFileName;
		} catch(Exception e) {
			new TestCaseFileRelatedFunctions().setTestCaseStatus(false);
			e.printStackTrace();
			new ReportRelatedFunctions().addReportData("error", e);
		}
		return null;
	}
	
	/**
	 * Creates a file and all parent directories
	 * @param fileName location of the file to be created
	 * @return created file
	 */
	public File createFile(String fileName) {
		try {
			File outputFile = new File(fileName);
			outputFile.getParentFile().mkdirs();
			if(outputFile.exists())
				createFile(setReportName(fileName));
			else
				outputFile.createNewFile();
			return outputFile;
		} catch(Exception e) {
			e.printStackTrace();
			new ReportRelatedFunctions().addReportData("error", e);
		}
		return null;
	}
	
	
	public String setReportName(String testCaseName) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String outputFolder = new PropertyFileRelatedFunctions().
				getPropertyValue("OutputReportFolder")+testCaseName+"/";
		String reportName = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		System.out.println(outputFolder+reportName+".html");
		return outputFolder+reportName+".html";
	}
	
	public void firsttry() {
		try {
			WebDriver driver = new BrowserRelatedFunctions().getDriver();
			XmlRelatedFunctions xmlObject = new XmlRelatedFunctions();
			new HomeScreenObjects();
			String actualText = driver.findElement(By.xpath(HomeScreenObjects.toContinueToGmail)).getText();
			if(xmlObject.getVariableValue(xmlObject.getXmlFile(), "toContinueToGmail") != null)
				verification(xmlObject.getVariableValue(xmlObject.getXmlFile(), "toContinueToGmail"), actualText);
		} catch(Exception e) {
			e.printStackTrace();
			new ReportRelatedFunctions().addReportData("error", e);
		}
	}
	
	
	/**
	* If possible this method opens the default browser to the specified web page.
	* If not it notifies the user of webpage's url so that they may access it
	* manually.
	* 
	* @param url
	*            - this can be in the form of a web address (http://www.mywebsite.com)
	*            or a path to an html file or SVG image file e.t.c 
	*/
	public void openInBrowser(String url)
	{
	    try
	        {
	            URI uri = new URL(url).toURI();
	            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
	                desktop.browse(uri);
	        }
	    catch (Exception e)
	        {
	            /*
	             *  I know this is bad practice 
	             *  but we don't want to do anything clever for a specific error
	             */
	            e.printStackTrace();

	            // Copy URL to the clipboard so the user can paste it into their browser
	            StringSelection stringSelection = new StringSelection(url);
	            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
	            clpbrd.setContents(stringSelection, null);
	            // Notify the user of the failure
	            JOptionPane.showMessageDialog(null, "This program just tried to open a webpage." + "\n"
	                + "The URL has been copied to your clipboard, simply paste into your browser to access."
	                + "Webpage: " + url);
	        }
	}
}
