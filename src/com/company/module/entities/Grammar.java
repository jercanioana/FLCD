package com.company.module.entities;

import java.awt.color.ProfileDataException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Grammar {
    private ArrayList<String> nonTerminals = new ArrayList<>();
    private ArrayList<String> terminals = new ArrayList<>();
    private ArrayList<Production> products = new ArrayList<>();
    private String startSymbol;
    private String fileName;

    public Grammar(String fileName){
        this.fileName = fileName;
        readFromFile();
    }

    public void readFromFile(){
        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = bufferedReader.readLine();
            int linenumber = 0;
            while(line != null){
                String[] elements = line.split(";");
                if(linenumber == 0){
                    nonTerminals.addAll(Arrays.asList(elements));

                }
                if(linenumber == 1){
                    terminals.addAll(Arrays.asList(elements));
                }
                if(linenumber == 2){
                    startSymbol = elements[0];
                }
                else if(linenumber > 2){
                    String first = elements[0];
                    ArrayList<String> second = new ArrayList<>(Arrays.asList(elements).subList(1, elements.length));
                    Production p = new Production(first, second);
                    products.add(p);

                }
                linenumber++;
                line = bufferedReader.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public ArrayList<String> getNonTerminals() {
        return nonTerminals;
    }

    public ArrayList<String> getTerminals() {
        return terminals;
    }

    public ArrayList<Production> getProducts() {
        return products;
    }

    public Production getProduct(String s){
        for(Production p : products){
            if(p.getFirst().equals(s))
                return p;
        }
        return null;
    }

    public ArrayList<Production> productionContaining(String s) {
        ArrayList<Production> result = new ArrayList<>();
        for (Production p: products)
            for(String st: p.getSecond())
            {
                int ok = 0;
                String[] tokens = st.split(" ");
                for(String t: tokens)
                    if(t.equals(s)) {
                        ok = 1;
                        result.add(p);
                        break;
                    }
                if(ok == 1)
                    break;

            }
        return result;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

}
