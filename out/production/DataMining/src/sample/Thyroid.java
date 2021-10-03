package sample;

import java.util.ArrayList;

public class Thyroid {

    private String Class_attribute;
    private String T3_resin;
    private String thyroxin;
    private String triodothyronine;
    private String TSH;


    private String Max_TSH;


    public Thyroid(ArrayList<Float> line) {
        Class_attribute = String.valueOf(line.get(0));
        T3_resin = String.valueOf(line.get(1));
        this.thyroxin = String.valueOf(line.get(2));
        this.triodothyronine = String.valueOf(line.get(3));
        this.TSH = String.valueOf(line.get(4));
        Max_TSH = String.valueOf(line.get(5));
    }

    public String getT3_resin() {
        return T3_resin;
    }

    public void setT3_resin(String t3_resin) {
        T3_resin = t3_resin;
    }

    public String getClass_attribute() {
        return Class_attribute;
    }

    public void setClass_attribute(String class_attribute) {
        Class_attribute = class_attribute;
    }

    public String getThyroxin() {
        return thyroxin;
    }

    public void setThyroxin(String thyroxin) {
        this.thyroxin = thyroxin;
    }

    public String getTriodothyronine() {
        return triodothyronine;
    }

    public void setTriodothyronine(String triodothyronine) {
        this.triodothyronine = triodothyronine;
    }

    public String getTSH() {
        return TSH;
    }

    public void setTSH(String TSH) {
        this.TSH = TSH;
    }

    public String getMax_TSH() {
        return Max_TSH;
    }

    public void setMax_TSH(String max_TSH) {
        Max_TSH = max_TSH;
    }




    @Override
    public String toString() {
        return "Thyroid{" +
                "Class_attribute='" + Class_attribute + '\'' +
                ", T3_resin='" + T3_resin + '\'' +
                ", thyroxin='" + thyroxin + '\'' +
                ", triodothyronine='" + triodothyronine + '\'' +
                ", TSH='" + TSH + '\'' +
                ", Max_TSH='" + Max_TSH + '\'' +
                '}';
    }


}
