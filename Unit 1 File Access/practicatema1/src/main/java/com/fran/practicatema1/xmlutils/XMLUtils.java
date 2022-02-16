package com.fran.practicatema1.xmlutils;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fran.practicatema1.entidades.Covid19Deceased;

/**
 * @author Francisco David Manzanedo Valle.
 */
public class XMLUtils {
	
	/**
	 * 
	 * @param urlCadena
	 * @return
	 */
	public static List<Covid19Deceased> getCovidDeceasedByDate(String urlCadena){
		
		URL url = null;
		List<Covid19Deceased> result = new ArrayList<Covid19Deceased>();
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date;
		
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			url = new URL(urlCadena);
			Document doc = dBuilder.parse(url.openStream());
			doc.getDocumentElement().normalize();
			
			NodeList nNodeList = doc.getElementsByTagName("deceasedCountAtDate");
			
			for(int temp = 0; temp<nNodeList.getLength(); temp++) {
				Node nNode = nNodeList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element)nNode;
					
					date = LocalDate.parse(eElement.getAttribute("date").substring(0, 10), inputFormatter);
					result.add(new Covid19Deceased(date, Integer.parseInt(eElement.getFirstChild().getTextContent())));
				}
			}
			
		} catch (Exception e) { e.printStackTrace(); }
		
		return result;
	}
}
