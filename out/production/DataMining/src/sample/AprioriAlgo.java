package sample;//import Interface.Apriori;
//import Interface.Interface;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class AprioriAlgo {
	
	
	
	public static String ExtractionItems(DataSet Data,int NC,int support)
	{   int cpt=0, niv=2;
		//((Apriori) JFrame.getWindows()[3]).dispose();
		HashSet<Double> items= new HashSet<Double>();
		HashSet<ArrayList<Double>> itemsTuple= new HashSet<ArrayList<Double>>();
		HashSet<ArrayList<Double>> itemsTuplePris= new HashSet<ArrayList<Double>>();
		HashSet<ArrayList<Double>> itemsTuplePrisSauv= new HashSet<ArrayList<Double>>();
		String reslut = new String();
		//((Interface) JFrame.getWindows()[0]).textview_1.setText("");
		//((Interface) JFrame.getWindows()[0]).textview_1.append("Liste des items C1: \n");
		reslut = reslut +"Liste des items C1: \n" ;
		 String line="";
		
		//********************************************niveau un********************************
				for(row r: Data.Contenu)
				{  for (Double el : r.set )
					{
					items.add(el);	
					}			
				}
				for (Double i:items)
					{  cpt=0;			
						for(row r: Data.Contenu)
						{ 
							if(r.set.contains(i))
							{
							cpt++;
							
							}
						}
						reslut = reslut + i+" : \t"+cpt+"\n";
					//	((Interface) JFrame.getWindows()[0]).textview_1.append();

						if(cpt>support)
						{ 	ArrayList<Double> l =new ArrayList<Double>();
							l.add(i);
							itemsTuplePris.add(l);
							
						}
					}	
				//((Interface) JFrame.getWindows()[0]).textview_1.append();
		       // reslut.concat(" Liste des items pris:  \n");
				reslut = reslut + " Liste des items pris:  \n" ;
				cpt=1;
				for(ArrayList<Double> item:itemsTuplePris) {
//				((Interface) JFrame.getWindows()[0]).textview_1.append();
					//reslut.concat();
					reslut = reslut + "Item "+cpt+":\t"+item.get(0)+"\n";
					cpt++;
				}
				
			//********************************************construction des autres niveaux ********************************		
				
				while((itemsTuplePris.size()!=1)&&(itemsTuplePris.size()!=0))		
				{	
					//((Interface) JFrame.getWindows()[0]).textview_1.append();
					//reslut.concat();
					reslut =reslut + " Liste des items C"+niv+":  \n" ;
					int deb=1;
					Object[] Tab=(Object[]) itemsTuplePris.toArray();
					itemsTuple=new HashSet<ArrayList<Double>>();
					for(ArrayList<Double> l:itemsTuplePris)							{
						 deb++;				
						 for(int i=deb;i<Tab.length;i++)				
								{		
							            HashSet<Double> tri=new HashSet<Double>();
										tri.addAll(l);
										tri.addAll(((ArrayList<Double>) Tab[i]));
										if(tri.size()==niv)
										{
											ArrayList<Double> tempor= new ArrayList<Double>();
											tempor.addAll(tri);
											Collections.sort(tempor);
											itemsTuple.add(tempor);
										}
								}
						
					}
					itemsTuplePrisSauv= new HashSet<ArrayList<Double>>();
					itemsTuplePrisSauv.addAll(itemsTuplePris);	
					itemsTuplePris=new HashSet<ArrayList<Double>>();
					
					for(ArrayList<Double> l:itemsTuple)
					{   
						cpt=0;	
						
						for(row r: Data.Contenu)
						{   line=" (";
							boolean find= true;
							for(Double el: l )
							{   line= line+el+" , ";
								if(!r.set.contains(el))
								{
									find= false;							
								}
							}
							if(find==true)
							{
								cpt++;
							}
						}
						//((Interface) JFrame.getWindows()[0]).textview_1.append();
						//reslut.concat();
						reslut = reslut + line.substring(0,line.length()-2)+") :\t "+cpt+"\n" ;
						if(cpt>support)
						{
							itemsTuplePris.add(l);
							
						}
							
						
					}
					//((Interface) JFrame.getWindows()[0]).textview_1.append();
					//reslut.concat();
					reslut = reslut + " Liste des items C"+niv+" pris: \n " ;
					cpt=1;
					
					for(ArrayList<Double> l:itemsTuplePris)
					{	line=" (";
						for(Double el: l )
						{   line= line+el+" , ";}
						 //	((Interface) JFrame.getWindows()[0]).textview_1.append();
							cpt++;
							//reslut.concat();
							reslut = reslut + "Item "+cpt+":\t"+line.substring(0,line.length()-2)+")\n" ;

							}
					niv++;
				}
				//**************************************Regles d'association***************************************
				itemsTuple= new HashSet<ArrayList<Double>>();
				if(itemsTuplePris.size()==0){itemsTuplePris=itemsTuplePrisSauv;}
				//((Interface) JFrame.getWindows()[0]).textview_1.append();
					//reslut.concat(" ************Les R�gles d'association************ \n ");
					reslut = reslut + " ************Les Régles d'association************ \n " ;
				ArrayList<RegleAsso> RegleAssoc= new ArrayList<RegleAsso>();
				
				int trace=0;
				for(ArrayList<Double> l:itemsTuplePris)	// pour chaque item candidat
				{
					  	for(Double item: l)
								{
								ArrayList<Double> tempor= new ArrayList<Double>();
								tempor.add(item);
								itemsTuple.add(tempor);								
								}
						trace=0;
							for (int j=2;j<=l.size()-1;j++)							 
							{ 	int deb=trace;
								Object[] Tab=(Object[]) itemsTuple.toArray();
								for(int s=trace;s<Tab.length;s++)	
								{	 deb++;												 
									 for(int k=deb;k<Tab.length;k++)				
											{		
										            HashSet<Double> tri=new HashSet<Double>();
													tri.addAll(((ArrayList<Double>)Tab[s]));
													tri.addAll(((ArrayList<Double>) Tab[k]));
											
													if(tri.size()==j)
													{	
														ArrayList<Double> tempor= new ArrayList<Double>();
														tempor.addAll(tri);
														Collections.sort(tempor);
														itemsTuple.add(tempor);
													}
											}									
								}	
								trace=Tab.length;
								
										
					}
							for (ArrayList<Double> part:itemsTuple)
							{
								RegleAsso R = new RegleAsso();
								R.PartieGauche.addAll(part);
								for(Double i:l )
								{
									if(!R.PartieGauche.contains((Double)i))
									{
										R.PartieDroite.add(i);
									}
								}
								RegleAssoc.add(R);
								
							}
							//Niveaux de confiance 
							int supp,supp2=0;
							float nivConf;
							ArrayList<RegleAsso> RegleAssocPrises= new ArrayList<RegleAsso>();
							for(RegleAsso R:RegleAssoc)
							{   boolean find= true;
								supp=0;supp2=0;
								ArrayList<Double> temp= new ArrayList<Double>();
								temp.addAll(R.PartieGauche);
								temp.addAll(R.PartieDroite);
								for(row r: Data.Contenu)
								{   
									find= true;
									for(Double el: temp )
									{  
										if(!r.set.contains(el))
										{
											find= false;							
										}
									}
									if(find==true)
									{supp++;}
								}
								for(row r: Data.Contenu)
								{   
									find= true;
									for(Double el: R.PartieGauche)
									{  
										if(!r.set.contains(el))
										{
											find= false;							
										}
									}
									if(find==true)
									{supp2++;}
								}								
								nivConf=((float)supp/supp2)*100;
								if(nivConf>NC)
								{
									RegleAssocPrises.add(R);
								}
								String PG="";
								String PD="";
								for(Double d: R.PartieGauche)
								{ String P=String.format("%.2f", d);
									PG=PG+P+"  |  ";
								}
								for(Double d: R.PartieDroite)
								{   String P=String.format("%.2f", d);
									PD=PD+P+"  |  ";
								}
								
								//((Interface) JFrame.getWindows()[0]).textview_1.append();
								//reslut.concat(PG.substring(0,PG.length()-1)+"====>"+PD.substring(0,PD.length()-1)+" :\t"+nivConf+" %\n ");

								reslut = reslut + PG.substring(0,PG.length()-1)+"====>"+PD.substring(0,PD.length()-1)+" :\t"+nivConf+" %\n ";
							}
								
							//((Interface) JFrame.getWindows()[0]).textview_1.append(" ************Les R�gles d'association prises************ \n ");
							for(RegleAsso R:RegleAssocPrises)
							{
							String PG="";
							String PD="";
							for(Double d: R.PartieGauche)
							{String P=String.format("%.2f", d);
								PG=PG+P+"  |  ";
							}
							for(Double d: R.PartieDroite)

							{String P=String.format("%.2f", d);
								PD=PD+P+"  |  ";
							}
							
							//((Interface) JFrame.getWindows()[0]).textview_1.append();
								//reslut.concat(PG.substring(0,PG.length()-1)+"====>"+PD.substring(0,PD.length()-1)+" \n ");
								reslut = reslut + PG.substring(0,PG.length()-1)+"====>"+PD.substring(0,PD.length()-1)+" \n ";
						}
									
					
				}
		System.out.println("ok");
		System.out.println(reslut.length());
			return reslut ;
	}

}
