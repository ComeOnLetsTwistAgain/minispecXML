import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import paquet1.*;

public class RepositoryTemp {

private HashMap<String, Flottes> liste_Flottes = new HashMap<String, Flottes>();
private HashMap<String, Satellite> liste_Satellite = new HashMap<String, Satellite>();

	public RepositoryTemp(){
		this.populateMaps();
	}

	public void populateMaps(){
		for(int i = 0 ; i <= 1 ; i++){
			this.liste_Flottes.put("flotte1", new Flottes());
		}
		for(int i = 0 ; i <= 3 ; i++){
			this.liste_Satellite.put("flotte1", new Satellite());
		}
	}

	public void generateXML(){
	}
}
