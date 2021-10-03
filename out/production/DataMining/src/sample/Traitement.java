package sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;

public class Traitement {
	
	//Lecture du fichier et extraction des attribut et caracteristique
	
	public static ArrayList<ArrayList<String>> Extraction(String NomFichier){
		
		ArrayList<ArrayList<String>> Attributs= new ArrayList<ArrayList<String>>();
		
		try{
			InputStream ips=new FileInputStream(NomFichier);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			int i;
			while (((ligne=br.readLine())!=null)&&(!ligne.startsWith("@attribute"))){}// on recherche les lignes ou les attributs sont d�cris
			if(ligne != null)
			{
				while((ligne.startsWith("@attribute"))&&(ligne!=null))
				{
					String[] TabL=ligne.split(" ");
					ArrayList<String> List= new ArrayList<String>();
					List.add(TabL[1]);
					List.add(TabL[2]);
					Attributs.add(List);
					ligne=br.readLine();
					
				}
			}
			 br.close(); 
		}		
	catch (Exception e){
		System.out.println(e.toString());
	}
			
		return Attributs;
	}
	
	
	public static DataSet RecupDonnees(String NomFichier){
		
		DataSet Data= new DataSet();
		try{
			InputStream ips= Traitement.class.getResourceAsStream(NomFichier);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne=br.readLine();
			int i;
			if(ligne != null)
			{
				while(ligne!=null)
				{  
					String[] TabL=ligne.split(",");
					row rowTemp= new row();
					for(String s:TabL)
					{   if(s.equals("absent"))
						{rowTemp.set.add((Double) 1.0);}
						else 
							{if(s.equals("present"))
							{rowTemp.set.add((Double) 2.0);}
							else{
							rowTemp.set.add(Double.parseDouble((s.trim())));}}
					}
					Data.Contenu.add(rowTemp);
					ligne=br.readLine();
					
				}
			}
			 br.close(); 
		}		
	catch (Exception e){
		System.out.println(e.toString());
	}
			
		
		
		return Data;
	}
	
	public static double CalculMoyenne(DataSet Data,int Att){
		double S=0;
		for(row r: Data.Contenu )
		{S=S+r.getSet().get(Att);}
			
		return S/Data.Contenu.size();}
		
	public static double CalculMediane(DataSet Data,int Att){
		int s;
		Vector<Double> Temp= new Vector<Double>();
		for(row r: Data.Contenu )
		{Temp.add(r.getSet().get(Att));}
		
		Collections.sort(Temp);
		
		if(Data.Contenu.size()%2 !=0)
		{
			s= (Data.Contenu.size()/2)+1;
			return Temp.get(s);
		}else
		{
			s=Data.Contenu.size()/2;
			return (Temp.get(s)+Temp.get(s))/2;
			
		}
		
		
			
	}
	   
	public static int DiscretOuContinu(DataSet Data,int Attribut)		
	{
		ArrayList<Double> Effectifs= new ArrayList<Double>();
		for( row r : Data.Contenu)
			if(!Effectifs.contains(r.set.get(Attribut)))
				Effectifs.add(r.set.get(Attribut));

		if((double) (Effectifs.size()/Data.Contenu.size())>=0.5)
		{	   return 1;} //Continu
		else
		{	   return 0;}//Discret	
	}

	public static double CalculMod(DataSet Data,int Att ){
		double mode = 0;
		if(DiscretOuContinu(Data,Att)==0){
			double key;
			int cpt=0;
			HashMap<Double, Integer> Effectifs= new HashMap<Double, Integer>();
			for( row r : Data.Contenu)
			{ 	key=r.set.get(Att);
				if(!Effectifs.containsKey(key))
					{Effectifs.put(key,1);}
				else
					{Effectifs.put(key, Effectifs.get(key)+1);} 
					
			}
			
			mode=(double) Effectifs.keySet().toArray()[0];
			for(Double El: Effectifs.keySet())
			{
				if(Effectifs.get(El)>Effectifs.get(mode))
					mode=El;	
				
			}
		}else{			
						
			ArrayList<Double> Vals= new ArrayList<Double>();
			for(row r : Data.Contenu)				
					Vals.add(r.set.get(Att));			
			
            Collections.sort(Vals);
            double pas= (Vals.get(Vals.size()-1)-Vals.get(0))/5;
            double DebInt=Vals.get(0);
            int cpt2=0;
            for(int i=1;i<=5;i++)
            {  int cpt=0;
            	for(double val: Vals)
            		{
            		  if ((val>=DebInt)&&(val<(DebInt+pas)))
            			  cpt++;            	
            		}
            	if(cpt>cpt2){cpt2=cpt; mode=DebInt+(pas/2);}
            	DebInt=DebInt+pas;
            }
			
			
		}
		
		return mode;}

	public static float ecartQuadratique(DataSet Data,int att, float m) {
		float v;
	    int i;
	    v = (float) 0.0;
	    for ( i = 0 ; i < Data.Contenu.size() ; i = i+1 ) {
	      v = (float) (v + (Data.Contenu.get(i).set.get(att)-m)*(Data.Contenu.get(i).set.get(att)-m)); 
	    }
	    v = (float) Math.sqrt(v);
	    return v;
	}

	public static float coefficientCorrelation(DataSet Data,int att1, int att2) 
	{
		float cc;
	    int i;
	    float mx;
	    float my;
	    float eqmx;
	    float eqmy;
	    mx =(float) CalculMoyenne(Data,att1);
	    my =(float) CalculMoyenne(Data,att2);
	    cc = (float) 0.0;
	    for ( i = 0 ; i < Data.Contenu.size(); i = i+1 ) 
	    {
	      cc = (float) (cc + (Data.Contenu.get(i).set.get(att1)-mx)*(Data.Contenu.get(i).set.get(att2)-my)); 
	    }
	    eqmx = ecartQuadratique(Data,att1,mx);
	    eqmy = ecartQuadratique(Data,att2,my);
	    cc = cc / (eqmx*eqmy);
	    return cc;
	}



	
		
		
		
	
	

}
