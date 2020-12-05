package Commands;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
		new Thread(()->runServer()).start();
		
		return numOfArgs;
		}
	
	
	private void runServer(){
		try {
			ServerSocket server=new ServerSocket(port);
			server.setSoTimeout(1000);
			while(!stop){
				try{
					Socket client=server.accept();
					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String s = null;
					while((s=in.readLine())!=null){
						try{
							String[] arr = s.split(",");
							for(int i=0;i<arr.length;i++) {
//								Utilities.symbolTable
							}
							try {Thread.sleep(timePerS);} catch (InterruptedException e1) {} //Go to sleep for HZ time
						}catch(NumberFormatException e){}
					}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}
			server.close();
		} catch (IOException e) {}
	}
	
}
