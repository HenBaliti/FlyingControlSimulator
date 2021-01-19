package Commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ConnectCommand implements Command{
	
	public Utilities ut;
	public static PrintWriter out;
	String ip;
	int port;
	public static volatile boolean stop=false;
	int numOfArgs;
	@Override
	public int doCommand(List<String> tokens, Utilities ut) {
		this.ut = ut;
		ip = tokens.get(1);
		port = Integer.parseInt(tokens.get(2));
		
		new Thread(()->{
		try {
			runClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}).start();
		
		numOfArgs = 2;
		return numOfArgs;
	}

	
	private void runClient(){
		while(!stop){
			try {
				
				Socket interpreter=new Socket(ip, port);
				out=new PrintWriter(interpreter.getOutputStream());
				while(!stop){
					try {Thread.sleep(100);} catch (InterruptedException e1) {}
				}
				out.close();
				interpreter.close();
			} catch (IOException e) {
				try {Thread.sleep(1000);} catch (InterruptedException e1) {}
			}
		}
	}


}
