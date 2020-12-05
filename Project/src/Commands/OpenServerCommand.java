package Commands;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import Expression.ShuntingYard;
import server_side.ClientHandler;
import server_side.MyClientHandler;
import server_side.MySerialServer;
import server_side.Server;

public class OpenServerCommand implements Command{

	public static volatile boolean stop=false;
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
		s.open(port, new ClientHandler() {

			@Override
			public void handleClient(InputStream in, OutputStream out) {
				// TODO Auto-generated method stub
				BufferedReader Bin=new BufferedReader(new InputStreamReader(in));
			
			}
		});
		
		return numOfArgs;
		}
}
