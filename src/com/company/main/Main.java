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
					myWriter.write("Token: " + pif.getElement(i).getK() + " " + "Position: " + pif.getElement(i).getV() + "\n");
				}
				myWriter.close();

			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

		SymbolTable st = scanner.getSt();
		st.inorder();
		FiniteAutomatan fa = new FiniteAutomatan();
		fa.getFAFromFile();
		System.out.println("Q: ");
		fa.printQ();
		System.out.println("Alphabet: ");
		fa.printAlphabet();
		System.out.println("Initial state: ");
		fa.printInitialState();
		System.out.println("Final states: ");
		fa.printF();
		System.out.println("Transition function: ");
		fa.printTransitionFunction();
		System.out.println("Is deterministic: ");
		System.out.println(fa.isDeterministic());
		String[] symbols = new String[]{"a", "a", "b"};
		System.out.println(fa.isAccepted(symbols));
    }
}
