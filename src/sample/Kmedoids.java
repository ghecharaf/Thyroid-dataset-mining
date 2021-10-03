package sample;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

//import Interface.Interface;
//import Interface.Classification;
public class Kmedoids {
	public static String ClassificationKmedoids(DataSet Data, int k)
	{   ArrayList<Cluster> Clusters = new ArrayList<Cluster>();
		ArrayList<Integer> Tabou= new ArrayList<Integer>();

//		((Interface) JFrame.getWindows()[0]).textview_1.setText("");
//		((Interface) JFrame.getWindows()[0]).textview_1.append();
		String res = new String();
		res = res + "	********************K-medoids************ \n" ;
		double distance=0;
		double distanceRes=0;
		int cpt=1, ind;
		Cluster clusterChoisi=new Cluster();
		boolean continu=true;
		for(int i=1;i<=k;i++)
		{
			Cluster c = new Cluster();
			do{
				SecureRandom random = new SecureRandom();
				ind= (int) (0+random.nextInt(Data.Contenu.size()));

			}while(Tabou.contains(ind));
			c.Centre=Data.getContenu().get(ind);
			Tabou.add(ind);
			Clusters.add(c);
		}

		while(continu)
		{
			ArrayList<Double> Somm=new ArrayList<Double>();
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
						somm= somm+Math.pow((r.set.get(c.Centre.set.indexOf(i))-i),2);

					}
					distance=Math.sqrt(somm)                                      ;
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
//			((Interface) JFrame.getWindows()[0]).textview_1.append();
			res = res + "\nRépartition "+cpt+":\n";
			continu= false;
			boolean continu2=true;
			for(Cluster c: Clusters)
			{   row nonmedoids;
				row comp= new row();
				comp.set.addAll(c.Centre.set);
//				((Interface) JFrame.getWindows()[0]).textview_1.append();
//				((Interface) JFrame.getWindows()[0]).textview_1.append();
				res = res + "Cluster"+(Clusters.indexOf(c)+1)+"===>"+c.Centre.set+"\n";
				res = res + " "+c.listeElements+"\n" ;
				ArrayList<Integer> temp= new ArrayList<Integer>();
				temp.addAll(c.listeElements);
				Collections.shuffle(temp);
				int in=0;
				continu2=true;
				while((continu2)&&(in<temp.size()))
				{
					nonmedoids=Data.Contenu.get(c.listeElements.get(in));
					double cost1=0;
					double cost2=0;
					for(int index : c.listeElements)
					{
						double somm=0;
						double somm2=0;
						for(Double i: nonmedoids.set)
						{
							somm= somm+Math.pow((Data.Contenu.get(index).set.get(nonmedoids.set.indexOf(i))-i),2);
							somm2= somm2+Math.pow((Data.Contenu.get(index).set.get(nonmedoids.set.indexOf(i)))-c.Centre.set.get(nonmedoids.set.indexOf(i)),2);

						}
						cost1=cost1+Math.sqrt(somm2);
						cost2=cost2+Math.sqrt(somm);
					}
					if(cost1>cost2)
					{	c.Centre=new row();
						c.Centre.set.addAll(nonmedoids.set);
						continu2= false;
						Somm.set(Clusters.indexOf(c),cost2);
					}else{
						in++;
						Somm.set(Clusters.indexOf(c),cost1);
					}
				}
				if(!comp.set.equals(c.Centre.set))
				{
					continu=true;
				}
				if(continu==false)
				{

					//((Interface) JFrame.getWindows()[0]).textview_1.append();
					res = res + "Cluster"+(Clusters.indexOf(c)+1)+"===>"+Somm.get(Clusters.indexOf(c))+"\n";
					double cost1=0;
					for(Cluster cc : Clusters)
					{   double somm=0;
						for(Double i: cc.Centre.set)
						{
							somm= somm+Math.pow((c.Centre.set.get(cc.Centre.set.indexOf(i))-i),2);}
						cost1=cost1+Math.sqrt(somm);

					}
					//((Interface) JFrame.getWindows()[0]).textview_1.append();
					res = res + "Cluster"+(Clusters.indexOf(c)+1)+"===>"+cost1+"\n" ;

				}


			}

			cpt++;
			if(cpt > 100 ){
				res = ClassificationKmedoids(Data, k);
				break;
			}
		}
		return res ;

	}

}
