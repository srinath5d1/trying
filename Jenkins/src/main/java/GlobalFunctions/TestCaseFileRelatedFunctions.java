package GlobalFunctions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class TestCaseFileRelatedFunctions {
	
	static boolean testCaseStatus;
 
    public static boolean isTestCaseStatus() {
		return testCaseStatus;
	}

	public static void setTestCaseStatus(boolean testCaseStatus) {
		TestCaseFileRelatedFunctions.testCaseStatus = testCaseStatus;
	}

	public LinkedHashMap<String, String> getTestCasesToBeExecuted() {
    		String True = "Yes"; 
        BufferedReader br = null;
        LinkedHashMap<String, String> testCasesList = new LinkedHashMap<>();
 
        try {
            br = new BufferedReader(new FileReader("datafile.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(values[4].equals(True))
                		testCasesList.put(values[1], values[3]);
            }
            return testCasesList;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
}