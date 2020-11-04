package com.company.main;

import com.company.module.entities.Node;
import com.company.module.entities.PIF;
import com.company.module.entities.Scanner;
import com.company.module.entities.SymbolTable;

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


    }
}
