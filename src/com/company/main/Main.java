package com.company.main;

import com.company.module.entities.Node;
import com.company.module.entities.PIF;
import com.company.module.entities.Scanner;
import com.company.module.entities.SymbolTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

	    Scanner scanner = new Scanner();

		BufferedReader bufferedReader;
		try{
			bufferedReader = new BufferedReader(new FileReader("p2.txt"));
			String line = bufferedReader.readLine();
			int lineNumber = 1;
			while(line != null){
				scanner.getTokensFromLine(line, lineNumber);
				lineNumber++;
				line = bufferedReader.readLine();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		PIF pif = scanner.getPif();
		for(int i = 0; i < pif.getSize(); i++){
			System.out.println("Token: " + pif.getElement(i).getK() + " " + "Position: " + pif.getElement(i).getV());
		}
		SymbolTable st = scanner.getSt();
		st.inorder();

    }
}
