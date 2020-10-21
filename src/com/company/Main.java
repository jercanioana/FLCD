package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
    	Node root = new Node("d");
	    SymbolTable symtbl = new SymbolTable(root);
	    Scanner scanner = new Scanner();
//	    symtbl.insertNode("a");
//	    symtbl.insertNode("c");
//	    symtbl.insertNode("b");
//
//        System.out.println(symtbl.toString());
//		System.out.println(symtbl.root);
//		System.out.println(symtbl.findNode(symtbl.root, "a"));
		BufferedReader bufferedReader;
		try{
			bufferedReader = new BufferedReader(new FileReader(""));
			String line = bufferedReader.readLine();
			while(line != null){
				System.out.println(line);
				scanner.getTokensFromLine(line);
				line = bufferedReader.readLine();
			}
		}catch(IOException e){
			e.printStackTrace();
		}

    }
}
