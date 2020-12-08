package Interpeter;

import java.util.ArrayList;
import java.util.Scanner;

// break up strings into a set of tokens
public class Lexer implements LexerInterface {

	@Override
	public ArrayList<String> lexer(String[] line) {
		ArrayList<String> tokens = new ArrayList<>();
		for(String st:line) {
			Scanner scan = new Scanner(st);
			while(scan.hasNext()) {
				tokens.add(scan.next());
			}
		}
		return tokens;
	}



}
