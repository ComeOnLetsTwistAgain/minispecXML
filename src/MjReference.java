
public class MjReference extends MjType{
	
	private MjEntity mjEntity;
	
	public MjReference(MjEntity mjEntity){
		super();
		this.mjEntity = mjEntity;
	}

	public MjEntity getMjEntity() {
		return mjEntity;
	}

	public void setMjEntity(MjEntity mjEntity) {
		this.mjEntity = mjEntity;
	}
	
	

}
