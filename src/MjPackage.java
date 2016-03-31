import java.util.ArrayList;


public class MjPackage {

	private String name;
	private ArrayList<MjEntity> listEntity ;

	public MjPackage(String name){
		this.name = name;
		this.listEntity = new ArrayList<MjEntity> ();
	}	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<MjEntity> getListEntity() {
		return listEntity;
	}
	public void setListEntity(ArrayList<MjEntity> listEntity) {
		this.listEntity = listEntity;
	}
	public void addEntity(MjEntity e){
		this.listEntity.add(e);
	}

	/*
	 * Methode accept du visiteur pour la visite d'un MjPackage
	 */
	public void accept(Visiteur v){
		v.visitPackage(this);
	}





}
