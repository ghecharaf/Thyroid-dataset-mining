package sample;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class discritization {

    String path;

    public ArrayList<ArrayList<Float>> readDataset(String path){
        List<String> list = new ArrayList<String>();
        ArrayList<ArrayList<Float>> dataset = new ArrayList<ArrayList<Float>>();
        File file = new File(path);
        if(file.exists()){
            try{
                list = Files.readAllLines(file.toPath(), Charset.defaultCharset());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

         for(String line : list){
             String [] res = line.split(",");
             ArrayList<Float> item = new ArrayList<Float>();
             for(String att : res){
                 item.add(Float.parseFloat(att));
             }
             dataset.add(item) ;
         }

        }
        return dataset ;
    }

    public ArrayList<ArrayList<Float>> getColumn(ArrayList<ArrayList<Float>> dataset , int index ){
        ArrayList<ArrayList<Float>> column = new ArrayList<ArrayList<Float>>();
        float i = 0 ;
        for(ArrayList<Float> item : dataset){
            ArrayList<Float> temp = new ArrayList<Float>();
            temp.add(item.get(index));
            temp.add(i);
            column.add(temp);
            i++;
        }
       return(column);
    }
    public ArrayList<ArrayList<Float>> bining(ArrayList<ArrayList<Float>>  column , int n){
        ArrayList<ArrayList<Float>> bined = new ArrayList<ArrayList<Float>>();
        for(int j = 0; j < column.size() ; j++ ) {
            for(int i = 1 ; i < column.size() - j ; i++){
            ArrayList<Float> x2 = column.get(i) ;
            ArrayList<Float> x1 = column.get(i-1);
            if(x2.get(0) < x1.get(0)){

                column.set(i,x1);
                column.set(i-1,x2);
            }
        }
        }
        int wdith ;
        wdith = (int) (column.size()/n);
        for(int i = 0; i < column.size() ; i = i + wdith ){
            float somme = 0 ;
            int count = 0 ;
            for( int j = i; j < wdith + i && j<= column.size()-1; j++){
                somme = somme + column.get(j).get(0);
                count++;
            }
            float mean = somme/count;
            for( int k = i; k <= wdith + i && k<= column.size()-1; k++){
                ArrayList<Float> temp = column.get(k);
                temp.set(0,mean);
                column.set(k,temp);
            }
        }
        return column ;
    }
    public ArrayList<ArrayList<Float>> smoothing(ArrayList<ArrayList<Float>> Dataset, ArrayList<ArrayList<Float>> Column , int index){
        ArrayList<ArrayList<Float>> smoothed = Dataset;
        int i = 0 ;
        for(ArrayList<Float> item: Dataset){
            ArrayList<Float> temp = Column.get(i);
            item.set(index , temp.get(0));
            smoothed.set(temp.get(1).intValue(), item);
            i++;
        }

        return smoothed;
    }
    public ArrayList<ArrayList<Float>> delDup(ArrayList<ArrayList<Float>> datset){
        ArrayList<ArrayList<Float>> cleaned = new ArrayList<ArrayList<Float>>();
        cleaned.add(datset.get(0));
        for(ArrayList<Float> item : datset){
            if(!cleaned.contains(item)){
                cleaned.add(item);
            }
        }
        return cleaned;
    }

    public DataSet ConvertToAprioriData(ArrayList<ArrayList<Float>> datset){
        DataSet converted = new DataSet();
        for(ArrayList<Float> line : datset ){
            row r = new row();
            for(Float att : line ){
                r.set.add((double)att);
            }
            converted.Contenu.add(r);
        }
        return converted;
    }
}
