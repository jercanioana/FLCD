package com.company.module.entities;

import java.util.ArrayList;

public class Function<K,V,J> {
    private String startState;
    private String symbol;
    private ArrayList<String> resultState = new ArrayList();

    public Function(String startState, String v, ArrayList<String> resultState) {
        this.startState = startState;
        symbol = v;
        this.resultState = resultState;
    }

    public String getStartState() {
        return startState;
    }


    public String getSymbol() {
        return symbol;
    }


    public ArrayList<String> getResultState() {
        return resultState;
    }




    public void printFunction() {
        for(String rs: resultState){
            System.out.println("(" + startState + "," + symbol + ") = " + rs);
        }

    }

    public void setResultState(ArrayList<String> resultState) {
        this.resultState = resultState;
    }
}
