
public class MjAttribute {
	
	private String name;
	private String init;
	private MjType mjType;
	private String paquet;
	
	private String min;
	private String max;
	
	public MjAttribute(String name, MjType mjType /*String listType*/){
		this.name = name;
		this.mjType = mjType;
	}
	
	public String genAttr(){
		StringBuilder str = new StringBuilder();
		//si la taille de l'array est défini
		if(!this.mjType.getLength().isEmpty())
			str.append("\tprivate " + this.getMjType().getName() + " " + this.name + " = new " + this.concatArrayLength(this.getMjType().getName(), this.getMjType().getLength()) +";");
		else if(!this.getInit().isEmpty()){
			switch (this.getMjType().getName()) {
				case "String":
					str.append("\tprivate " + this.getMjType().getName() + " " +this.name + " = \"" + this.init  + "\";");
					break;
				case "int" :
					str.append("\tprivate " + this.getMjType().getName() + " " +this.name+ " = " + this.init + ";");
			}
				
		}
		else
			str.append("\tprivate " + this.getMjType().getName() + " " + this.name + ";");
		return str.toString();
	}
	
	private String concatArrayLength(String arrayType, String length){
		StringBuilder str = new StringBuilder();
		
		str.append(arrayType.substring(0, arrayType.length()-1));
		str.append(length);
		str.append(arrayType.substring(arrayType.length()-1));
		
		return str.toString();
	}
	
	public String genGetSet(){
		StringBuilder str = new StringBuilder();
		
		//getter
		if(!this.mjType.getLength().isEmpty())
			str.append("\n\tpublic " + this.getMjType().getName() + " get" + Util.GetFirstLetterUppercase(this.name) + "() { \n");
		else
			str.append("\n\tpublic " + this.getMjType().getName() + " get" + Util.GetFirstLetterUppercase(this.name) + "() { \n");
		str.append("\t\treturn this."+ this.name +"; \n");
		str.append("\t}\n");
		
		//setter
		if(!this.mjType.getLength().isEmpty())
			str.append("\n\tpublic void set" + Util.GetFirstLetterUppercase(this.name) + "(" + this.getMjType().getName()+" "+ this.name +") { \n");
		else
			str.append("\n\tpublic void set" + Util.GetFirstLetterUppercase(this.name) + "(" + this.getMjType().getName()+" "+ this.name +") { \n");
		str.append("\t\tthis."+ this.name + " = "+ this.name +"; \n");
		str.append("\t} \n");
		
		
		
		return str.toString();
	}
	
	public String genLim(){
		StringBuilder str = new StringBuilder();
		if(!this.getMin().equals("") && !this.getMax().equals("")){
			
			str.append("\n\tpublic void add(" + this.mjType.getListType() + " o) {");
			str.append("\n\t\tint max = " + this.max + ";");
			str.append("\n\t\tif(this."+this.name+".size() < max){");
			str.append("\n\t\t\tthis." + this.name + ".add(o)" + ";");
			str.append("\n\t\t}");
			
			str.append("\n\t}");
			
			str.append("\n\tpublic void remove(" + this.mjType.getListType() + " o) {");
			str.append("\n\t\tint min = " + this.min + ";");
			str.append("\n\t\tif(this."+this.name+".size() >= min){");
			str.append("\n\t\t\tthis." + this.name + ".remove(o)" + ";");
			str.append("\n\t\t}");
			str.append("\n\t}");
			
		}
		return str.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String nom) {
		this.name = nom;
	}

	public MjType getMjType() {
		return mjType;
	}

	public void setMjType(MjType mjType) {
		this.mjType = mjType;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getPaquet() {
		return paquet;
	}

	public void setPaquet(String paquet) {
		this.paquet = paquet;
	}
	
	

	
	
	
	

}
