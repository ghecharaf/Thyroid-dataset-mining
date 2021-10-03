
package sample;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.paint.Paint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Boxplot {
    static DefaultBoxAndWhiskerCategoryDataset dataset;

    ArrayList<Float> T3=new ArrayList<>(),thy=new ArrayList<>(),trio=new ArrayList<>(),TSH=new ArrayList<>();

    public JFreeChart BoxAndWhiskerDemo() {


        final BoxAndWhiskerCategoryDataset dataset = Boxplot.dataset;
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRangeIncludesZero(false);

        BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setMeanVisible(false);

        renderer.setMaximumBarWidth(0.2);
        renderer.setUseOutlinePaintForWhiskers(false);


        CategoryPlot plot;
        plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        plot.setRenderer(renderer);
        JFreeChart chart = new JFreeChart("BoxAndWhiskerDemo",new Font("SansSerif", Font.BOLD, 12), plot,false);
        ChartPanel chartPanel = new ChartPanel(chart);

        return chart;
    }

    public BoxAndWhiskerCategoryDataset createSampleDataset(int i) {

        dataset = new DefaultBoxAndWhiskerCategoryDataset();


        ArrayList<ArrayList<Float>> data =Data.RecupDonnees("/Thyroid_Dataset.txt");
        for (ArrayList<Float> list: data) {
            T3.add(list.get(1));
            thy.add((list.get(2)));
            trio.add(list.get(3));
            TSH.add(list.get(4));
        }
        if(i==0) {
            dataset.clear();
            dataset.add(min_max_normalization(T3), "T3", "values");
            dataset.add(min_max_normalization(thy), "thyroid", "values");
            dataset.add(min_max_normalization(trio), "trio", "values");
            dataset.add(min_max_normalization(TSH), "TSH", "values");
        }

        else if(i==1){
            dataset.clear();
            dataset.add(T3,"T3_resin","T3_resin");
        }
        else if (i==2){
            dataset.clear();
            dataset.add(thy,"thyroxin","thyroxin");
        }
        else if(i==3){
            dataset.clear();
            dataset.add(trio,"triodothyronine","triodothyronine");
        }
        else if(i==4){
            dataset.clear();
            dataset.add(TSH,"TSH","TSH");
        }

        return dataset;
    }

    public double dataHigh,dataLow,normalizedHigh,normalizedLow;

    public double normalize(Float x) {
        return ((x - dataLow)
                / (dataHigh - dataLow))
                * (normalizedHigh - normalizedLow) + normalizedLow;
    }

    public ArrayList<Float> min_max_normalization(ArrayList<Float> a ){
        ArrayList<Float> b = new ArrayList<Float>();

        this.dataHigh = Collections.max(a);
        this.dataLow = Collections.min(a);
        this.normalizedHigh = 1;
        this.normalizedLow = 0;

        for (float i : a){
            double x = normalize(i);
            b.add((float)x);
        }

        return b;
    }

    public JFreeChart histogram(int var){
        ArrayList<Float> a = null;
        String title ="",color = "";

        switch (var){
            case 1 : {
                a = T3;
                title = "T3";
                color = "red";
                break;
            }
            case 2 : {
                a = thy;
                title = "THY";
                color = "purple";
                break;
            }
            case 3 : {
                a = TSH;
                title = "TSH";
                color = "black";
                break;
            }
            case 4 : {
                a = trio;
                title = "trio";
                color = "yellow";
                break;
            }

        }

        this.dataHigh = Collections.max(a);
        this.dataLow = Collections.min(a);
        JFreeChart chart = null ;
        double[] value = new double[a.size()];
        Random generator = new Random();
        for (int i = 0 ;i < a.size();i++) {
            value[i] = a.get(i) ;
            int number = (int) dataHigh - (int) dataLow;
            HistogramDataset dataset = new HistogramDataset();
            dataset.setType(HistogramType.RELATIVE_FREQUENCY);
            dataset.addSeries("Histogram",value,number);
            String plotTitle = "Histogramme " + title;
            String xaxis = "number";
            String yaxis = "value";
            PlotOrientation orientation = PlotOrientation.VERTICAL;
            boolean show = false;
            boolean toolTips = false;
            boolean urls = false;
            chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis,
                    dataset, orientation, show, toolTips, urls);

        }
        return chart ;
    }


    public JFreeChart XYGraphDataSet(int x,int y){
        ArrayList<Float> a = null;
        ArrayList<Float> b = null;
        String titlex = "";
        String titley = "";

        switch (x){
            case 1 : {
                a = T3;
                titlex = "T3";
                break;
            }
            case 2 : {
                a = thy;
                titlex = "THY";
                break;
            }
            case 3 : {
                a = TSH;
                titlex = "TSH";
                break;
            }
            case 4 : {
                a = trio;
                titlex = "trio";
                break;
            }

        }

        switch (y){
            case 1 : {
                b = T3;
                titley = "T3";
                break;
            }
            case 2 : {
                b = thy;
                titley = "THY";
                break;
            }
            case 3 : {
                b = TSH;
                titley = "TSH";
                break;
            }
            case 4 : {
                b = trio;
                titley = "trio";
                break;
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries data = new XYSeries("Scatterplot");

        for (int i = 0 ;i < a.size();i++) {
            data.add(a.get(i),b.get(i));
        }


        dataset.addSeries(data);

        XYPlot plot = new XYPlot();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, new XYLineAndShapeRenderer(false, true));
        plot.setDomainAxis(0, new NumberAxis(titlex));
        plot.setRangeAxis(0, new NumberAxis(titley));
        plot.mapDatasetToDomainAxis(0, 0);
        plot.mapDatasetToRangeAxis(0, 0);


        JFreeChart chart = new JFreeChart(
                "Diagramme de dispersion",
                JFreeChart.DEFAULT_TITLE_FONT, plot,
                true
        );

        return chart;

    }
}
