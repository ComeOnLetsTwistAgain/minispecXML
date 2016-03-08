import java.util.ArrayList;


public class MjEntity {
	
	private String name;
	private String paquet;
	private String extendsName;
	private ArrayList<MjAttribute> listAttribute ;
	private ArrayList<String> imports;
	
	
	
	public MjEntity(String name){
		this.name = name;
		listAttribute = new ArrayList<MjAttribute>();
		imports = new ArrayList<String>();
	}
	
	public String genClassHeader(){
		StringBuilder str = new StringBuilder();
		
		str.append("\npublic class " + this.name);
		if(this.extendsName != "") 
			str.append(" extends " + this.extendsName);
		str.append(" {");
		
		return str.toString();
	}
	
	public String genConstructor(){
		StringBuilder str = new StringBuilder();
		String strParams = "";
		String strParamsName = "";
		
		StringBuilder params = new StringBuilder();
		StringBuilder paramsName = new StringBuilder();
		
		//pour la liste des attribut dans le constructeur
		for(MjAttribute a : listAttribute)
			if(a.getInit().equals(""))
				params.append(a.getMjType().getName() + " " + a.getName() + ", ");
		
		//pour le super()
		for(MjAttribute a : listAttribute)
			paramsName.append(a.getName() + ", ");
		
		//on enleve la virgule de fin
		if(params.length() > 2){
			int start = 0;
			int end = params.length()-2;
			strParams = params.substring(start, end).toString();
		}
		
		//on enleve la virgule de fin
		if(paramsName.length() > 2){
			int start = 0;
			int end = paramsName.length()-2;
			strParamsName = paramsName.substring(start, end).toString();
		}
		
		
				
				
				
				
		
		
		//constructeur sans paramêtres
		//str.append("\n\tpublic " + this.name + "(){}");
		
		
		//constructeur avec paramêtres
		str.append("\n\tpublic " + this.name + "("+ strParams +") { \n");
		if(this.extendsName != ""){
			str.append("\n\t\tsuper( " + strParamsName + "); \n");
		}
			
		
		for(MjAttribute a : listAttribute){
			if(a.getInit().equals("")) //si la valeur init n'est pas défini
				str.append("\t\tthis."+ a.getName() + " = " + a.getName() + ";\n");
		}
		
		str.append("\t} ");
		
		return str.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<MjAttribute> getListAttribute() {
		return listAttribute;
	}

	public void setListAttribute(ArrayList<MjAttribute> listAttribute) {
		this.listAttribute = listAttribute;
	}
	
	public void addAttribute(MjAttribute a){
		this.listAttribute.add(a);
	}

	public String getExtendsName() {
		return extendsName;
	}

	public void setExtendsName(String extendsName) {
		this.extendsName = extendsName;
	}
	
	public String getPaquet() {
		return paquet;
	}

	public void setPaquet(String paquet) {
		this.paquet = paquet;
	}

	public ArrayList<String> getImports() {
		return imports;
	}

	public void setImports(ArrayList<String> imports) {
		this.imports = imports;
	}

	

	
}
