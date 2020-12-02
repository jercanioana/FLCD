package com.company.module.entities;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Parser {

    private Grammar g;
    private ArrayList<F> firsts = new ArrayList<>();
    private ArrayList<F> follows = new ArrayList<>();


    public Parser(Grammar g) {
        this.g = g;
        initFirst();
        initFollow();

    }


    public void initFollow() {
        String S = g.getStartSymbol();
        F f = new F(S);
        f.add("$");
        follows.add(f);
        for (String n : g.getNonTerminals())
            if (!n.equals(S)) {
                F f1 = new F(n);
                follows.add(f1);
            }
        for (String n : g.getNonTerminals())
            getFollow(n);
    }

    private ArrayList<String> getAll(Production p) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : p.getSecond()) {
            String[] tokens = s.split(" ");
            result.addAll(Arrays.asList(tokens));
        }
        return result;
    }


    private void getFollow(String n) {
        ArrayList<Production> productions = g.productionContaining(n);
        for (Production p : productions) {
            ArrayList<String> items = getAll(p);
            if (items.contains(n)) {
                int index = items.indexOf(n);
                index++;
                if (index == items.size()) {
                    getFollow(p.getFirst());
                    for (String s : getFF(p.getFirst()).getSet())
                        addF(n, s);
                } else {
                    ArrayList<ArrayList<String>> temp = new ArrayList<>();
                    for(int k = index; k < items.size(); k++){
                        String next = items.get(k);
                        ArrayList<String> copy = new ArrayList<>(findF(next).getSet());
                        temp.add(copy);
                    }
                    ArrayList<String> result = concat(temp);
                    if(!result.contains("#")){
                        for(String s: result){
                            addF(n, s);
                        }
                    }else{
                        result.remove("#");
                        for(String s: result){
                            addF(n, s);
                        }
                        getFollow(p.getFirst());
                        for(String t: getFF(p.getFirst()).getSet()){
                            addF(n,t);
                        }
                    }
                }
            }
        }

    }

    public F getFF(String s) {
        for (F f : follows)
            if (f.getSymbol().equals(s))
                return f;
        return null;
    }

    public void initFirst() {

        ArrayList<String> terminals = g.getTerminals();
        ArrayList<String> N = g.getNonTerminals();

        for (String terminal : terminals) {
            F f = new F(terminal);
            f.add(terminal);
            firsts.add(f);
        }

        for (String nonTerminal : N) {
            F f = new F(nonTerminal);
            firsts.add(f);
        }
        for (String nonTerminal : N) {
            getFirst(nonTerminal);
        }
    }

    public void add(String s, String tba) {
        for (F f : firsts) {
            if (f.getSymbol().equals(s)) {
                f.add(tba);
                break;
            }
        }
    }

    public void addF(String s, String tba) {
        for (F f : follows) {
            if (f.getSymbol().equals(s)) {
                f.add(tba);
                break;
            }
        }
    }

    public void getFirst(String nonTerminal) {
        Production p = g.getProduct(nonTerminal);
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        for (String pr : p.getSecond()) {
            String[] tokens = pr.split(" ");
            int index = 0;
            for (String token : tokens) {
                index++;
                if (g.getTerminals().contains(token)) {
                    if(index <= 1)
                        add(nonTerminal, token);
                    ArrayList<String> elem = new ArrayList<>();
                    elem.add(token);
                    temp.add(elem);
                } else {
                    getFirst(token);
                    ArrayList<String> setToken = findF(token).getSet();
                    temp.add(setToken);
                }


            }
        }
        ArrayList<String> result = concat(temp);
        for (String s : result) {
            add(nonTerminal, s);
        }
    }

    private ArrayList<String> concat(ArrayList<ArrayList<String>> temp) {

        if (temp.size() == 1)
            return temp.get(0);
        ArrayList<String> result = new ArrayList<>();
        boolean check = true;
        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = 0; j < temp.size(); j++) {
                if (!temp.get(i).contains("#") || !temp.get(j).contains("#")) {
                    check = false;
                }
                for (String s1 : temp.get(i)) {
                    for (String s2 : temp.get(j)) {
                        if (s1.equals("#") && !s2.equals("#"))
                            result.add(s2);
                        else if (!s1.equals("#")) {
                            result.add(s1);
                        }
                    }
                }
            }
        }
        if (check) {
            result.add("#");
        }
        return result;
    }

    public F findF(String symbol) {
        for (F f : firsts) {
            if (f.getSymbol().equals(symbol)) {
                return f;
            }
        }
        return null;
    }

    public ArrayList<String> getFirsts(String X) {
        for (F f : firsts)
            if (f.getSymbol().equals(X))
                return (ArrayList<String>) f.getSet().stream().distinct().collect(Collectors.toList());
        ;
        return null;


    }
}