package GlobalFunctions;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.LinkedHashMap;

public class XmlRelatedFunctions {

	public static String xmlFile;
	
	/**
	 * @return xmlFile
	 */
	public String getXmlFile() {
		return xmlFile;
	}

	/**
	 * Sets xmlFile name for current testcase
	 * @param xmlFile xmlFile name of current testcase
	 */
	public void setXmlFile(String xmlFile) {
		XmlRelatedFunctions.xmlFile = xmlFile;
	}

	/**
	 * 
	 * @param Xml xml name
	 * @param nodeName Node name in the Xml whose elements to be returned
	 * @return List of node elements for the nodeName
	 */
	public LinkedHashMap<String,String> getNodeElements(String Xml, String nodeName) {
		try {
			File fXmlFile = new File(System.getProperty("user.dir")+"//TestcaseXML/"+Xml+".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			LinkedHashMap <String,String> nodeList = new LinkedHashMap<String,String>();

			doc.getDocumentElement().normalize();

			NodeList functionNodeList = doc.getElementsByTagName(nodeName);

			for (int functionLoopVariable = 0; functionLoopVariable < functionNodeList.getLength(); functionLoopVariable++) {

				Node functionNode = functionNodeList.item(functionLoopVariable);
				if (functionNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) functionNode;
					nodeList.put(eElement.getAttribute("name"), eElement.getAttribute("value"));
				}
			}
		return nodeList;
		}
		catch (Exception e) {
			e.printStackTrace();
			new ReportRelatedFunctions().addReportData("error", e);
		}

		return null;
	}
	
	/**
	 * 
	 * @param Xml xml name
	 * @param variableName element to be searched
	 * @return value of variableName
	 */
	public String getVariableValue(String Xml, String variableName) {
		return getNodeElements(Xml, "variable").get(variableName);
	}
}
