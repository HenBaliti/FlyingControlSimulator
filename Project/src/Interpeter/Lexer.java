package Interpeter;

import java.util.ArrayList;
import java.util.Scanner;

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

//	@Override
//	public ArrayList<String> lexer(String[] line) {
//		ArrayList<String> tokens = new ArrayList<>();
//		String[] arr = null;
//		for(int i=0;i<line.length;i++) {
//			arr = line[i].split(" ");
//			for(int j=0;j<arr.length;j++) {
//				tokens.add(arr[j]);
//			}
//		}
//		
//		return tokens;
//	}


}
