//package Commands;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.SocketTimeoutException;
//
//import Interpeter.Command;
//import server_side.ClientHandler;
//import server_side.Server;
//
//public class OpenDataServerCommand implements Command,Server{
//
//	private volatile boolean stop;
//	@Override
//	public void doCommand(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void testArgs(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void open(int port, ClientHandler c) {
//		// TODO Auto-generated method stub
//		new Thread(()->{
//		try {
//			runServer(port,c);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}).start();
//		
//	}
//
//	@Override
//	public void stop() {
//		// TODO Auto-generated method stub
//		this.stop=true;
//	}
//	
//	private void runServer(int port, ClientHandler ch) throws Exception 
//	{
//		ServerSocket server = new ServerSocket(port);
//		server.setSoTimeout(1000);
//		while(!stop) {
//			try {
//				Socket aClient = server.accept();
//				
//				try {
//					ch.handleClient(aClient.getInputStream(),aClient.getOutputStream());
//					aClient.getInputStream().close();
//					aClient.getOutputStream().close();
//					aClient.close();
//					
//				}catch(IOException e){}
//			}catch(SocketTimeoutException e) {}
//		}
//		server.close();
//	}
//
//}
