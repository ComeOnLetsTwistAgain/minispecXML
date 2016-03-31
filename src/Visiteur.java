import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * Cette classe prend en parametre un mjPAckage (dont la structure a été instanciée par la classe Main)
 * Génere les classe java correspondantes.
 */
public class Visiteur {

	public void visitPackage(MjPackage p){
		
		try {
			ArrayList<MjEntity> entities;
			ArrayList<MjAttribute> attributes;
			
			/*
			 * Récuperation de la liste des enitiy
			 */
			entities = p.getListEntity();
			
			/*
			 * Parcours de la liste des entity
			 */
			for(MjEntity e : entities) {
				/*
				 * Création
				 * 	du writer
				 * 	du fichier
				 * 	du dossier
				 */
				PrintWriter writer;
				
				File folder = new File("src/" + p.getName());
				folder.mkdir();
				
				String nameOfFile = "src/"+ p.getName() + "/" + e.getName() + ".java";
				writer = new PrintWriter(nameOfFile, "UTF-8");
				System.out.println("génération de ... " + nameOfFile);
				
				/*
				 *  ##### ECRITURE
				 */
				
				// le package
				writer.println("package " + p.getName() + ";");
				writer.println();
				
				// les imports
				writer.println(Util.printImports(e));
				writer.println();
				
				// le nom de la classe
				writer.println(e.genClassHeader());
				
				/*
				 * Récupération des attributs
				 */
				attributes = e.getListAttribute();
				
				/*
				 * Parcours de la liste des attributs
				 * Ecriture des attributs
				 */
				for(MjAttribute a : attributes)
						writer.println(a.genAttr()); 
				
				// le constructeur
				writer.println(e.genConstructor());
				
				/*
				 * Parcours de la liste des attributs
				 * Ecriture des Fonction add et remove s'il sagit d'une liste bornée
				 */
				for(MjAttribute a : attributes)
					writer.println(a.genLim());
				
				/*
				 * Parcours de la liste des attributs
				 * Ecriture des getter & setter
				 */
				for(MjAttribute a : attributes)
					writer.println(a.genGetSet());
				
				
				writer.println("}");
				writer.close();
				
				/*
				 *  ##### END ECRITURE
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
