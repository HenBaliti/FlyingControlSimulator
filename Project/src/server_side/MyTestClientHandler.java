//package server_side;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class MyTestClientHandler implements ClientHandler{
//	
//	Solver<String,String> solver;
//	CacheManager<String,String> cm;
//
//
//	public MyTestClientHandler() {
//		this.solver = (var)->{
//			 StringBuilder input1 = new StringBuilder(); 
//			  
//		        // append a string into StringBuilder input1 
//		        input1.append(var); 
//		  
//		        // reverse StringBuilder input1 
//		        input1 = input1.reverse(); 
//			return input1.toString();
//		};
//		this.cm = new FileCacheManager<String, String>();
//		
//	}
//	@Override
//	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
//		// TODO Auto-generated method stub
//		BufferedReader userInput = new BufferedReader(new InputStreamReader(inFromClient));
//		PrintWriter outClient = new PrintWriter(outToClient);
//		
//		readInputAndSend(userInput,outClient);
//
//	}
//	
//	
//	
//	//  Function to read and write from a buffer
//	private void readInputAndSend(BufferedReader in, PrintWriter out) {
//		try {
//			String line;
//			while(!(line=in.readLine()).equals("end")) {
//				//Checking if the the soulotion is in the cachManager
//				if(cm.existSolution(line)) {
//					out.println(cm.loadSolution(line));
//				}else {
//					cm.store(line, this.solver.solve(line));
//					out.println(this.solver.solve(line));
//				}
//				//If it is not in the cachManager make the Solver and store the solve and send him to the client
//				
//				out.flush();
//			}
//		}catch(IOException e) {e.printStackTrace();}
//	}
//	
//
//}
