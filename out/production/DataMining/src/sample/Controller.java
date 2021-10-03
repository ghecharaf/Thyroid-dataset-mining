package sample;

import animatefx.animation.*;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jfree.chart.fx.ChartViewer;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;


public class Controller implements Initializable {
    Traitement t;
    DataSet d;
    discritization dsc;

    ObservableList Dataset= FXCollections.observableArrayList();
    @FXML
    TableView<Thyroid> Table;
    @FXML
    TableColumn<Thyroid,String> Class_attribute;
    @FXML TableColumn<Thyroid,String> T3_resin;
    @FXML TableColumn<Thyroid,String> thyroxin;

    @FXML TableColumn<Thyroid,String> triodothyronine;
    @FXML TableColumn<Thyroid,String> TSH;
    @FXML
    JFXTextField T3 ,T31,T311,thy,thy1,thy11,trio,trio1,trio11,Tsh,Tsh1,Tsh11,Interval,NC,NivSUP,NCluster,Maxneighbor,NumLocal;
    @FXML
    JFXComboBox<Integer> SizeBines;
    @FXML
    AnchorPane statsPane;
    @FXML
    TextArea Output;
    @FXML
    Text TempEXECUTION;
    @FXML
    JFXButton T3Button,THYButton,TSHButton,TrioButton,APRIORI,MEDIODS,MEANS,CLARANS;
    @FXML
    Pane APANE,APANE1,APANE11,APANE2,APANE3,Descritization,APANE21;
    JFXSnackbar snackbar;
    JFXSnackbar snackbar2;
    JFXSnackbar snackbar3;
    JFXSnackbar snackbar4;
    ChartViewer ch,chHISTO,chDis;
    ArrayList<ArrayList<Float>> dataset;
    public JFXTextArea jfxTextArea;

    ArrayList<Integer> check = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Class_attribute.setCellValueFactory(new PropertyValueFactory<>("Class_attribute"));
        T3_resin.setCellValueFactory(new PropertyValueFactory<Thyroid,String>("T3_resin"));
        thyroxin.setCellValueFactory(new PropertyValueFactory<Thyroid,String>("thyroxin"));
        triodothyronine.setCellValueFactory(new PropertyValueFactory<Thyroid,String>("triodothyronine"));
        TSH.setCellValueFactory(new PropertyValueFactory<Thyroid,String>("TSH"));
        T3.setEditable(false);
        thy.setEditable(false);
        trio.setEditable(false);
        Tsh.setEditable(false);
        T31.setEditable(false);
        thy1.setEditable(false);
        trio1.setEditable(false);
        Tsh1.setEditable(false);
        T311.setEditable(false);
        thy11.setEditable(false);
        Descritization.setVisible(false);
        trio11.setEditable(false);
        Tsh11.setEditable(false);

        statsPane.setVisible(false);
        snackbar=new JFXSnackbar(APANE);
        snackbar2=new JFXSnackbar(APANE1);

        snackbar4=new JFXSnackbar(APANE3);
        APANE1.setLayoutX(APANE.getLayoutX());
        APANE1.setLayoutY(APANE.getLayoutY());
        APANE3.setLayoutX(APANE.getLayoutX());
        APANE3.setLayoutY(APANE.getLayoutY());
        APANE1.setVisible(false);
        ch=new ChartViewer();
        chHISTO=new ChartViewer();
        chDis=new ChartViewer();
        APANE11.getChildren().add(chHISTO);
        APANE1.getChildren().add(ch);
        APANE2.getChildren().add(chDis);
        APANE2.setVisible(false);
        APANE3.setVisible(false);
        Output.setText("");
        APANE21.setVisible(false);

        ch.setPrefSize(600,500);
        chHISTO.setPrefSize(600,500);
        chDis.setPrefSize(600,500);

        ch.setLayoutX(50);
        chHISTO.setLayoutX(50);
        chDis.setLayoutX(50);
        SizeBines.getItems().add(2);
        SizeBines.getItems().add(4);SizeBines.getItems().add(6);SizeBines.getItems().add(8);


        afficher();

    }
    public static <T> T mostCommon(List<T> list) {
        Map<T, Float> map = new HashMap<>();

        for (T t : list) {
            Float val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Float> max = null;

        for (Map.Entry<T, Float> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

    public void sortir(ActionEvent actionEvent) throws SQLException {
        Main.stage.close();

    }

    public void afficher(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        statsPane.setVisible(false);
        APANE1.setVisible(false);
        APANE.setVisible(true);
        APANE11.setVisible(false);
        APANE2.setVisible(false);
        APANE3.setVisible(false);

        ArrayList<ArrayList<Float>> list =    Data.RecupDonnees("/Thyroid_Dataset.txt");

        for(ArrayList<Float> line : list)
        {
            Thyroid i = new Thyroid(line);
            Dataset.add(i);
        }
        Table.setItems(Dataset);
        new FadeInRight(APANE).play();
    }public void afficher( ) {
        APANE21.setVisible(false);
        statsPane.setVisible(false);
        APANE1.setVisible(false);
        APANE.setVisible(true);
        APANE2.setVisible(false);
        APANE11.setVisible(false);
        APANE3.setVisible(false);

        ArrayList<ArrayList<Float>> list =    Data.RecupDonnees("/Thyroid_Dataset.txt");

        for(ArrayList<Float> line : list)
        {
            Thyroid i = new Thyroid(line);
            Dataset.add(i);
        }
        Table.setItems(Dataset);
        new FadeInRight(APANE).play();
    }

    public void AfficherStat(ActionEvent actionEvent) {
        if (!Table.getItems().isEmpty()) {
            float TEMPt3 = 0, TEMPthy = 0, TEMPtrio = 0, TEMPTSh = 0;
            List<Float> T3List=new ArrayList<>(),thyList=new ArrayList<>(),trioList=new ArrayList<>(),TshList=new ArrayList<>();

            for (Thyroid i : Table.getItems()) {
                TEMPt3 += Float.valueOf(i.getT3_resin());
                T3List.add(Float.valueOf(i.getT3_resin()));


                TEMPthy += Float.valueOf(i.getThyroxin());
                thyList.add(Float.valueOf(i.getThyroxin()));

                TEMPtrio += Float.valueOf(i.getTriodothyronine());
                trioList.add(Float.valueOf(i.getTriodothyronine()));
                TEMPTSh += Float.valueOf(i.getTSH());
                TshList.add( Float.valueOf(i.getTSH()));

            }
            System.out.println(Dataset.size());
            T3.setText(TEMPt3 / Dataset.size() + "");
            thy.setText(TEMPthy / Dataset.size() + "");
            trio.setText(TEMPtrio / Dataset.size() + "");
            Tsh.setText(TEMPTSh / Dataset.size() + "");
            T31.setText(String.valueOf(Table.getItems().get(108).getT3_resin()));
            thy1.setText(String.valueOf(Table.getItems().get(108).getThyroxin()));
            trio1.setText(String.valueOf(Table.getItems().get(108).getTriodothyronine()));
            Tsh1.setText(String.valueOf(Table.getItems().get(108).getTSH()));
            T311.setText(mostCommon(T3List)+"");
            thy11.setText(mostCommon(thyList)+"");
            trio11.setText(mostCommon(trioList)+"");
            Tsh11.setText(mostCommon(TshList)+"");


            statsPane.setVisible(true);
            new FadeInLeft(statsPane).play();

        }
        else snackbar.show("Veuillez charger les donnée",3000);
    }

    public void boxPlot(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        APANE1.setVisible(true);
        APANE.setVisible(false);
        APANE2.setVisible(false);
        APANE11.setVisible(false);
        APANE3.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(0);
        ch.setChart(b.BoxAndWhiskerDemo());
        new FadeInRight(APANE1).play();
    }

    public void boxPlotT3(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        APANE1.setVisible(true);
        APANE.setVisible(false);
        APANE2.setVisible(false);
        APANE11.setVisible(false);
        APANE3.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(1);
        ch.setChart(b.BoxAndWhiskerDemo());
        new FadeInRight(ch).play();
    }

    public void boxPlotTSH(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        APANE1.setVisible(true);
        APANE.setVisible(false);
        APANE2.setVisible(false);
        APANE11.setVisible(false);
        APANE3.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(4);
        ch.setChart(b.BoxAndWhiskerDemo());
        new FadeInRight(ch).play();
    }

    public void boxPlotTrio(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        APANE1.setVisible(true);
        APANE.setVisible(false);
        APANE3.setVisible(false);
        APANE2.setVisible(false);
        APANE11.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(3);
        ch.setChart(b.BoxAndWhiskerDemo());
        new Jello(ch).play();
        new JackInTheBox(ch).play();
        new Shake(ch).play();
        new RotateIn(ch).play();
        new SlideInDown(ch).play();
        new Swing(ch).play();
        new Flash(ch).play();
        new LightSpeedIn(ch).play();
    }

    public void boxPlotthyroxine(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        APANE1.setVisible(true);
        APANE.setVisible(false);
        APANE2.setVisible(false);
        APANE3.setVisible(false);
        APANE11.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(2);
        ch.setChart(b.BoxAndWhiskerDemo());
        new FadeInRight(ch).play();
    }

    public void histrogramT3(ActionEvent actionEvent){
        APANE21.setVisible(false);
        APANE11.setVisible(true);
        APANE.setVisible(false);
        APANE1.setVisible(false);
        APANE2.setVisible(false);
        APANE3.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(0);
        chHISTO.setChart(b.histogram(1));
        new FadeInRight(APANE11).play();
    }
    public void histrogramThy(ActionEvent actionEvent){
        APANE21.setVisible(false);
        APANE11.setVisible(true);
        APANE.setVisible(false);
        APANE1.setVisible(false);
        APANE3.setVisible(false);
        APANE2.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(0);
        chHISTO.setChart(b.histogram(2));
        new FadeInRight(APANE11).play();
    }
    public void histrogramTSH(ActionEvent actionEvent){
        APANE21.setVisible(false);
        APANE11.setVisible(true);
        APANE.setVisible(false);
        APANE1.setVisible(false);
        APANE3.setVisible(false);
        APANE2.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(0);
        chHISTO.setChart(b.histogram(3));
        new FadeInRight(APANE11).play();
    }
    public void histrogramTRIO(ActionEvent actionEvent){
        APANE21.setVisible(false);
        APANE11.setVisible(true);
        APANE.setVisible(false);
        APANE1.setVisible(false);
        APANE3.setVisible(false);
        APANE2.setVisible(false);
        Boxplot b=new Boxplot();
        b.createSampleDataset(4);
        chHISTO.setChart(b.histogram(4));
        new FadeInRight(APANE11).play();
    }


    public void dispertionPanel(){
        APANE21.setVisible(false);
        APANE2.setVisible(true);
        APANE.setVisible(false);
        APANE1.setVisible(false);
        APANE3.setVisible(false);
        APANE11.setVisible(false);
    }


    int x=0,y=0;
    public void buttonColor(){
        switch (x){
            case 1: T3Button.setStyle("-fx-background-color: #33d674");break;
            case 2: THYButton.setStyle("-fx-background-color: #33d674");break;
            case 3: TSHButton.setStyle("-fx-background-color: #33d674");break;
            case 4: TrioButton.setStyle("-fx-background-color: #33d674");break;
        }
        switch (y){
            case 1: T3Button.setStyle("-fx-background-color: #3369D6");break;
            case 2: THYButton.setStyle("-fx-background-color: #3369D6");break;
            case 3: TSHButton.setStyle("-fx-background-color: #3369D6");break;
            case 4: TrioButton.setStyle("-fx-background-color: #3369D6");break;
        }
    }

    public void DisT3(ActionEvent actionEvent) {
        if(check.size() != 2 && !check.contains(1)) {
            if (check.size() == 1){
                y = 1;
            }
            else{
                x = 1;
            }
            buttonColor();
            check.add(1);
            if(check.size() == 2){
                showXYplot();
            }
        }
        else {
            T3Button.setStyle("-fx-background-color: red");
            if (check.contains(1)) check.remove(check.indexOf(1));
            if (x == 1){
                x = y;
            }
            y = 0;
            buttonColor();
        }
    }

    public void Disthy(ActionEvent actionEvent) {
        if(check.size() != 2 && !check.contains(2)) {
            if (check.size() == 1){
                y = 2;
            }
            else{
                x = 2;
            }
            buttonColor();
            check.add(2);
            if(check.size() == 2){
                showXYplot();
            }
        }
        else {
            THYButton.setStyle("-fx-background-color: red");
            if (check.contains(2)) check.remove(check.indexOf(2));
            if (x == 2){
                x = y;
            }
            y = 0;
            buttonColor();
        }
    }

    public void Distrio(ActionEvent actionEvent) {
        if(check.size() != 2 && !check.contains(4)) {
            if (check.size() == 1){
                y = 4;
            }
            else{
                x = 4;
            }
            buttonColor();
            check.add(4);
            if(check.size() == 2){
                showXYplot();
            }
        }
        else {
            TrioButton.setStyle("-fx-background-color: red");
            if (check.contains(4)) check.remove(check.indexOf(4));
            if (x == 4){
                x = y;
            }
            y = 0;
            buttonColor();
        }
    }

    public void DisTSH(ActionEvent actionEvent) {
        if(check.size() != 2 && !check.contains(3)) {
            if (check.size() == 1){
                y = 3;
            }
            else{
                x = 3;
            }
            buttonColor();
            check.add(3);
            if(check.size() == 2){
                showXYplot();
            }
        }
        else {
            TSHButton.setStyle("-fx-background-color: red");
            if (check.contains(3)) check.remove(check.indexOf(3));
            if (x == 3){
                x = y;
            }
            y = 0;
            buttonColor();
        }
    }

    public void showXYplot(){
        Boxplot b=new Boxplot();
        b.createSampleDataset(0);
        chDis.setChart(b.XYGraphDataSet(check.get(0),check.get(1)));
    }

    public void GoToTraitement(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        statsPane.setVisible(false);
        APANE1.setVisible(false);
        APANE.setVisible(false);
        APANE2.setVisible(false);
        Output.setText("");

        APANE11.setVisible(false);
        APANE3.setVisible(true);
        new FadeInDownBig(MEANS).setDelay(Duration.millis(300)).play();
        new FadeInDownBig(MEDIODS).setDelay(Duration.millis(500)).play();
        new FadeInDownBig(APRIORI).setDelay(Duration.millis(700)).play();
        new FadeInDownBig(CLARANS).setDelay(Duration.millis(1000)).play();

        new FadeInRight(APANE3).play();




        // descritisation





    }

    public void APRIORI(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        NCluster.setVisible(false);
        NumLocal.setVisible(false);
        Maxneighbor.setVisible(false);
        t = new Traitement() ;
        d = new DataSet();
        dsc = new discritization();
        dsc.path = "/Thyroid_Dataset.txt";
        dataset = dsc.readDataset(dsc.path);
        d=  t.RecupDonnees("/Thyroid_Dataset.txt");

        NCluster.setVisible(false);
        Descritization.setVisible(true);
        TempEXECUTION.setText("");
        new FadeInRight(Descritization).play();


        if( NC.getText().isEmpty() || NivSUP.getText().isEmpty() )
        {snackbar4.show("veuillez remplir tout les champs de discritisation",3000);
        }
        else {
            long tempsDebut = System.currentTimeMillis();
            for(int i = 1 ; i <=5 ; i++) {
                ArrayList<ArrayList<Float>> column = dsc.getColumn(dataset, i);
                ArrayList<ArrayList<Float>> bined = dsc.bining(column,SizeBines.getSelectionModel().getSelectedItem());
                dataset = dsc.smoothing(dataset, bined, i);
            }
            dataset = dsc.delDup(dataset);
            d = dsc.ConvertToAprioriData(dataset);
            long tempsFin = System.currentTimeMillis();
            long seconds = (tempsFin - tempsDebut) ;
            TempEXECUTION.setText("TEMP EXECUTION: "+seconds+"MS");
            new FadeInRight(TempEXECUTION).play();
            Output.setEditable(true);
            AprioriAlgo apriori = new AprioriAlgo();
            Output.setText(apriori.ExtractionItems(d, Integer.valueOf(NC.getText()), Integer.valueOf(NivSUP.getText())));
            Output.setEditable(false);
        }


    }

    public void MEANS(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        NCluster.setVisible(true);
        NumLocal.setVisible(false);
        Maxneighbor.setVisible(false);
        new FadeInRight(NCluster).play();

        TempEXECUTION.setText("");

        if(Descritization.isVisible())
        {

            new FadeOutRight(Descritization).play();


        }
        if(NCluster.getText().isEmpty())
        {
            snackbar4.show("veuillez préciser le nombre de cluster",3000);
        }
        else {
            t = new Traitement() ;
            d = new DataSet();
            dsc = new discritization();
            dsc.path = "/Thyroid_Dataset.txt";
            dataset = dsc.readDataset(dsc.path);

            d=  t.RecupDonnees("/Thyroid_Dataset.txt");
            long tempsDebut = System.currentTimeMillis();
            Kmeans k = new Kmeans();
            Output.setText(k.ClassificationKmeans(d,Integer.valueOf(NCluster.getText())));

            long tempsFin = System.currentTimeMillis();
            long seconds = (tempsFin - tempsDebut) ;
            TempEXECUTION.setText("TEMP EXECUTION: "+seconds+"MS");
            new FadeInRight(TempEXECUTION).play();
        }

    }

    public void MEDIODS(ActionEvent actionEvent) {
        APANE21.setVisible(false);
        NCluster.setVisible(true);
        NumLocal.setVisible(false);
        Maxneighbor.setVisible(false);
        new FadeInRight(NCluster).play();
        TempEXECUTION.setText("");
        if(Descritization.isVisible())
        {

            new FadeOutRight(Descritization).play();


        }
        if(NCluster.getText().isEmpty())
        {
            snackbar4.show("veuillez préciser le nombre de cluster",3000);
        }
        else {
            t = new Traitement() ;
            d = new DataSet();
            dsc = new discritization();
            dsc.path = "/Thyroid_Dataset.txt";
            dataset = dsc.readDataset(dsc.path);
            d=  t.RecupDonnees("/Thyroid_Dataset.txt");
            long tempsDebut = System.currentTimeMillis();
            Kmedoids k_medoids = new Kmedoids();
            Output.setText(k_medoids.ClassificationKmedoids(d,Integer.valueOf(NCluster.getText())));

            long tempsFin = System.currentTimeMillis();
            long seconds = (tempsFin - tempsDebut) ;
            TempEXECUTION.setText("TEMP EXECUTION: "+seconds+"MS");
            new FadeInRight(TempEXECUTION).play();

        }
    }

    public void CLARANS(ActionEvent actionEvent) {
        NCluster.setVisible(true);
        new FadeInRight(NCluster).play();
        NumLocal.setVisible(true);
        new FadeInRight(NumLocal).play();
        Maxneighbor.setVisible(true);
        new FadeInRight(Maxneighbor).play();
        APANE21.setVisible(false);
        TempEXECUTION.setText("");
        if(Descritization.isVisible())
        {

            new FadeOutRight(Descritization).play();


        }




        if(NCluster.getText().isEmpty()||Maxneighbor.getText().isEmpty()||NumLocal.getText().isEmpty())
        {
            snackbar4.show("veuillez remplir tout les parametres",3000);
        }
        else {
            t = new Traitement();
            d = new DataSet();

            d = t.RecupDonnees("/Thyroid_Dataset.txt");
            Clarans c = new Clarans();
            long tempsDebut = System.currentTimeMillis();
            String res = c.CLARANS_Algorithme(Integer.valueOf(NCluster.getText()), Integer.valueOf(NumLocal.getText()), Integer.valueOf(Maxneighbor.getText()), c.ConvertToClaransData(d));
            Output.setText(res);
            long tempsFin = System.currentTimeMillis();
            long seconds = (tempsFin - tempsDebut) ;
            TempEXECUTION.setText("TEMP EXECUTION: "+seconds+"MS");
            new FadeInRight(TempEXECUTION).play();

        }


    }
    public void afficherDescription(){

        String data="";
        try {
            InputStream inputStream = Data.class.getResourceAsStream("/Thyroid_Dataset_Information.txt");
            InputStreamReader inputReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader myReader = new BufferedReader(inputReader);

            String ligne;
            while ((ligne = myReader.readLine()) != null) {
                data += "\n"+ligne;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            data = "An error occurred.";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jfxTextArea.setText(data);
        jfxTextArea.setEditable(false);

        APANE21.setVisible(true);

    }
}

