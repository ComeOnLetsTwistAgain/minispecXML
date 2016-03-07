import java.io.File;
import java.lang.reflect.Constructor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Main {

	public static void main(String[] args) {
		
		try{
    		
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = factory.newDocumentBuilder();
	    	
	    	Visiteur v = new Visiteur();
	    	
	    	File xml = new File("model.xml");
	    	Document document = builder.parse(xml);
	    	
	    	Element racine = document.getDocumentElement();
	    	
	    	NodeList packageList 	= racine.getElementsByTagName("package");
	    	NodeList typedefs 		= racine.getElementsByTagName("typedef");
	    	
	    	for(int x=0; x<packageList.getLength(); x++){
	    		
	    		Element elementPackage = (Element) packageList.item(x);
	    		
	    		//liste dans entity dans le package
		    	NodeList nodelist = elementPackage.getElementsByTagName("entity");
		    	
		    	//création MjPackage
		    	MjPackage mjPackage = new MjPackage(elementPackage.getAttribute("name"));
		    	
		    	for(int i = 0; i<nodelist.getLength() ;i++){
		    		
		    		Node node = nodelist.item(i);
		    		Element elementEntity = (Element) node;
		    		
		    		//création MjEntity / ajout de l'entity dans le package
		    		MjEntity mjEntity = new MjEntity(elementEntity.getAttribute("name"));
		    				 mjEntity.setExtendsName(elementEntity.getAttribute("extends"));
		    				 mjEntity.setPaquet(mjPackage.getName());
		    				 Util.addImport(mjEntity.getName(), mjPackage.getName());
		    				 
		    				
		    		
		    		//recherche des attributs de la classe étendue
		    		//TODO
		    		
		    		
		    				 
		    		mjPackage.addEntity(mjEntity);
		    		
					//liste des attribut et des typedef dans l'entity
					NodeList attributes = elementEntity.getElementsByTagName("attribute");
					
					
					for (int j = 0; j<attributes.getLength(); j++){
						
						Node attribut = attributes.item(j);
						Element elementAttribut = (Element) attribut;
						
						//création MjAttributes, MjType / ajout des attributs dans l'entity
						String name = ""; 
						String[] typetab = new String[3];
						MjType mjType = null;
						
						name = elementAttribut.getAttribute("name");
						//si typeid est un integer, il s'agit d'un typedef.
						//sinon on prend directement le type
						if(Util.IsInteger(elementAttribut.getAttribute("typeid"))){
							typetab = getTypeById(typedefs, elementAttribut.getAttribute("typeid"));
						} else {
							typetab[0] = elementAttribut.getAttribute("typeid");
							typetab[1] = elementAttribut.getAttribute("length");
						}
						
						mjType = new MjType(typetab[0], typetab[1]);
						mjType.setListType(typetab[2]);
						
						MjAttribute mjAttribute = new MjAttribute(name, mjType);
						mjAttribute.setMax(elementAttribut.getAttribute("max"));
						mjAttribute.setMin(elementAttribut.getAttribute("min"));
						mjAttribute.setInit(elementAttribut.getAttribute("init"));
						mjAttribute.setPaquet(mjEntity.getPaquet());
						
						
						//on ajoute les imports primitif
						if(!getImportForEntity(typedefs, elementAttribut.getAttribute("typeid")).equals(""))
							Util.addImport(mjAttribute.getName(), getImportForEntity(typedefs, elementAttribut.getAttribute("typeid")));
						
						
						
						mjEntity.addAttribute(mjAttribute);
					}
					
					System.out.println();
					
				}
		    	
		    	//on recherche les attributs des super classes
		    	
		    	// Création du fichier java
		    	v.visitPackage(mjPackage);
		    	
		    	//sérialisation en XML
		    	new xmlGenerator(mjPackage);
	    	
	    	}
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//res[0] = nom du type
	//res[1] = length
	public static String[] getTypeById(NodeList list, String id){
		String[] res = new String[3];
		for(int i = 0; i<list.getLength(); i++){
			Element e = (Element) list.item(i);
			if(e.getAttribute("id").equals(id)){
				// ARRAYLIST d'ARRAYLIST
				if(Util.IsInteger(e.getAttribute("typeid")) && e.getAttribute("name").equals("ArrayList")){
					res[0] = e.getAttribute("name") + "<" + getTypeById(list, e.getAttribute("typeid"))[0] + ">";
					res[1] = "";
					res[2] = e.getAttribute("typeid");
				} 
				// ARRAY
				else if (e.getAttribute("name").equals("array")) {
					res[0] = e.getAttribute("typeid") + "[]";
					res[1] = e.getAttribute("length");
					res[2] = e.getAttribute("typeid");
				// ARRAYLIST SIMPLE
				} else {
					res[0] = e.getAttribute("name") + "<" + e.getAttribute("typeid") + ">";
					res[1] = "";
					res[2] = e.getAttribute("typeid");
				}
			}
		}
		return res;
	}
	
	public static String getImportForEntity(NodeList list, String id){
		String res = "";
		for(int i = 0; i<list.getLength(); i++){
			Element e = (Element) list.item(i);
			if(e.getAttribute("id").equals(id)){
				if(!e.getAttribute("package").equals(""))
					res = e.getAttribute("package");
			}
		}
		return res;
	}

}
