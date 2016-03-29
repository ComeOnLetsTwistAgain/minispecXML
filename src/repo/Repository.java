package repo;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import paquet1.*;




public class Repository {
	//datas
	HashMap<String, Flottes> flottes = new HashMap<String, Flottes>();
	HashMap<String, Satellite> satellites = new HashMap<String, Satellite>();
	
	
	//parse
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	
	File xml = new File("repoModel.xml");
	
	//write
	PrintWriter writer;
	
	String nameOfFile = "src/repo/objects.xml";
	

	public Repository() { 
		try {
			
			//parse
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			
			//write
			writer = new PrintWriter(nameOfFile, "UTF-8");
			
			parseXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	private void parseXML(){
		try{
			Document document = builder.parse(xml);
	    	
	    	Element racine = document.getDocumentElement();
	    	
	    	NodeList entityList = racine.getElementsByTagName("entity");
	    	
	    	
	    	for(int i = 0; i<entityList.getLength(); i++)
	    	{
	    		Element entity = (Element) entityList.item(i);
	    		
	    		int idSatellite = 1;
	    		int idFlottes = 1;
	    		
    			if(entity.getAttribute("name").equals("Flottes"))
    			{
    				
    				this.flottes.put(entity.getAttribute("is"), new Flottes());
    				idFlottes++;
    				
    			}
    			
    			if(entity.getAttribute("name").equals("Satellite"))
    			{
    				
    				this.flottes.get(entity.getAttribute("in")).add(new Satellite());
    				idSatellite++;
    				
    			}
	    	}
	    	
	    	System.out.println(this.satellites.toString());
	    	System.out.println(this.flottes.get("flotte1").getSatellites().toString());
	    	
	    	generateXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
	
	private void generateXML(){
		try{
			int nbSatellites = 1;
		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.newDocument();
			         
			Element rootElement = doc.createElement("repo");
			doc.appendChild(rootElement);
			
			for(Flottes f : this.flottes.values()){
				for(Satellite s : f.getSatellites()){
					Element satellite = doc.createElement("Satellite");
					satellite.setAttribute("id", "#"+nbSatellites);
					rootElement.appendChild(satellite);
					
					nbSatellites ++;
				}
				Element flotte = doc.createElement("Flotte");
				
				rootElement.appendChild(flotte);
				
			}
			
			
			
			// write the content into xml file
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult("repo.xml");
	        transformer.transform(source, result);
		} catch (Exception e){
			System.out.println(e);
		}
	}


}
