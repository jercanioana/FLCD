package com.company.module.entities;
import java.util.ArrayList;

public class F {
    private int k;
    private String symbol;
    private ArrayList<String> set = new ArrayList<>();

    public F(int k, String symbol, ArrayList<String> set) {
        this.k = k;
        this.symbol = symbol;
        this.set = set;
    }

    public int getK() {
        return k;
    }

    public String getSymbol() {
        return symbol;
    }

    public ArrayList<String> getSet() {
        return set;
    }
}
