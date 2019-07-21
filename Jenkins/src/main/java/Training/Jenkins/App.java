package Training.Jenkins;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.reflect.ClassPath;

import GlobalFunctions.BrowserRelatedFunctions;
import GlobalFunctions.Constants;
import GlobalFunctions.PropertyFileRelatedFunctions;
import GlobalFunctions.ReportRelatedFunctions;
import GlobalFunctions.TestCaseFileRelatedFunctions;
import GlobalFunctions.UtilityFunctions;
import GlobalFunctions.XmlRelatedFunctions;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args )
    {
		System.out.println("It is here");
    	BrowserRelatedFunctions browserObject = new BrowserRelatedFunctions();
		TestCaseFileRelatedFunctions testCaseObject = new TestCaseFileRelatedFunctions();
		XmlRelatedFunctions xmlObject = new XmlRelatedFunctions();
		PropertyFileRelatedFunctions propertyObject = new PropertyFileRelatedFunctions();
		ReportRelatedFunctions reportObject = new ReportRelatedFunctions();
		UtilityFunctions utilityObject = new UtilityFunctions();
		List <String>listOfMethods = new ArrayList<String>();
		
		//gets all packages in src folder
		File directory = new File(propertyObject.getPropertyValue("JavaFilesLocations"));
	    
		//gets all the files from a directory
	    File[] fList = directory.listFiles();
	    
	    for (File file : fList) {
	    		if (file.isDirectory()) {
	            		
	            	//Start of Code Block
	    			//Following code iterative over all the packages in the project and 
	    			//lists out all the methods and stores them in an ArrayList
	        		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
	
	    			try {
						for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses(file.getName())) {
							Class<?> aClass = Class.forName(info.getName());
							Method[] methods = aClass.getDeclaredMethods();
							for (Method method : methods)
								listOfMethods.add(info.getName()+"."+method.getName());
						}
					} catch (ClassNotFoundException | SecurityException | IOException e) {
						e.printStackTrace();
					}
		    		    //End of Code Block
	        }
	    }
	    System.out.println(listOfMethods);
			
	    //gets list of testcases to be executed along with corresponding XML Names
		LinkedHashMap <String, String> testCasesToBeExecuted = testCaseObject.getTestCasesToBeExecuted();
		for(Entry<String, String> testCasesToBeExecutedEntry: testCasesToBeExecuted.entrySet()) {
			File outputFile = new UtilityFunctions().createFile(utilityObject.setReportName(testCasesToBeExecutedEntry.getKey()));
			new TestCaseFileRelatedFunctions().setTestCaseStatus(true);
			System.out.println("Status is true");
			try {
				xmlObject.setXmlFile(testCasesToBeExecutedEntry.getValue());
					
				//gets list of functions to be executed
				LinkedHashMap<String, String> functionsToBeExecuted = xmlObject.
						getNodeElements(xmlObject.getXmlFile(), "function");
				System.out.println(functionsToBeExecuted);
				for(Entry<String, String> functionsToBeExecutedEntry: functionsToBeExecuted.entrySet()) {	
					for(String methods: listOfMethods) {
						if(methods.contains(functionsToBeExecutedEntry.getKey())) {
							System.out.println("Executing: "+methods);
							//Invoke methods using reflection
							Class<?> className;
								className = Class.forName(methods.substring(
										0, methods.lastIndexOf(".")));
								Method method = className.getDeclaredMethod (functionsToBeExecutedEntry.getKey());
								reportObject.addReportData("info", "FunctionCall: "+method.toString());
								method.invoke (className.newInstance(), null);
						}
					}
				}
				browserObject.getDriver().quit();
				reportObject.addTestCaseData(testCasesToBeExecutedEntry.getKey(), 
						new TestCaseFileRelatedFunctions().isTestCaseStatus(), outputFile.getAbsolutePath());
				reportObject.createReport(outputFile);
			} catch (ClassNotFoundException | NoSuchMethodException | 
					SecurityException | IllegalAccessException | 
					IllegalArgumentException | InvocationTargetException | 
					InstantiationException | NullPointerException e) {
				new TestCaseFileRelatedFunctions().setTestCaseStatus(false);
				new ReportRelatedFunctions().addReportData("error", e);
				reportObject.addTestCaseData(testCasesToBeExecutedEntry.getKey(), 
						new TestCaseFileRelatedFunctions().isTestCaseStatus(), outputFile.getAbsolutePath());
				reportObject.createReport(outputFile);
				continue;
			}
		}
		File consolidatedOutputFile = new UtilityFunctions().createFile(utilityObject.setReportName("consolidated"));
		reportObject.createConsolidatedReport(consolidatedOutputFile);
				
    }
}
