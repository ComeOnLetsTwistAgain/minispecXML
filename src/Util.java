import java.util.*;

public class Util {
	
	public static ArrayList<String> imports = new ArrayList<String>();
	
	public static String GetFirstLetterUppercase(String str){
		String res = str.substring(0, 1).toUpperCase() + str.substring(1);
		return res;
	}
	
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
	
	public static boolean isTypePrimitif(String s) {
		switch (s) {
		case "String" :
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
