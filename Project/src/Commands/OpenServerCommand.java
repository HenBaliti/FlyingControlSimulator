package Commands;


import java.util.List;

import server_side.ClientHandler;
import server_side.MyClientHandler;
import server_side.MySerialServer;
import server_side.Server;

public class OpenServerCommand implements Command{

	public int numOfArgs = 2;
	Server s;
	private int port;
	private int timePerS;

	public OpenServerCommand() {
		super();
		this.port=0;
		this.timePerS=0;
	}

	@Override
	public void testArgs(String args){
		
		
	}


	@Override
	public int doCommand(List<String> args) {
		
		port = Integer.parseInt(args.get(1));
		timePerS = Integer.parseInt(args.get(2));
		s = new MySerialServer();
//		s.open(port, new ClientHandler()) {
//			
//		}
	
		
		return numOfArgs;
	}

}
