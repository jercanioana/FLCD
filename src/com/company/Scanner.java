package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scanner {

    private ArrayList<String> reservedWords = new ArrayList<>();
    private ArrayList<String> operators = new ArrayList<>();
    private ArrayList<String> separators = new ArrayList<>();
    private PIF pif = new PIF();
    private SymbolTable st = new SymbolTable();

    public Scanner(){
        List<String> resWordsList = Arrays.asList("if", "then", "else", "while", "execute");
        List<String> operatorsList = Arrays.asList("=","+","-","/","=",">=", "<=", "<", ">", "==", "%");
        List<String> separatorsList = Arrays.asList(" ", ",", "}","{", "[", "]", "(", ")");
        this.reservedWords.addAll(resWordsList);
        this.operators.addAll(operatorsList);
        this.separators.addAll(separatorsList);

    }

    public void getTokensFromLine(String line){

        int index = 0;
        while(index < line.length()){
           String token = String.valueOf(line.charAt(index));
           if(token.equals("i") && index + 1 < line.length()){
               String nextChar = String.valueOf(line.charAt(index+1));
               token = token+nextChar;
           }
           if(this.isReservedWord(token) || this.isOperator(token) || this.isSeparator(token)){
               pif.genPIF(token, -1);
               index++;
           }else{
               if(this.isIdentifier(token) || this.isConstant(token)){
                   int positionInPif = pif.pos(token, st);
                   pif.genPIF(token, positionInPif);
                   index++;
               }else{
                   System.out.println("Lexical error.");
               }
           }
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
        return id.matches("^[a-z]([a-zA-Z][0-9])*");
    }

    public Boolean isConstant(String constant){
        return constant.matches("");
    }

}
