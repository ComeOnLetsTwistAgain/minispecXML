import java.util.*;

public class Util {

	public static HashMap<String, String> imports = new HashMap<String, String>();

	/*
	 * methode testant si la clé passé en paramètre existe dans la hashmap d'import
	 */
	private static boolean exists(String s){
		if(imports.containsKey(s))
			return true;
		return false;
	}

	/*
	 * alimente la hashmap d'import avec 
	 */
	public static void addImport(String i, String e){
		if(!exists(i)){
			imports.put(i, e);
		}
	}

	/*
	 * String qui retourne l'ensemble des imports necessaires pour les attributs d'une entity
	 */
	public static String printImports(MjEntity e){
		ArrayList<MjAttribute> listAttr = e.getListAttribute();
		StringBuilder str = new StringBuilder();

		/*
		 * Pour tous les attributs, on cherche si la map des imports contient la clÃ© correspondante.
		 * 
		 * Du nom du type de l'attribut, pour les classes internes
		 * Du nom de l'attribut, pour les type style ArrayList
		 */
		for(MjAttribute a : listAttr){
			if(imports.containsKey(a.getMjType().getName())){ 
				str.append("import" + " " + imports.get(a.getMjType().getName()) +  ".*; \n");
			} else if(imports.containsKey(a.getName())){
				str.append("import" + " " + imports.get(a.getName()) +  ".*;");
			}
		}

		return str.toString();
	}

	/*
	 * fonction retournant la chaine de caractère passé en paramètre avec la première lette en majuscule
	 */
	public static String GetFirstLetterUppercase(String str){
		String res = str.substring(0, 1).toUpperCase() + str.substring(1);
		return res;
	}

	/*
	 * test si la chaine de caractère, une fois parsée, est un integer ou non 
	 */
	public static boolean IsInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}

	/*
	 * test si la chaine de caractère passée en paramètre est un type primititf
	 */
	public static boolean isTypePrimitif(String s) {
		switch (s) {
		case "String" :
			return true;
		case "String[]" :
			return true;
		case "int" :
			return true;
		case "Integer" :
			return true;
		case "float" :
			return true;
		case "Double" :
			return true;
		default :
			return false;
		}
	}

}
