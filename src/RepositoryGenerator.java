import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class RepositoryGenerator {
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

	private void generateRepo(){
		try{


			PrintWriter writer;

			String nameOfFile = "src/RepositoryTemp.java";
			writer = new PrintWriter(nameOfFile, "UTF-8");
			System.out.println("génération de ... " + nameOfFile);

			writer.println(this.genImports());

			writer.println("public class RepositoryTemp {");
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
	 * Génere le XML
	 */
	private String genXML(){
		StringBuilder str = new StringBuilder();
		
		str.append("\n\tpublic void generateXML(){");
		
		str.append("\n\t}");
		
		return str.toString();
	}

	/*
	 * Génere les hashmaps
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
		str.append("import java.io.*;"
				+ "\nimport java.util.*;"
				+ "\nimport javax.xml.parsers.*;"
				+ "\nimport org.w3c.dom.*;"
				+ "\nimport paquet1.*;"
				+ "\n");
		return str.toString();
	}

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
