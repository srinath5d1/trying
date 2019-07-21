package GlobalFunctions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import GlobalFunctions.PropertyFileRelatedFunctions;
import GlobalFunctions.UtilityFunctions;

public class ReportRelatedFunctions {
	
	public String consolidatedReportPrefix = "<!DOCTYPE html>\n" + 
			"<html>\n" + 
			"<head>\n" + 
			"<meta charset=\"UTF-8\">\n" + 
			"<title>Insert title here</title>\n" + 
			"</head>\n" +  
			"<body>\n" + 
			"	<table width=\"100%\" cellpadding=\"10px\" border=\"1\">\n" + 
			"		<tbody>";

	public String reportPrefix = "<!DOCTYPE html>\n" + 
			"<html>\n" + 
			"<head>\n" + 
			"<meta charset=\"UTF-8\">\n" + 
			"<title>Insert title here</title>\n" + 
			"</head>\n" + 
			"<style>\n" + 
			"	.passRow {\n" + 
			"		color: green;\n" + 
			"	}\n" + 
			"	\n" + 
			"	.failRow {\n" + 
			"		color: red;\n" + 
			"	}\n" + 
			"	\n" + 
			"	.errorRow {\n" + 
			"		color: red;\n" + 
			"	}\n" + 
			"	\n" + 
			"	.infoRow {\n" + 
			"		color: orange;\n" + 
			"	}\n" + 
			"</style>\n" + 
			"<script type=\"text/javascript\">\n" + 
			"	\n" + 
			"	function passChange() {\n" + 
			"		var totalElements = document.getElementsByClassName(\"passRow\");\n" + 
			"		if(document.getElementById(\"Pass\").checked == true) {\n" + 
			"			for(var i=0; i<totalElements.length; i++) {\n" + 
			"				totalElements[i].style.display = \"\";\n" + 
			"			}\n" + 
			"		}\n" + 
			"		\n" + 
			"		else if(document.getElementById(\"Pass\").checked == false) {\n" + 
			"			for(var i=0; i<totalElements.length; i++) {\n" + 
			"				totalElements[i].style.display = \"none\";\n" + 
			"			}\n" + 
			"		}\n" + 
			"	}\n" + 
			"	\n" + 
			"	function failChange() {\n" + 
			"		var totalElements = document.getElementsByClassName(\"failRow\");\n" + 
			"		if(document.getElementById(\"Fail\").checked == true) {\n" + 
			"			for(var i=0; i<totalElements.length; i++) {\n" + 
			"				totalElements[i].style.display = \"\";\n" + 
			"			}\n" + 
			"		}\n" + 
			"		\n" + 
			"		if(document.getElementById(\"Fail\").checked == false) {\n" + 
			"			for(var i=0; i<totalElements.length; i++) {\n" + 
			"				totalElements[i].style.display = \"none\";\n" + 
			"			}\n" + 
			"		}\n" + 
			"	}\n" + 
			"	\n" + 
			"	function errorChange() {\n" + 
			"		var totalElements = document.getElementsByClassName(\"errorRow\");\n" + 
			"		if(document.getElementById(\"Error\").checked == true) {\n" + 
			"			for(var i=0; i<totalElements.length; i++) {\n" + 
			"				totalElements[i].style.display = \"\";\n" + 
			"			}\n" + 
			"		}\n" + 
			"		\n" + 
			"		if(document.getElementById(\"Error\").checked == false) {\n" + 
			"			for(var i=0; i<totalElements.length; i++) {\n" + 
			"				totalElements[i].style.display = \"none\";\n" + 
			"			}\n" + 
			"		}\n" + 
			"	}\n" + 
			"	\n" + 
			"	function infoChange() {\n" + 
			"		var totalElements = document.getElementsByClassName(\"infoRow\");\n" + 
			"		if(document.getElementById(\"Info\").checked == true) {\n" + 
			"			for(var i=0; i<totalElements.length; i++) {\n" + 
			"				totalElements[i].style.display = \"\";\n" + 
			"			}\n" + 
			"		}\n" + 
			"		\n" + 
			"		if(document.getElementById(\"Info\").checked == false) {\n" + 
			"			for(var i=0; i<totalElements.length; i++) {\n" + 
			"				totalElements[i].style.display = \"none\";\n" + 
			"			}\n" + 
			"		}\n" + 
			"	}\n" + 
			"	\n" + 
			"	function checkAll() {\n" + 
			"		document.getElementById(\"Pass\").setAttribute('checked', 'checked');\n" + 
			"		document.getElementById(\"Fail\").setAttribute('checked', 'checked');\n" + 
			"		document.getElementById(\"Error\").setAttribute('checked', 'checked');\n" + 
			"		document.getElementById(\"Info\").setAttribute('checked', 'checked');\n" + 
			"		infoChange();\n" + 
			"		errorChange();\n" + 
			"	}\n" + 
			"</script>\n" + 
			"<body onload=\"checkAll()\">\n" + 
			"	<table width=\"25%\" cellspacing=\"5\">\n" + 
			"		<tbody align=\"center\">\n" + 
			"			<tr>\n" + 
			"				<td><input type=\"checkbox\" id=\"Pass\" onchange=\"passChange()\">Pass</td>\n" + 
			"				<td><input type=\"checkbox\" id=\"Fail\" onchange=\"failChange()\">Fail</td>\n" + 
			"				<td><input type=\"checkbox\" id=\"Error\" onchange=\"errorChange()\">Error</td>\n" + 
			"				<td><input type=\"checkbox\" id=\"Info\" onchange=\"infoChange()\">Info</td>\n" + 
			"		</tr>\n" + 
			"		</tbody>\n" + 
			"	</table>\n" + 
			"	\n" + 
			"	<table width=\"100%\" cellpadding=\"10px\" border=\"1\">\n" + 
			"		<tbody>";
	
	public String reportPostfix = "</tbody>\n" + 
			"	</table>\n" + 
			"</body>\n" + 
			"</html>";
	
	public static StringBuilder reportData = new StringBuilder();
	public static StringBuilder consolidatedReportData = new StringBuilder();
	
	/**
	 * Adds a table row to the output report<p>
	 * Should be called after every verification point
	 * @param status status of the verification point (pass, fail, error and info)
	 * @param data Text to be printed in report file
	 * @param screenshotName Screenshot location of the verification point
	 */
	public void addReportData(String status, String data, String screenshotName) {
		String tableRow = new String();
		switch(status) {
			case "pass":tableRow = "<tr class=\"passRow\" width=\"25%\"><td>Pass</td><td align=\"left\" width=\"75%\">"+data+"<p>\n" + 
					"			<a href="+'"'+screenshotName+'"'+" target=\"_blank\">Screenshot</a></td>\n" + 
					"			</tr>";
						break;
			case "fail":tableRow = "<tr class=\"failRow\" width=\"25%\"><td>Fail</td><td align=\"left\" width=\"75%\">"+data+"<p>\n" + 
					"			<a href="+'\"'+screenshotName+'\"'+" target=\"_blank\">Screenshot</a></td>\n" + 
					"			</tr>";
						break;
			case "error":tableRow = "<tr class=\"errorRow\" width=\"25%\"><td>Error</td><td align=\"left\" width=\"75%\">"+data+"<p>\n" + 
					"			<a href="+'\"'+screenshotName+'\"'+" target=\"_blank\">Screenshot</a></td>\n" + 
					"			</tr>";
						break;
			case "info":tableRow = "<tr class=\"infoRow\" width=\"25%\"><td>Info</td><td align=\"left\" width=\"75%\">"+data+"<p>\n" + 
					"			<a href="+'\"'+screenshotName+'\"'+" target=\"_blank\">Screenshot</a></td>\n" + 
					"			</tr>";
						break;
			
		}
		reportData.append(tableRow+"\n");
	}
	
	/**
	 * Adds a table row to the output report<p>
	 * Should be called in every catch block
	 * @param status status of the verification point (error)
	 * @param e Exception occured
	 */
	public void addReportData(String status, Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		String tableRow = new String();
		switch(status) {
			case "pass":tableRow = "<tr class=\"passRow\" width=\"25%\"><td>Pass</td><td align=\"left\" width=\"75%\">"+exceptionAsString+"<p>\n" + 
					"			</tr>";
						break;
			case "fail":tableRow = "<tr class=\"failRow\" width=\"25%\"><td>Fail</td><td align=\"left\" width=\"75%\">"+exceptionAsString+"<p>\n" + 
					"			</tr>";
						break;
			case "error":tableRow = "<tr class=\"errorRow\" width=\"25%\"><td>Error</td><td align=\"left\" width=\"75%\">"+exceptionAsString+"<p>\n" + 
					"			</tr>";
						break;
			case "info":tableRow = "<tr class=\"infoRow\" width=\"25%\"><td>Info</td><td align=\"left\" width=\"75%\">"+exceptionAsString+"<p>\n" + 
					"			</tr>";
						break;
			
		}
		reportData.append(tableRow+"\n");
	}
	
	/**
	  * Adds a table row to the output report<p>
	 * Should be called after every verification point when screenshot is not available
	 * @param data Text to be printed in report file
	 */
	public void addReportData(String status, String data) {
		String tableRow = new String();
		switch(status) {
			case "pass":tableRow = "<tr class=\"passRow\" width=\"25%\"><td>Pass</td><td align=\"left\" width=\"75%\">"+data+"<p>\n" + 
					"			</tr>";
						break;
			case "fail":tableRow = "<tr class=\"failRow\" width=\"25%\"><td>Fail</td><td align=\"left\" width=\"75%\">"+data+"<p>\n" + 
					"			</tr>";
						break;
			case "error":tableRow = "<tr class=\"errorRow\" width=\"25%\"><td>Error</td><td align=\"left\" width=\"75%\">"+data+"<p>\n" + 
					"			</tr>";
						break;
			case "info":tableRow = "<tr class=\"infoRow\" width=\"25%\"><td>Info</td><td align=\"left\" width=\"75%\">"+data+"<p>\n" + 
					"			</tr>";
						break;
			
		}
		reportData.append(tableRow+"\n");
	}
	
	
	public void addTestCaseData(String testCaseName, boolean Status, String reportLink) {
		if(Status) {
			consolidatedReportData.append("<tr width=\"35%\"><td>"+testCaseName+"</td>"
					+ "<td align=\"center\" color=\"green\" width=\"30%\">PASS</td>"
					+ "<td><a href='"+reportLink+"' target=\"_blank\">Report</a></td>\n"
					+ "			</tr>"+"\n");
		}
		else {
			consolidatedReportData.append("<tr width=\"35%\"><td>"+testCaseName+"</td>"
					+ "<td align=\"center\" color=\"red\" width=\"30%\">FAIL</td>"
					+ "<td><a href='"+reportLink+"' target=\"_blank\">Report</a></td>\n"
					+ "			</tr>"+"\n");
		}
	}
	
	/**
	 * Creates Report file using OutputReportFolder from Property file
	 * @return Report file name
	 * @throws IOException
	 */
	public void createReport(File outputFile) {
		
		FileWriter writer;
		try {
			writer = new FileWriter(outputFile);
			writer.write(reportPrefix+"\n"+reportData+reportPostfix); 
		    writer.flush();
		    writer.close();
		    reportData.delete(0, reportData.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	public void createConsolidatedReport(File outputFile) {
		//System.out.println(consolidatedReportData);
		FileWriter writer;
		try {
			writer = new FileWriter(outputFile);
			writer.write(consolidatedReportPrefix+"\n"+consolidatedReportData+reportPostfix); 
		    writer.flush();
		    writer.close();
		    consolidatedReportData.delete(0, reportData.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
