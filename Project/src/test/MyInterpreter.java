package test;

import Commands.Utilities;
import Interpeter.Parser;

public class MyInterpreter {

	public static  int interpret(String[] lines){
		
		Utilities ut = new Utilities();
		Parser ps = new Parser();
		ps.parse(lines); // Parsing all the Object and Vars
		
		
		return ps.returnedValue;
	}
}
