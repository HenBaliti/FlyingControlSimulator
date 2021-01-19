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
public class ServerInputFromSim{
	

	public static void main(String[] args) {
		
		MyServerGet ms = new MyServerGet();
		
		new Thread(()->{
		try {
			ms.runServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}).start();
		
		}
	
	
	
}


