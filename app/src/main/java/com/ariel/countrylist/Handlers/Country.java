package com.ariel.countrylist.Handlers;

import java.util.ArrayList;

public class Country {
    private String name;
    private String nativeName;
    private String flag;
    private ArrayList<String> borders;

    public Country(String name, String nativeName, String flag, ArrayList<String> borders) {
        this.name = name;
        this.nativeName = nativeName;
        this.flag = flag;
        this.borders = borders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public void setBorders(ArrayList<String> borders) {
        this.borders = borders;
    }
}
