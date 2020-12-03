package Commands;


import java.util.ArrayList;

import Interpeter.Command;
import Interpeter.Utilities;

public class OpenDataServerCommand implements Command{

	public int numOfArgs = 2;
	


	@Override
	public void testArgs(String args){
		
		
	}


	@Override
	public int doCommand(ArrayList<String> args) {
		
		int port,timePerS;
		
		
		port = Integer.parseInt(args.get(1));
		timePerS = Integer.parseInt(args.get(2));
		
		
		return numOfArgs;
	}

}
