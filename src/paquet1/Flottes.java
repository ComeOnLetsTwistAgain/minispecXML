package paquet1;

import java.util.*;import paquet1.*; 
import paquet1.*; 
import paquet1.*; 



public class Flottes {
	protected ArrayList<Satellite> satellites;
	protected Satellite sat1 = new Satellite();
	protected Satellite sat2 = new Satellite();
	protected Satellite sat3 = new Satellite();

	public Flottes(ArrayList<Satellite> satellites) { 
		this.satellites = satellites;
	} 

	public void add(Satellite o) {
		int max = 10;
		if(this.satellites.size() < max){
			this.satellites.add(o);
		}
	}
	public void remove(Satellite o) {
		int min = 1;
		if(this.satellites.size() >= min){
			this.satellites.remove(o);
		}
	}




	public ArrayList<Satellite> getSatellites() { 
		return this.satellites; 
	}

	public void setSatellites(ArrayList<Satellite> satellites) { 
		this.satellites = satellites; 
	} 


	public Satellite getSat1() { 
		return this.sat1; 
	}

	public void setSat1(Satellite sat1) { 
		this.sat1 = sat1; 
	} 


	public Satellite getSat2() { 
		return this.sat2; 
	}

	public void setSat2(Satellite sat2) { 
		this.sat2 = sat2; 
	} 


	public Satellite getSat3() { 
		return this.sat3; 
	}

	public void setSat3(Satellite sat3) { 
		this.sat3 = sat3; 
	} 

}
