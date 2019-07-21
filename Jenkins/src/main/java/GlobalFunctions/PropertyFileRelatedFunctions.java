package GlobalFunctions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyFileRelatedFunctions {
	
	public void setPropertyValue(String Key,String Value) {	  
		 
		  

		Properties prop = new Properties();
		OutputStream output = null;

		try {
			FileInputStream input = new FileInputStream("GlobalProperties.properties");
			prop.load(input);
			  input.close();
			output = new FileOutputStream("GlobalProperties.properties");

			// set the properties value
			prop.setProperty(Key, Value);
		
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	  }
	

	/**
	 * Reads Property file
	 * @param propertyName name of the property whose value to be returned
	 * @return value of the propertyName
	 */
	public String getPropertyValue(String propertyName) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("GlobalProperties.properties");

			// load a properties file
			prop.load(input);
			
			// property value
			String value = prop.getProperty(propertyName);
			
			if(prop.getProperty(propertyName).startsWith("user.dir"))
				return new Constants().PresentWorkingDirectory+prop.getProperty(propertyName).replace("user.dir", "");
			else
				return prop.getProperty(propertyName);

		} catch (IOException ex) {
			ex.printStackTrace();
			new ReportRelatedFunctions().addReportData("error", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					new ReportRelatedFunctions().addReportData("error", e);
				}
			}
		}
	return null;
	}
}
