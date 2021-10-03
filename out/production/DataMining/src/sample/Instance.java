package sample;

public class Instance {

    private double [] attributs;
    public int size;

    public Instance (double att1, double att2, double att3, double att4, double att5, double att6){
        attributs = new double[6];
        attributs[0] = att1;
        attributs[1] = att2;
        attributs[2] = att3;
        attributs[3] = att4;
        attributs[4] = att5;
        attributs[5] = att6;

        size = 6;
    }

    public Instance (double [] atts){
        attributs = atts;
        size = 6;
    }

    public double getAtt(int n){
        return attributs[n];
    }
    public void setAtt(int n, double m){
        attributs[n] = m;
    }

    public double distanceEuc(Instance ist){
        double dst = 0;
        for(int i=1; i<ist.size ;i++){
            double diff = this.getAtt(i) - ist.getAtt(i);
            double d = Math.pow(diff, 2);
            dst = dst + d;
        }
           return Math.sqrt(dst);
    }

    public boolean eq(double [] att){
        boolean b = true;
        for (int i = 0; i < size; i++) {
            if(attributs[i] != att[i]){
                b = false; break;
            }
        }
        if(b) return true;
        else return false;
    }



}
