package sample;

import java.util.ArrayList;

public class DataSet {
	
	ArrayList<ArrayList<String>> Attributs= new ArrayList<ArrayList<String>>();
	
	public ArrayList<ArrayList<String>> getAttributs() {
		return Attributs;
	}

	public void setAttributs(ArrayList<ArrayList<String>> attributs) {
		Attributs = attributs;
	}
	


	public ArrayList<row> Contenu= new ArrayList<row>();

	public ArrayList<row> getContenu() {
		return Contenu;
	}

	public void setContenu(ArrayList<row> contenu) {
		Contenu = contenu;
	}
	

}
