package com.company.module.entities;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private Grammar g;
    private ArrayList<F> firsts = new ArrayList<>();
    private ArrayList<F> follows = new ArrayList<>();
    private ArrayList<Pair<String, String>> numberedProductions = new ArrayList<>();
    private ParseTable parseTable = new ParseTable();
    private Stack<String> alpha = new Stack<>();
    private Stack<String> beta = new Stack<>();
    private Stack<String> pi = new Stack<>();

    public Parser(Grammar g) {
        this.g = g;
        initFirst();
        initFollow();
        createParseTable();

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
        for (String n : g.getNonTerminals()){
            getFollow(n, new ArrayList<>());
        }
    }

    private ArrayList<String> getAll(Production p) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : p.getSecond()) {
            String[] tokens = s.split(" ");
            result.addAll(Arrays.asList(tokens));
        }
        return result;
    }


    public void getFollow(String n, ArrayList<String> visited) {

        ArrayList<Production> productions = g.productionContaining(n);
        for(Production p: productions)
        {
            String startS = p.getFirst();
            for (String s: p.getSecond()) {
                String[] tokenstemp = s.split(" ");
                ArrayList<String> tokens = new ArrayList<>(Arrays.asList(tokenstemp));
                if (tokens.contains(n)) {
                    if(visited.contains(n))
                        continue;
                    int index = tokens.indexOf(n);
                    index++;
                    if(index == tokens.size()) {
                        visited.add(n);
                        getFollow(startS, visited);
                        for (String t: getFF(startS).getSet())
                            addF(n,t);
                    }
                    else {
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        for (int k = index; k < tokens.size(); k++) {
                            String next = tokens.get(k);
                            ArrayList<String> firstSet = getFirsts(next);
                            temp.add(firstSet);
                        }
                        ArrayList<String> result = concat(temp);
                        if (!result.contains("#"))
                            for (String r: result)
                                addF(n, r);
                        else {
                            result.remove("#");
                            for (String r: result)
                                addF(n, r);
                            visited.add(n);
                            getFollow(startS, visited);
                            for (String t: getFollows(startS))
                                addF(n,t);
                        }
                    }
                }

            }
        }


    }
    private ArrayList<String> concat(ArrayList<ArrayList<String>> temp) {
        if (temp.size() == 1)
            return temp.get(0);
        ArrayList<String> result = new ArrayList<>();
        boolean check = true;

        for (int i = 0; i < temp.size() - 1; i++)
            for(int j = 0; j < temp.size(); j++)
            {
                if (!temp.get(i).contains("#") || !temp.get(j).contains("#"))
                    check = false;
                for (String s1: temp.get(i))
                    for(String s2: temp.get(j))
                        if(s1.equals("#") && !s2.equals("#"))
                            result.add(s2);
                        else if (!s1.equals("#"))
                            result.add(s1);
            }
        if(check)
            result.add("#");
        return result;
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
            F f = findF(nonTerminal);
            f.setSet(getFirst(nonTerminal));
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

    public ArrayList<String> getFirst(String nonTerminal) {
        Production p = g.getProduct(nonTerminal);
        ArrayList<String> temp = new ArrayList<>();
        for (String pr : p.getSecond()) {
            String[] tokens = pr.split(" ");
            String token = tokens[0];
            if(token.equals("#")){
                temp.add("#");
            }
            else if(g.getTerminals().contains(token)){
                temp.add(token);
            }else
                temp.addAll(getFirst(token));
        }


        return temp;
    }

    public void createParseTable(){
        numberingProductions();
        ArrayList<String> columns = new ArrayList<>(g.getTerminals());
        columns.add("$");
        columns.remove("#");
        parseTable.put(new Pair<>("$", "$"), new Pair<>(new ArrayList<String>(Collections.singleton("accept")), -1));
        for(String t: g.getTerminals()){
            parseTable.put(new Pair<>(t, t), new Pair<>(new ArrayList<String>(Collections.singleton("pop")), -1));
        }
        for(Pair<String, String> p:numberedProductions) {
            String row = p.getL();
            String[] rhs1 = p.getR().split(" ");
            ArrayList<String> rhs = new ArrayList<>();
            rhs.addAll(Arrays.asList(rhs1));
            Pair<ArrayList<String>, Integer> parseTableValue = new Pair<>(rhs, numberedProductions.indexOf(p)+1);
            for(String column: columns){
                Pair<String, String> key = new Pair<>(row, column);
                if(rhs.get(0).equals(column) && !column.equals("#"))
                    parseTable.put(key, parseTableValue);
                else if(g.getNonTerminals().contains(rhs.get(0)) && findF(rhs.get(0)).getSet().contains(column)){
                    if(!parseTable.containsKey(key)){
                        parseTable.put(key, parseTableValue);
                    }
                }
                else{
                    if (rhs.get(0).equals("#")) {
                        for (String b : getFF(row).getSet())
                            if(!parseTable.containsKey(new Pair<>(row, b))) {
                                parseTable.put(new Pair<>(row, b), parseTableValue);
                            }

                    }

                }
            }

        }
    }

    private void initStacks(ArrayList<String> w) {
        alpha.clear();
        alpha.push("$");
        pushChars(w, alpha);

        beta.clear();
        beta.push("$");
        beta.push(g.getStartSymbol());

        pi.clear();
        pi.push("#");
    }

    private void pushChars(ArrayList<String> w, Stack<String> alpha) {
        for(int i = w.size() - 1; i >= 0; i--)
            alpha.push(w.get(i));
    }

    public boolean parse(ArrayList<String> w) {
        initStacks(w);
        boolean go = true;
        boolean result = true;

        while(go) {
            String headb = beta.peek();
            String heada = alpha.peek();
            System.out.println("alpha");
            System.out.println(getAlpha());
            System.out.println("beta");
            System.out.println(getBeta());
            System.out.println("pi");
            System.out.println(getPi());

            if(headb.equals("$") && heada.equals("$"))
                return result;

            Pair<String, String> heads = new Pair<>(headb, heada);
            Pair<ArrayList<String>, Integer> parseT = parseTable.get(heads);

            if (parseT == null) {
                heads = new Pair<>(headb, "#");
                parseT = parseTable.get(heads);
                if (parseT != null) {
                    beta.pop();
                    continue;
                }
            }

            if(parseT == null) {
                go = false;
                result = false;
            }
            else {
                ArrayList<String> production = parseT.getL();
                Integer prodPos = parseT.getR();

                if(prodPos == -1 && production.get(0).equals("accept"))
                    go = false;
                else if (prodPos == -1 && production.get(0).equals("pop")) {
                    beta.pop();
                    alpha.pop();
                }
                else {
                    beta.pop();
                    if(!production.get(0).equals("#"))
                        pushChars(production,beta);
                    pi.push(prodPos.toString());
                }
            }
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

    public void numberingProductions(){
        for(Production production: g.getProducts()) {
            for (String second : production.getSecond()) {
                numberedProductions.add(new Pair(production.getFirst(), second));
            }
        }
    }

    public ArrayList<String> getFollows(String X) {
        for (F f : follows)
            if (f.getSymbol().equals(X))
                return (ArrayList<String>) f.getSet().stream().distinct().collect(Collectors.toList());
        return null;

    }

    public ParseTable getParseTable(){
        return parseTable;
    }

    public Stack<String> getAlpha() {
        return alpha;
    }

    public Stack<String> getBeta() {
        return beta;
    }

    public Stack<String> getPi() {
        return pi;
    }
}