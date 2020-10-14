package com.company;

public class Main {

    public static void main(String[] args) {
    	Node root = new Node("d");
	    SymbolTable symtbl = new SymbolTable(root);
	    symtbl.insertNode("a");
	    symtbl.insertNode("c");
	    symtbl.insertNode("b");

        System.out.println(symtbl.toString());
		System.out.println(symtbl.findNode("a"));
    }
}
