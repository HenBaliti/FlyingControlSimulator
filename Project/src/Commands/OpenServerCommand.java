package Commands;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
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
	public static HashMap<Integer,String> varTable;
	

	public OpenServerCommand() {
		super();
		this.port=0;
		this.timePerS=0;
		
//        String[] variablesNamesOrder = {"airspeed", "alt", "Pressure", "pitch", "roll", "Internal-Pitch",
//                "Internal-Roll", "Encoder-Altitude", "Encoder-Pressure", "GPS-Altitude", "Ground-Speed",
//                "Vertical-Speed", "heading", "Compass-Heading", "Slip", "Turn", "Fpm-Speed", "aileron",
//                "elevator", "rudder", "Flaps", "throttle", "Rpm"
//        };
        varTable = new HashMap<Integer, String>();
        varTable.put(1, "/instrumentation/airspeed-indicator/indicated-speed-kt");
        varTable.put(2, "/instrumentation/altimeter/indicated-altitude-ft");
        varTable.put(3, "/instrumentation/altimeter/pressure-alt-ft");
        varTable.put(4, "/instrumentation/attitude-indicator/indicated-pitch-deg");
        varTable.put(5, "/instrumentation/attitude-indicator/indicated-roll-deg");
        varTable.put(6, "/instrumentation/attitude-indicator/internal-pitch-deg");
        varTable.put(7, "/instrumentation/attitude-indicator/internal-roll-deg");
        varTable.put(8, "/instrumentation/encoder/indicated-altitude-ft");
        varTable.put(9, "/instrumentation/encoder/pressure-alt-ft");
        varTable.put(10, "/instrumentation/gps/indicated-altitude-ft");
        varTable.put(11, "/instrumentation/gps/indicated-ground-speed-kt");
        varTable.put(12, "/instrumentation/gps/indicated-vertical-speed");
        varTable.put(13, "/instrumentation/heading-indicator/indicated-heading-deg");
        varTable.put(14, "/instrumentation/magnetic-compass/indicated-heading-deg");
        varTable.put(15, "/instrumentation/slip-skid-ball/indicated-slip-skid");
        varTable.put(16, "/instrumentation/turn-indicator/indicated-turn-rate");
        varTable.put(17, "/instrumentation/vertical-speed-indicator/indicated-speed-fpm");
        varTable.put(18, "/controls/flight/aileron");
        varTable.put(19, "/controls/flight/elevator");
        varTable.put(20, "/controls/flight/rudder");
        varTable.put(21, "/controls/flight/flaps");
        varTable.put(22, "/controls/engines/engine/throttle");
        varTable.put(23, "/engines/engine/rpm");
        
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
							String[] arr = s.split(",");
							for(int i=0;i<arr.length;i++) {
								
								double valueForString = Double.parseDouble(arr[i]);
								String variableString = varTable.get(i+1);
								

								if(Utilities.symbolTable.get(variableString)!=null) {
									//Updating the values in the VarObject if its not the same as the current value
									if(valueForString!=Utilities.symbolTable.get(variableString).getV()) {
										Utilities.symbolTable.get(variableString).setV(valueForString);
										System.out.println("Updating "+variableString+" to new value->  " +valueForString);
									}
									else {
										System.out.println("var name : "+variableString+" Value is still: "+valueForString);
									}
								}
								else {
									SymbolTabelObject stNew = new SymbolTabelObject(valueForString);
									Utilities.symbolTable.put(variableString, stNew);
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
