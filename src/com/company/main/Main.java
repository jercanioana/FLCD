package com.company.main;

import com.company.module.entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

	    Scanner scanner = new Scanner();

		BufferedReader bufferedReader;
		try{
			bufferedReader = new BufferedReader(new FileReader("p3.txt"));
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
			try {
				FileWriter myWriter = new FileWriter("PIF.out");
				for(int i = 0; i < pif.getSize(); i++) {
					myWriter.write("Token: " + pif.getElement(i).getL() + " " + "Position: " + pif.getElement(i).getR() + "\n");
				}
				myWriter.close();

			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

		Grammar g = new Grammar("g1.txt");
		System.out.println(g.getNonTerminals());
		System.out.println(g.getTerminals());
		for(Production p : g.getProducts()) {
			System.out.println(p.toString());
		}
		System.out.println(g.getStartSymbol());
		Parser p = new Parser(g);
		System.out.println(p.getFirsts("S"));

    }
}
