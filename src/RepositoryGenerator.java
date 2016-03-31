import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * Cette classe prend un parametre un model de repository (xml) et génere le fichier java correspondant.
 * 
 * Le fichier repository generé permet de génerer un xml conforme
 */
public class RepositoryGenerator {
	/*
	 * Le fichier xml
	 * Les factory pour parser le DOM
	 */
	private File xml;
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	Document document;
	Element racine;


	public RepositoryGenerator(File xml){
		try {

			this.xml = xml;
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			document = builder.parse(this.xml);
			racine = document.getDocumentElement();



			this.generateRepo();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Fonction principale de génération du repository
	 * 
	 * Cette fonction appelle toutes les autres.
	 * 
	 * Génere un .xml
	 */
	private void generateRepo(){
		try{
			PrintWriter writer;

			String nameOfFile = "src/repo/RepositoryTemp.java";
			writer = new PrintWriter(nameOfFile, "UTF-8");
			System.out.println("génération de ... " + nameOfFile);

			writer.println(this.genImports());

			writer.println(""
					+ "\npublic class RepositoryTemp {");
			writer.println(this.genMaps());

			writer.println(this.genConstructor());
			writer.println(this.genPopulate());
			writer.println(this.genXML());

			writer.println("}");
			writer.close();

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Génere la fonction qui génere le xml
	 */
	private String genXML(){
		StringBuilder str = new StringBuilder();
		
		str.append(""
				+ "\n\tpublic void generateXML(){");
		str.append("\n\t\ttry{");
		str.append("\n\t\t\tDocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();"
				+ "\n\t\t\tDocumentBuilder dBuilder = dbFactory.newDocumentBuilder();"
				+ "\n\t\t\tDocument doc = dBuilder.newDocument();"
				+ "\n"
				+ "\n\t\t\tElement rootElement = doc.createElement(\"repo\");"
				+ "\n\t\t\tdoc.appendChild(rootElement);"
				+ "\n");
		str.append("\n\t\t} catch (Exception e){");
		str.append("\n\t\t\te.printStackTrace();");
		str.append("\n\t\t}");
		str.append("\n\t}");
		
		return str.toString();
	}

	/*
	 * Génere la fonction qui genere les hashmap ou sont enregistrés les lots d'instances
	 */
	private String genMaps(){
		StringBuilder str = new StringBuilder();

		ArrayList<String> maps = this.getEntities();
		for(String s : maps){
			str.append("\nprivate HashMap<String, "+ s + "> liste_"+ s + " = new HashMap<String, "+ s + ">();" );
		}

		return str.toString();
	}

	/*
	 * Génere le constructeur de la classe
	 */
	private String genConstructor(){
		StringBuilder str = new StringBuilder();
		str.append("\n\tpublic RepositoryTemp(){");
		str.append("\n\t\tthis.populateMaps();");
		str.append("\n\t}");
		return str.toString();
	}

	/*
	 * Génere la fonction de remplissage des hashmaps
	 */
	private String genPopulate(){
		StringBuilder str = new StringBuilder();
		str.append("\n\tpublic void populateMaps(){");
		for(int i = 0 ; i<this.getEntities().size(); i++){
			str.append("\n\t\tfor(int i = 0 ; i <= "+this.getNbEntitiesByName(this.getEntities().get(i))+" ; i++){");
			if(!this.getEntityIsInByEntityType(this.getEntities().get(i), "is").equals(""))
				str.append("\n\t\t\tthis.liste_" + this.getEntities().get(i) + ".put(\""+this.getEntityIsInByEntityType(this.getEntities().get(i), "is")+"\", new "+this.getEntities().get(i)+"());");
			if(!this.getEntityIsInByEntityType(this.getEntities().get(i), "in").equals(""))
				str.append("\n\t\t\tthis.liste_" + this.getEntities().get(i) + ".put(\""+this.getEntityIsInByEntityType(this.getEntities().get(i), "in")+"\", new "+this.getEntities().get(i)+"());");
			str.append("\n\t\t}");
		}

		str.append("\n\t}");
		return str.toString();
	}

	/*
	 * Génere les imports de la classe
	 */
	private String genImports(){
		StringBuilder str = new StringBuilder();
		str.append("package repo;"
				+ ""
				+ "\nimport java.io.*;"
				+ "\nimport java.util.*;"
				+ "\nimport javax.xml.parsers.*;"
				+ "\nimport org.w3c.dom.*;"
				+ "\nimport paquet1.*;"
				+ "\n");
		return str.toString();
	}
	
	/*
	 * Permet de récuperer les entity du DOM
	 */
	private ArrayList<String> getEntities(){
		ArrayList<String> res = new ArrayList<String>();
		try{
			NodeList entityList = racine.getElementsByTagName("entity");

			for(int i = 0; i<entityList.getLength(); i++)
			{
				Element entity = (Element) entityList.item(i);

				if(!res.contains(entity.getAttribute("name"))){
					res.add(entity.getAttribute("name"));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	/*
	 * Permet de recuperer le nombre d'entity qui possedent le nom passé en parametre
	 */
	private int getNbEntitiesByName(String name){
		int res = 0;
		NodeList entityList = racine.getElementsByTagName("entity");
		for(int i = 0; i<entityList.getLength(); i++)
		{
			Element entity = (Element) entityList.item(i);

			if(entity.getAttribute("name").equals(name)){
				res++;
			}
		}
		return res;
	}
	
	/*
	 * Permet de recuperer les attributs is & in en fonction du type passé en parametre
	 */
	private String getEntityIsInByEntityType(String type, String isin){
		String res = "";
		NodeList entityList = racine.getElementsByTagName("entity");

		for(int i = 0; i<entityList.getLength(); i++)
		{
			Element entity = (Element) entityList.item(i);
			if(entity.getAttribute("name").equals(type)){
				res = entity.getAttribute(isin);
			}
		}
		System.out.println(res);
		return res;
	}

}
