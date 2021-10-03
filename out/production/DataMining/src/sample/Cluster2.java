package sample;

import java.util.ArrayList;

public class Cluster2 {
    ArrayList<Instance> elements;
    int medoid_nb;
    Instance medoid;

    public Cluster2(int nb, Instance md){
        this.medoid = md;
        this.medoid_nb = nb;
        this.elements = new ArrayList<Instance>();
    }

    public double[] calcul_mean(int l){
        double [] somme =  new double[l];
        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < l; j++) {
                somme[j] = somme[j] + elements.get(i).getAtt(j);
            }
        }
        double [] means = new double[l];
        for (int i = 0; i < means.length; i++) {
            means[i] = somme [i] /elements.size();
        }

        return means;
    }





}
