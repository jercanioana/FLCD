package com.company.module.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class FiniteAutomatan {
    private ArrayList<String> Q = new ArrayList<>();
    private String initialState;
    private ArrayList<Function<String, String, String>> transitionFunction = new ArrayList<>();
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<String> F = new ArrayList<>();

    public void getFAFromFile(){
        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader(new FileReader("FA.txt"));
            String line = bufferedReader.readLine();
            int linenumber = 0;
            while(line != null){
                String[] elements = line.split(" ");
                if(linenumber == 0)
                    if(!containsDuplicates(elements)) {
                        Q.addAll(Arrays.asList(elements));
                    }
                if(linenumber == 1)
                    if(!containsDuplicates(elements)) {
                        alphabet.addAll(Arrays.asList(elements));
                    }
                if(linenumber == 2){
                    if(Q.contains(elements[0])) {
                        initialState = elements[0];
                    }
                }
                if(linenumber == 3){
                    if(Q.containsAll(Arrays.asList(elements))){
                        F.addAll(Arrays.asList(elements));
                    }

                }else if(linenumber > 3){
                    boolean ok = false;
                    ArrayList<String> list = new ArrayList<>();
                    list.add(elements[2]);
                    if(Q.contains(elements[0]) && alphabet.contains(elements[1]) && Q.contains(elements[2])) {
                        Function TransFunction = new Function(elements[0], elements[1], list);
                        for (Function pairs : transitionFunction) {
                            if (pairs.getStartState().equals(elements[0]) && pairs.getSymbol().equals(elements[1])) {
                                ArrayList<String> J = pairs.getResultState();
                                J.add(elements[2]);
                                pairs.setResultState(J);
                                ok = true;
                            }
                        }
                        if (!ok) {
                            transitionFunction.add(TransFunction);
                        }

                    }
                }

                linenumber++;
                line = bufferedReader.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public boolean containsDuplicates(String[] array){
        Long distinctCount = Stream.of(array)
                .distinct().count();

        return array.length != distinctCount;
    }

    public boolean isDeterministic(){
        for(Function pairs: transitionFunction){
            if(pairs.getResultState().size() > 1){
                return false;
            }
        }
        return true;
    }

    public boolean isAccepted(String[] sequence){

        if(!this.isDeterministic())
            return false;

        String start = this.initialState;
        boolean found = false;
        for(int i = 0; i < sequence.length; i++) {
            String symbol = sequence[i];
            for (Function function : transitionFunction) {
                if (function.getStartState().equals(start)) {
                    if (function.getSymbol().equals(symbol)) {
                        start = (String) function.getResultState().get(0);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                return false;
            }
        }

        for(String state: F){
            if(state.equals(start)){
                return true;
            }
        }
        return false;
    }

    public void printF(){
        for(String f: F){
            System.out.println(f);
        }
    }

    public void printQ(){
        for(String q: Q){
            System.out.println(q);
        }
    }

    public void printAlphabet(){
        for(String q: alphabet){
            System.out.println(q);
        }
    }

    public void printInitialState(){
        System.out.println(initialState);
    }

    public void printTransitionFunction(){
        for(Function function :transitionFunction){
            function.printFunction();
        }
    }

    @Override
    public String toString() {
        return "FiniteAutomata{" +
                "Q=" + Q +
                ", initialState='" + initialState + '\'' +
                ", transitionFunction='" + transitionFunction + '\'' +
                ", alphabet=" + alphabet +
                ", F=" + F +
                '}';
    }
}
