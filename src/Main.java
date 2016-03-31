import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import repo.Repository;

/*
 * La classe Main permet d'instancier des classes à partir d'un fichier XML
 * Permet de créer un méta-modèle.
 * 
 * Cette classe initie aussi la visite du modele. Pour générer les classes java.
 */
public class Main {

	public static void main(String[] args) {
		
		try{
    		
			/*
			 * Initialisation de la factory pour la lecture du DOM
			 */
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = factory.newDocumentBuilder();
	    	File xml = new File("model.xml");
	    	Document document = builder.parse(xml);
	    	
	    	/*
	    	 * Initialisation du visiteur
	    	 */
	    	Visiteur v = new Visiteur();
	    	
	    	
	    	Element racine = document.getDocumentElement();
	    	
	    	/*
	    	 * La liste des package au sein du fichier XML
	    	 * La liste des typedef au sein du fichier XML
	    	 */
	    	NodeList packageList 	= racine.getElementsByTagName("package");
	    	NodeList typedefs 		= racine.getElementsByTagName("typedef");
	    	
	    	/*
	    	 * On parcours la liste des package
	    	 */
	    	for(int x=0; x<packageList.getLength(); x++){
	    		
	    		Element elementPackage = (Element) packageList.item(x);
	    		
	    		/*
	    		 * Récuperation de la liste des entity
	    		 */
		    	NodeList nodelist = elementPackage.getElementsByTagName("entity");
		    	
		    	/*
		    	 * Instanciation du MjPackage
		    	 */
		    	MjPackage mjPackage = new MjPackage(elementPackage.getAttribute("name"));
		    	
		    	/*
		    	 * Parcous de la liste des entity
		    	 */
		    	for(int i = 0; i<nodelist.getLength() ;i++){
		    		
		    		Node node = nodelist.item(i);
		    		Element elementEntity = (Element) node;
		    		
		    		/*
		    		 * création MjEntity / ajout de l'entity dans le package
		    		 */
		    		MjEntity mjEntity = new MjEntity(elementEntity.getAttribute("name"));
		    				 mjEntity.setExtendsName(elementEntity.getAttribute("extends"));
		    				 mjEntity.setPaquet(mjPackage.getName());
		    				 Util.addImport(mjEntity.getName(), mjPackage.getName());
		    				     		
		    		/*
		    		 * On ajoute l'entity qu'on vient de créer dans le package		 
		    		 */
		    		mjPackage.addEntity(mjEntity);
		    		
		    		/*
		    		 * Récuperation de la liste des attribute
		    		 */
					NodeList attributes = elementEntity.getElementsByTagName("attribute");
					
					/*
					 * Parcous de la liste des attribute
					 */
					for (int j = 0; j<attributes.getLength(); j++){
						
						Node attribut = attributes.item(j);
						Element elementAttribut = (Element) attribut;
						
						/*
						 * création MjAttributes, MjType / ajout des attributs dans l'entity
						 */
						String name = ""; 
						String[] typetab = new String[3];
						MjType mjType = null;
						
						name = elementAttribut.getAttribute("name");
						
						/*
						 * si typeid est un integer, il s'agit d'un typedef.
						 * sinon on prend directement le type
						 */
						if(Util.IsInteger(elementAttribut.getAttribute("typeid"))){
							typetab = getTypeById(typedefs, elementAttribut.getAttribute("typeid"));
						} else {
							typetab[0] = elementAttribut.getAttribute("typeid");
							typetab[1] = elementAttribut.getAttribute("length");
						}
						
						/*
						 * Création d'un mjType
						 */
						mjType = new MjType(typetab[0], typetab[1]);
						mjType.setListType(typetab[2]);
						
						/*
						 * Création du mjAttribute
						 */
						MjAttribute mjAttribute = new MjAttribute(name, mjType);
						mjAttribute.setMax(elementAttribut.getAttribute("max"));
						mjAttribute.setMin(elementAttribut.getAttribute("min"));
						mjAttribute.setInit(elementAttribut.getAttribute("init"));
						mjAttribute.setPaquet(mjEntity.getPaquet());
						mjAttribute.setIn(elementAttribut.getAttribute("in"));
						
						
						/*
						 * Si il y a des imports à ajouter, on les ajoute dans la liste static
						 */
						if(!getImportForEntity(typedefs, elementAttribut.getAttribute("typeid")).equals(""))
							Util.addImport(mjAttribute.getName(), getImportForEntity(typedefs, elementAttribut.getAttribute("typeid")));
						
						
						/*
						 * Ajout de l'attribut dans l'entity
						 */
						mjEntity.addAttribute(mjAttribute);
					}
					
					System.out.println();
					
				}
		    	
		    	/*
		    	 * Visite du package, création des fichier java.
		    	 */
		    	v.visitPackage(mjPackage);
		    	
		    	
		    	/*
		    	 * sérialisation en XML / lot d'instance.
		    	 */
		    	
		    	// TEMPORAIRE : instanciation d'un repository pour test avant la génération de celui-i
		    	new Repository();
		    	
		    	// Instanciation et déclenchement de la génération du repository
		    	new RepositoryGenerator(new File("repoModel.xml"));
		    	
	    	
	    	}
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * Permet de parcourir, de façon recursive, la liste des typedef, et de retourner un tableau contenant :
	 * 
	 * res[0] = nom du type
	 * res[1] = length
	 * res[2] = type
	 */	
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
	
	/*
	 * Permet de retourner les imports pour une entity donnée
	 */
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
