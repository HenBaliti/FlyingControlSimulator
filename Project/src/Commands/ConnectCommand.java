package Commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ConnectCommand implements Command{
	
	public static volatile boolean stop=false;
	int numOfArgs;
	@Override
	public int doCommand(List<String> tokens) {

		numOfArgs = 2;
		return numOfArgs;
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}
	
//	private void runClient(){
//		while(!stop){
//			try {
//				Socket interpreter=new Socket("127.0.0.1", port+1);
//				PrintWriter out=new PrintWriter(interpreter.getOutputStream());
//				while(!stop){
//					out.println(simX+","+simY+","+simZ);
//					out.flush();
//					try {Thread.sleep(100);} catch (InterruptedException e1) {}
//				}
//				out.close();
//				interpreter.close();
//			} catch (IOException e) {
//				try {Thread.sleep(1000);} catch (InterruptedException e1) {}
//			}
//		}
//	}

}
