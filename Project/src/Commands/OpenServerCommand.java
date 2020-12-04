package Commands;


import java.util.ArrayList;

import server_side.ClientHandler;
import server_side.MyClientHandler;
import server_side.MySerialServer;
import server_side.Server;

public class OpenServerCommand implements Command{

	public int numOfArgs = 2;
	Server s;


	@Override
	public void testArgs(String args){
		
		
	}


	@Override
	public int doCommand(ArrayList<String> args) {
		
		int port,timePerS;
		port = Integer.parseInt(args.get(1));
		timePerS = Integer.parseInt(args.get(2));
		s = new MySerialServer();
//		s.open(port, new ClientHandler()) {
//			
//		}
	
		
		return numOfArgs;
	}

}
