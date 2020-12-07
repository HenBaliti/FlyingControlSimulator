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
			System.out.println("Hen Server Has been Set Succecfully");
			while(!stop){
				try{
					Socket client=server.accept();
					System.out.println("Eli Client Has been Connected Succecfully");
					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String s = null;
					while((s=in.readLine())!=null){
						try{
							SymbolTabelObject STobject = null;
							String[] arr = s.split(",");
							for(int i=0;i<arr.length;i++) {

								if(i==0) {
//									System.out.println("SimX = "+arr[0].toString());
									STobject = Utilities.symbolTableSim.get("simX");
									STobject.setV(Double.parseDouble(arr[0]));
									System.out.println("set simX "+arr[0]);
									ConnectCommand.out.println("set simX" + " " + arr[0]);
									ConnectCommand.out.flush();
								}
								if(i==1) {
//									System.out.println("SimY = "+arr[1].toString());
									STobject = Utilities.symbolTableSim.get("simY");
									if(STobject!=null)
									STobject.setV(Double.parseDouble(arr[1]));
									System.out.println("set simY "+arr[1]);
									ConnectCommand.out.println("set simY" + " " + arr[1]);
									ConnectCommand.out.flush();
								}
								if(i==2) {
//									System.out.println("SimY = "+arr[2].toString());
									STobject = Utilities.symbolTableSim.get("simZ");
									if(STobject!=null)
									STobject.setV(Double.parseDouble(arr[2]));
									System.out.println("set simZ "+arr[2]);
									ConnectCommand.out.println("set simZ" + " " + arr[2]);
									ConnectCommand.out.flush();
								}
								
							} 
							try {Thread.sleep(1000/timePerS);} catch (InterruptedException e1) {} //Go to sleep for HZ time
						}catch(NumberFormatException e){}
					}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}

			if(stop==true) {
				System.out.println("--------------------------------------------------------Stopped Hen Server");
			}
		} catch (IOException e) {}
	}
	
}
