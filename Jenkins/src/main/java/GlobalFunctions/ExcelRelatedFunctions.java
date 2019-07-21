/*package GlobalFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import GlobalFunctions.PropertyFileRelatedFunctions;

public class ExcelRelatedFunctions {*/

	/**
	 * Retrieve ExcelLocation from Property file.
	 * @return list of functions to be executed
	 */
/*	public LinkedHashMap<String,String> getTestCasesToBeExecuted() {
		PropertyFileRelatedFunctions propertyObj = new PropertyFileRelatedFunctions();
		String excelLocation = propertyObj.getPropertyValue("ExcelLocation");
		LinkedHashMap<String, String> testCasesList = new LinkedHashMap<>();
		try {
            FileInputStream excelFile = new FileInputStream(new File(excelLocation));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            int rowCount = datatypeSheet.getLastRowNum();

            for (int rowCountLoopVariable = 1; rowCountLoopVariable < rowCount; rowCountLoopVariable++) {
                Row currentRow = datatypeSheet.getRow(rowCountLoopVariable);
                if(currentRow.getCell(4).getStringCellValue().equalsIgnoreCase("Yes")) {
                		testCasesList.put(currentRow.getCell(0).getStringCellValue(), 
                				currentRow.getCell(3).getStringCellValue());
                }
            }
            workbook.close();
            return testCasesList;
		}
		catch (Exception e) {
			e.printStackTrace();
			new ReportRelatedFunctions().addReportData("error", e);
		}
		return null;
	}
}*/
