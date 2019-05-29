package proba;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;

public class Proba {
	
	public static List<File> zaZip = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		
//		C:\Users\COFITIGAR\Desktop\slike
		
    	System.out.println("Putanja foldera: ");

    	Scanner scanner = new Scanner(System.in);
    	String putanja = scanner.nextLine();
    	scanner.close();
    	File dir = new File(putanja);
    	
    	try {
    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.newDocument();
			org.w3c.dom.Element rootElement = doc.createElement("slike");
			doc.appendChild(rootElement);
			
			for (File file : dir.listFiles()) {
				if (file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".gif") || file.getName().endsWith(".png")) {
					zaZip.add(file);
					
					
					
					
				org.w3c.dom.Element slika = doc.createElement("slika");
					
                  Attr velicina = doc.createAttribute("velicina");
                  velicina.setValue(String.valueOf(file.length()/1024));
                  slika.setAttributeNode(velicina);
                  
                  Attr hash = doc.createAttribute("hash");
                  hash.setValue(String.valueOf(file.hashCode()));
                  slika.setAttributeNode(hash);
                  
                  slika.appendChild(doc.createTextNode(file.getName()));
                  
                  rootElement.appendChild(slika);
				}
			}
			
			
	    	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         File xmlFile = new File("./data/slike.xml");
	         
	         StreamResult result = new StreamResult(xmlFile);
	            transformer.transform(source, result);            
//	            
//	            zaZip.add(xmlFile.getAbsolutePath());
//	            zipFiles(zaZip);
    	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	

		

	
}
	
}
	


