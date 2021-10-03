package sample;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;

public class Clarans {

    public static String CLARANS_Algorithme(int k, int numlocal, int maxneighbor,
                                          ArrayList<Instance> all_instances){

        ArrayList<Instance> bestnode = new ArrayList<Instance>();
        ArrayList<Instance> current_node = new ArrayList<Instance>();
        ArrayList<Cluster2> clusters = new ArrayList<Cluster2>();
        ArrayList<Cluster2> bestclusters = new ArrayList<Cluster2>();
        String result = new String();

        Random rand= new Random();
        int i =1;
        double mincost = 9999999.99;

        double starttime = currentTimeMillis();

        while(i< numlocal){
            clusters.clear();
            for (int md = 0; md < k; md++) {
                int r = rand.nextInt(all_instances.size());
                Instance inst = all_instances.get(r);
                current_node.add(inst);
                clusters.add(new Cluster2(md, inst));
            }

            double cost = 0;
            cost = fill_clusters(all_instances, current_node, k, cost, clusters, true);

            int j=1;
            while(j < maxneighbor) {
                int m = rand.nextInt(k);
                while(clusters.get(m).elements.size()<1)
                    m = rand.nextInt(k);
                int c = rand.nextInt(clusters.get(m).elements.size());
                Instance alt_med = clusters.get(m).elements.get(c);

                ArrayList<Instance> curr_node = current_node;
                ArrayList<Instance> neighbor_node = curr_node;
                ArrayList<Cluster2> clusters_alt = new ArrayList<Cluster2>();
                for (int l = 0; l < k; l++) {
                    if(l != m)
                        clusters_alt.add(new Cluster2(clusters.get(l).medoid_nb, clusters.get(l).medoid));
                    else
                        clusters_alt.add(new Cluster2(l, alt_med));
                }

                neighbor_node.set(m, alt_med);

                double cost2 = 0;
                cost2 = fill_clusters(all_instances, neighbor_node, k, cost2, clusters_alt, true);

                if(cost2< cost) {
                    cost = cost2;
                    current_node = neighbor_node;
                    clusters = clusters_alt;
                    j=1;
                }
                else
                    j++;
            }
            if(cost < mincost) {
                bestnode = current_node;
                bestclusters = clusters;
                mincost = cost;
            }
            i++;
        }

        //temp.setText((System.currentTimeMillis()-starttime) + "ms");
       // result = result + System.currentTimeMillis()-starttime) + "ms";

        for (int j = 0; j < bestclusters.size(); j++) {
          //  textArea.appendText(" ================= Cluster : "+ (j+1) + " =================  \n" );
            result = result + " ================= Cluster : "+ (j+1) + " =================  \n";
            for (int l = 0; l < bestclusters.get(j).elements.size() ; l++) {
              //  textArea.appendText("instance numero : "+all_instances.indexOf(bestclusters.get(j).elements.get(l))+"\n");
                result = result + ""+all_instances.indexOf(bestclusters.get(j).elements.get(l))+",";
            }
            result = result + "\n";
        }
       // cout.setText(""+mincost);
        return result ;
 /*
            for (i = 0; i < k; i++) {
                int n[] = new int [k];
                for (int j = 0; j < clusters.get(i).elements.size() ; j++) {
                    if (clusters.get(i).elements.get(j).getAtt(i) == i+1)
                        n[i] = n[i]+1;

                }


                textArea.appendText(String.valueOf(clusters.get(i).medoid_nb));
                textArea.appendText(n[0]+" "+n[1]+" "+n[2]);
                textArea.appendText(String.valueOf(clusters.get(i).elements.size()));
            }

*/
    }

    public static double fill_clusters(ArrayList<Instance> all_instances, ArrayList<Instance> bestnodes,
                                       int k, double cost, ArrayList<Cluster2> clusters, boolean p){
        for (int i = 0; i < all_instances.size() ; i++) {
            boolean b = false;
            int m;
            for (m = 0; m < k; m++) {
                if(i == all_instances.indexOf(bestnodes.get(m))){
                    b = true; break;
                }
            }
            if(b)
                continue;

            double distance = all_instances.get(i).distanceEuc(bestnodes.get(0));
            int Cnum =0;
            for (m = 1; m < k; m++) {
                double dst = all_instances.get(i).distanceEuc(bestnodes.get(m));
                if(dst<distance){
                    distance = dst; Cnum = m;
                }
            }
            if (p) clusters.get(Cnum).elements.add(all_instances.get(i));
            cost = cost + distance;
        }
        return cost;
    }

    public  ArrayList<Instance> ConvertToClaransData(DataSet Data ){

        ArrayList<Instance> d = new ArrayList<>();
        for(row r  : Data.Contenu){
            Instance i = new Instance(r.set.get(0) , r.set.get(1) , r.set.get(2) , r.set.get(3) , r.set.get(4) , r.set.get(5));
            d.add(i);
        }
        return d ;
    }
}
