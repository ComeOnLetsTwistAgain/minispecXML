import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Visiteur {

	public void visitPackage(MjPackage p){
		
		try {
			
			ArrayList<MjEntity> entities;
			ArrayList<MjAttribute> attributes;
			
			entities = p.getListEntity();
			
			for(MjEntity e : entities) {
				PrintWriter writer;
				
				File folder = new File("src/" + p.getName());
				folder.mkdir();
				
				String nameOfFile = "src/"+ p.getName() + "/" + e.getName() + ".java";
				writer = new PrintWriter(nameOfFile, "UTF-8");
				System.out.println("génération de ... " + nameOfFile);
				
				// ##### ECRITURE
				
				
				
				//package
				
				writer.println("package " + p.getName() + ";");
				writer.println();
				writer.println(e.printImports());
				writer.println();
				
				writer.println(e.genClassHeader());
				
				attributes = e.getListAttribute();
				
				for(MjAttribute a : attributes)
						writer.println(a.genAttr());
				
				writer.println(e.genConstructor());
				
				for(MjAttribute a : attributes)
					writer.println(a.genLim());
				
				for(MjAttribute a : attributes)
					writer.println(a.genGetSet());
				
				
				
				
				writer.println("}");
				writer.close();
				
				// ##### END ECRITURE
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
