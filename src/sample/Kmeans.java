package sample;

import java.util.ArrayList;

public class Kmeans {
	public ArrayList<Cluster> Clusters;
	public String ClassificationKmeans(DataSet Data, int k)
	{   Clusters = new ArrayList<Cluster>();
		
		ArrayList<Integer> Tabou= new ArrayList<Integer>();
//		((Interface) JFrame.getWindows()[0]).textview_1.setText("");
//		((Interface) JFrame.getWindows()[0]).textview_1.append();
		String result = new String();
		result = result + "	********************K-means************ \n" ;
		double distance=0; 
		double distanceRes=0;
		int cpt=1,ind; 
		Cluster clusterChoisi=new Cluster();
		boolean continu=true;
		for(int i=1;i<=k;i++)
		{	Cluster c = new Cluster();
			do{
			ind= (int) (0+ Math.random()*(Data.Contenu.size()));
			}while(Tabou.contains(ind));
			c.Centre=Data.getContenu().get(ind);
			Tabou.add(ind);
			Clusters.add(c);			
		}
		while(continu)
		{	ArrayList<Double> Somm=new ArrayList<Double>();
			for(Cluster c : Clusters)
			{   Somm.add((double) 0);
				c.listeElements= new ArrayList<Integer>();
			}
		for(row r: Data.Contenu)
		{	distanceRes=0;
		    
			for(Cluster c : Clusters)
				{	double somm=0;
					for(Double i: c.Centre.set)
					{
						somm= somm+ Math.pow((i-r.set.get(c.Centre.set.indexOf(i))),2);
						
					}
					distance= Math.sqrt(somm);
					Somm.set(Clusters.indexOf(c),(Somm.get(Clusters.indexOf(c))+distance));
					if(distanceRes==0)
					{
						distanceRes=distance;
						clusterChoisi=c;
						
					}else{
					if(distance<distanceRes)
					{	distanceRes=distance;
						clusterChoisi=c;
					}}
				}
			clusterChoisi.listeElements.add(Data.Contenu.indexOf(r));
		}
		//((Interface) JFrame.getWindows()[0]).textview_1.append();
		result = result + "\nRepartition "+cpt+":\n" ;
		continu= false;
		for(Cluster c: Clusters)		{   
			row comp= new row();
			comp.set.addAll(c.Centre.set);
//			((Interface) JFrame.getWindows()[0]).textview_1.append();
//			((Interface) JFrame.getWindows()[0]).textview_1.append();
			result = result + "Cluster"+(Clusters.indexOf(c)+1)+"===>"+c.Centre.set+"\n" ;
			result = result + " "+c.listeElements+"\n" ;
			for(Double el:c.Centre.set)
			{   Double somm=(double) 0;
				for(int index : c.listeElements)
				{
					somm=somm+Data.Contenu.get(index).set.get(c.Centre.set.indexOf(el));
				}
				c.Centre.set.set(c.Centre.set.indexOf(el), (Double)(somm/c.listeElements.size()));
			}
			if(!comp.set.equals(c.Centre.set))
			{
				continu=true;
			}	
			if(continu==false)
			{
				
			    // ((Interface) JFrame.getWindows()[0]).textview_1.append();
				result = result + "Cluster"+(Clusters.indexOf(c)+1)+"===>"+Somm.get(Clusters.indexOf(c))+"\n" ;
			     double cost1=0;
			     for(Cluster cc : Clusters)
			     {   double somm=0;
			 		for(Double i: cc.Centre.set)
					{
						somm= somm+ Math.pow((c.Centre.set.get(cc.Centre.set.indexOf(i))-i),2);}
			 		    cost1=cost1+ Math.sqrt(somm);
			 		  
			 		    			     }
			     //((Interface) JFrame.getWindows()[0]).textview_1.append();
					result = result + "Cluster"+(Clusters.indexOf(c)+1)+"===>"+cost1+"\n" ;
			     
			}
			
			
		}
		
		cpt++;
		}
		return result;
	}
}
