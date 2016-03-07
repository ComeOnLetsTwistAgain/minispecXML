package paquet1;

import paquet1.*;


public class HeirOfClass1 extends Class2 {
	private Class1 theInt;

	public HeirOfClass1(Class1 theInt) { 

		super( theInt); 
		this.theInt = theInt;
	} 


	public Class1 getTheInt() { 
		return this.theInt; 
	}

	public void setTheInt(Class1 theInt) { 
		this.theInt = theInt; 
	} 

}
