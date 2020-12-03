package Interpeter;

import java.util.ArrayList;

public class MyInterpreter {

	public static  int interpret(String[] lines){
		
		Parser ps = new Parser();
		ps.parse(lines); // Parsing all the Object and Vars
		
		return 0;
	}
}
