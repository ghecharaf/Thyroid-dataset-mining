package sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Data {
    public static ArrayList<ArrayList<Float>> RecupDonnees(String NomFichier) {

        ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();

        try {
            InputStream inputStream = Data.class.getResourceAsStream(NomFichier);
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputReader);
            String ligne;
            while ((ligne = br.readLine()) != null) {

                String[] TabL = ligne.split(",");
                ArrayList<Float> list = new ArrayList<Float>();
                for (String number : TabL) {
                    list.add(Float.parseFloat(number));
                }
                data.add(list);

            }// on recherche les lignes ou les attributs sont décri


            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }



        return data;
    }
}
