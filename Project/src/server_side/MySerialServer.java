package server_side;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.lang.Thread;

public class MySerialServer implements Server {

	private volatile boolean stop;
	

	public MySerialServer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void stop() {
		this.stop=true;
	}

	@Override
	public void open(int port, ClientHandler c){
		new Thread(()->{
		try {
			runServer(port,c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}).start();

	}
	
	// ------ Function that opens server and waiting for clients ----------

	private void runServer(int port, ClientHandler ch) throws Exception 
	{
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(1000);
		while(!stop) {
			try {
				Socket aClient = server.accept();
				System.out.println("Client Has Been Connected Succesfully");
				try {
					ch.handleClient(aClient.getInputStream(),aClient.getOutputStream());
					aClient.getInputStream().close();
					aClient.getOutputStream().close();
					aClient.close();
					
				}catch(IOException e){}
			}catch(SocketTimeoutException e) {}
		}
		server.close();
	}

}
