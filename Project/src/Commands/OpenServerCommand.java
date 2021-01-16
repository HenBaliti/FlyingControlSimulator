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
import Interpeter.SymbolTabelObject;
import server_side.ClientHandler;
import server_side.MyClientHandler;
import server_side.MySerialServer;
import server_side.Server;

//open the server
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
	public int doCommand(List<String> args) {
		port = Integer.parseInt(args.get(1));
		timePerS = Integer.parseInt(args.get(2));
		new Thread(()->{
		try {
			runServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}).start();
		
		return numOfArgs;
		}
	
	
	private void runServer(){
		try {
			ServerSocket server=new ServerSocket(port);
			server.setSoTimeout(1000);
			System.out.println("Server Has been Set Succecfully");
			while(!stop){
				try{
					Socket client=server.accept();
					System.out.println("Client-Simulator Has been Connected Succecfully");
					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String s = null;
					while((s=in.readLine())!=null){
						try{
							System.out.println("Whats coming from the simulator Client is : "+s);
							SymbolTabelObject STobject = null;
							String[] arr = s.split(",");
							for(int i=0;i<arr.length;i++) {


								//Updating the values in the VarObject if its not the same as the current value
								if(Double.parseDouble(arr[i])!=Utilities.symbolTableSim.get(arr[i]).getV()) {
									Utilities.symbolTableSim.get(arr[i]).setV(Double.parseDouble(arr[i]));
									System.out.println("Updating "+arr[i] +Utilities.symbolTableSim.get(arr[i]).getV());
								}
								
							} 
							try {Thread.sleep(timePerS);} catch (InterruptedException e1) {} //Go to sleep for HZ time
						}catch(NumberFormatException e){}
					}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}
			stop =true;
		} catch (IOException e) {}
	}
	
}
