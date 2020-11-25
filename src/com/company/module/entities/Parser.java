package com.company.module.entities;

import java.util.ArrayList;

public class Parser {

    private Grammar g;

    public Parser(Grammar g){
        this.g = g;
    }


    public ArrayList<String> first(String x){


        ArrayList<F> setF = new ArrayList<>();
        ArrayList<String> terminals = g.getTerminals();
        ArrayList<String> nonTerminals = g.getNonTerminals();
        ArrayList<Production> products = g.getProducts();

        for(String nonTerminal: nonTerminals){

            ArrayList<String> set = new ArrayList<>();
            for(Production p: products){
                if(p.getFirst().equals(nonTerminal)){
                    for(String s: p.getSecond()){
                        String[] tokens = s.split(" ");
                        if(terminals.contains(tokens[0]) || (tokens.length == 1 && tokens[0].equals("#"))){
                            set.add(tokens[0]);
                        }
                    }
                }
            }
            F f = new F(0, nonTerminal, set);
            setF.add(f);
        }

        int i = 0;
        boolean stop = false;
        do{
            i++;
            for(String nonTerminal: nonTerminals){
                if(exists(setF, i)){

                }
            }
        }while(!stop);

        return new ArrayList<>();

    }

    public boolean exists(ArrayList<F> fs, int idx){
        int count = 0;
        for(String nonTerminal: g.getNonTerminals()) {
            for (F x : fs) {
                if(x.getSymbol().equals(nonTerminal)) {
                    if (x.getK() == idx) {
                        count++;
                    }
                }
            }
        }
        return (count == g.getNonTerminals().size());
    }
}
