
public class MjType {
	private String name;
	private String length; //taille de l'array (optionnel)
	private String listType;
	
	public MjType(){
		
	}
	
	public MjType(String name){
		this.name = name;
	}
	
	public MjType(String name, String length){
		this.name = name;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}
	
	
	
	

}
