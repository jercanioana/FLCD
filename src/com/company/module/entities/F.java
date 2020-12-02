package com.company.module.entities;
import java.util.ArrayList;

public class F {
    private String symbol;
    private ArrayList<String> set = new ArrayList<>();

    public F(String symbol) {

        this.symbol = symbol;
    }

    public void add(String s){
        set.add(s);
    }

    public String getSymbol() {
        return symbol;
    }

    public ArrayList<String> getSet() {
        return set;
    }

    @Override
    public String toString() {
        return "F{" +
                "symbol='" + symbol + '\'' +
                ", set=" + set +
                '}';
    }
}
