package com.company.module.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scanner {

    private ArrayList<String> reservedWords = new ArrayList<>();
    private ArrayList<String> operators = new ArrayList<>();
    private ArrayList<String> separators = new ArrayList<>();
    private PIF pif = new PIF();
    private SymbolTable st = new SymbolTable();
    private FiniteAutomatan FAForIdentifiers = new FiniteAutomatan("FAid.txt");
    private FiniteAutomatan FAForConstants = new FiniteAutomatan("FAconst.txt");

    public Scanner(){
        List<String> resWordsList = Arrays.asList("if","read","write","string", "int", "then", "else", "while", "execute");
        List<String> operatorsList = Arrays.asList("=","+","-","/","=",">=", "<=", "<", ">", "==", "%");
        List<String> separatorsList = Arrays.asList(" ",";", ",", "}","{", "[", "]", "(", ")");
        this.reservedWords.addAll(resWordsList);
        this.operators.addAll(operatorsList);
        this.separators.addAll(separatorsList);

    }

    public void getTokensFromLine(String line, int lineNumber){

        String copyline = line;
//        String[] tokens = line.split("[\\[|\\(|\\)|\\s|\\;|\\{|\\}|\\]]+");
        String[] tokens = line.split("[\\[|\\s|\\;|\\{|\\}|\\]]+");

           for (int i = 0; i < tokens.length; i++) {
              String token = tokens[i];
              if (this.isReservedWord(token) || this.isOperator(token) || (separators.contains(token))){
                       pif.genPIF(token, -1);
              }else{
                    if (this.isIdentifier(token)) {
                        int positionInPif = pif.pos(token, st);
                        pif.genPIF("identifier", positionInPif);
                    }else if(this.isConstant(token)) {
                        int positionInPif = pif.pos(token, st);
                        pif.genPIF("constant", positionInPif);
                    }else if(token.contains("\"")) {
                        StringBuilder stringConstant = new StringBuilder(token + " ");
                        int idx = i+1;
                        String nextToken = tokens[idx];
                        while(idx < tokens.length && !nextToken.contains("\"")){
                            stringConstant.append(nextToken);
                            stringConstant.append(" ");
                            idx++;
                            nextToken = tokens[idx];
                        }
                        stringConstant.append(tokens[idx]);
                        i = idx;
                        int positionInPif = pif.pos(stringConstant.toString(), st);
                        pif.genPIF("constant", positionInPif);

                    }else{
                        System.out.println("At line: " + lineNumber + " error: " + token );
                        System.out.println("Lexical error.");
                    }

                }
           }
        int index = 0;
        while (index < line.length()) {
            String sep = String.valueOf(line.charAt(index));
            if (sep.equals(";") || sep.equals("}") || sep.equals("{")) {
                pif.genPIF(sep, -1);
            }
            index++;


           }

        }

    public Boolean isReservedWord(String word){
        return reservedWords.contains(word);
    }

    public Boolean isOperator(String op){
        return operators.contains(op);
    }

    public Boolean isSeparator(String sep){
        return separators.contains(sep);
    }

    public Boolean isIdentifier(String id){
        return FAForIdentifiers.isAccepted(id);
//        return id.matches("^[a-z]([a-zA-Z0-9])*");
    }

    public Boolean isConstant(String constant){
        //if(constant.matches("[+-]?[1-9][0-9]*") || constant.matches("0"))
        if(FAForConstants.isAccepted(constant))
            return true;
        else{
            return constant.matches("\"[0-9A-Za-z_]+\"");
        }
    }

    public PIF getPif() {
        return pif;
    }

    public SymbolTable getSt() {
        return st;
    }
}
