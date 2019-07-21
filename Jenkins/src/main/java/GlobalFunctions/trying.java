/*package GlobalFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hslf.examples.CreateHyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class trying {

	public static void main(String[] args) {
		PropertyFileRelatedFunctions propertyObj = new PropertyFileRelatedFunctions();
		String excelLocation = propertyObj.getPropertyValue("ExcelLocation");
		LinkedHashMap<String, String> testCasesList = new LinkedHashMap<>();
		try {
            FileInputStream excelFile = new FileInputStream(excelLocation);
            Workbook workbook = new XSSFWorkbook(excelFile);
            CreationHelper createHelper = workbook.getCreationHelper();
            
            //cell style for hyperlinks
            //by default hyperlinks are blue and underlined
            CellStyle hlink_style = workbook.createCellStyle();
            Font hlink_font = workbook.createFont();
            hlink_font.setUnderline(Font.U_SINGLE);
            hlink_font.setColor(IndexedColors.BLUE.getIndex());
            hlink_style.setFont(hlink_font);
            
            Sheet datatypeSheet = workbook.getSheetAt(0);
            int rowCount = datatypeSheet.getLastRowNum();
            Row currentRow = datatypeSheet.getRow(1);
            currentRow.removeCell(currentRow.getCell(6));
            Cell cell = currentRow.createCell(6);
            
            System.out.println(cell.getStringCellValue());
            cell.setCellValue("Report.html");
            Hyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
            link.setAddress("/Users/srinath/eclipse-workspace/FrameworkDemo/Report.html");
            cell.setHyperlink(link);
            cell.setCellStyle(hlink_style);
            
            FileOutputStream excelOut = new FileOutputStream(excelLocation);
            workbook.write(excelOut);
            workbook.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			new ReportRelatedFunctions().addReportData("error", e);
		}
	}

}
*/