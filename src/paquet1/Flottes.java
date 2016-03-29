package paquet1;

import java.util.*;


public class Flottes {
	protected ArrayList<Satellite> satellites = new ArrayList<Satellite>();

	public Flottes() { 
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

}
