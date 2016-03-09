package repo;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
	    		NodeList attributeList = entity.getElementsByTagName("attribute");
	    		
	    		int idSatellite = 1;
	    		int idFlottes = 1;
	    		for(int j = 0; j<attributeList.getLength();j++)
	    		{
	    			Element attribute = (Element) attributeList.item(j);
	    			if(attribute.getAttribute("typeid").equals("Flottes"))
	    			{
	    				idFlottes++;
	    			}
	    			
	    			if(attribute.getAttribute("typeid").equals("Satellite"))
	    			{
	    				
	    				this.satellites.put("#"+idSatellite, new Satellite());
	    				idSatellite++;
	    				
	    			}
	    			
	    			//si la map des satellites a une taille positive, on instancie une flottes avec ces satellites
	        		if(this.satellites.size() > 0){
	        			
	        			System.out.println("new flotte");
	        			ArrayList<Satellite> satList = new ArrayList<Satellite>();
	        			
	        			Flottes flotte = new Flottes(satList);
	        			
	        			
	        		}
	    			
	    			
	    		}
	    	}
	    	
	    	
	    	
	    	System.out.println(this.satellites.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}


}
